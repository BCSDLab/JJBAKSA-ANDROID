package com.jjbaksa.data.repository

import android.net.Uri
import com.jjbaksa.data.datasource.local.UserLocalDataSource
import com.jjbaksa.data.datasource.remote.UserRemoteDataSource
import com.jjbaksa.data.mapper.FormDataUtil
import com.jjbaksa.data.mapper.RespMapper
import com.jjbaksa.data.mapper.follower.toFollower
import com.jjbaksa.data.mapper.toUserList
import com.jjbaksa.data.mapper.user.toLoginResult
import com.jjbaksa.data.mapper.user.toUser
import com.jjbaksa.data.model.apiCall
import com.jjbaksa.domain.base.ErrorType
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.model.follower.FollowerList
import com.jjbaksa.domain.model.user.User
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.model.user.LoginReq
import com.jjbaksa.domain.model.user.Login
import com.jjbaksa.domain.model.user.SignUpReq
import com.jjbaksa.domain.model.user.SignUpResp
import com.jjbaksa.domain.model.user.FindPasswordReq
import com.jjbaksa.domain.model.user.PasswordAndNicknameReq
import com.jjbaksa.domain.model.user.UserList
import com.jjbaksa.domain.model.user.WithdrawalReasonReq
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : UserRepository {
    override suspend fun postLoginSNS(token: String, snsType: String): Result<Login> =
        runCatching { userRemoteDataSource.postLoginSNS(token, snsType).toLoginResult() }.onSuccess {
            userRemoteDataSource.saveAccessToken(it.accessToken)
            userRemoteDataSource.saveRefreshToken(it.refreshToken)
        }

    override suspend fun getUserMe(): Flow<Result<User>> {
        return apiCall(
            call = { userRemoteDataSource.getUserMe() },
            remoteData = {
                if (it.isSuccessful) {
                    val user = it.body()?.toUser() ?: User()
                    userLocalDataSource.saveNickname(user.nickname)
                    userLocalDataSource.saveFollowers(user.userCountResponse.friendCount)
                    userLocalDataSource.saveReviews(user.userCountResponse.reviewCount)
                    userLocalDataSource.saveProfileImage(user.profileImage.url.toString())
                }
            },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toUser() ?: User()
                } else {
                    User()
                }
            }
        )
    }

    override suspend fun postLogin(
        account: String,
        password: String,
        isAutoLogin: Boolean,
    ): Flow<Result<Login>> {
        return apiCall(
            call = { userRemoteDataSource.postLogin(LoginReq(account, password)) },
            remoteData = {
                if (it.isSuccessful) {
                    userLocalDataSource.clearDataStore()
                    val loginResponse = it.body()?.toLoginResult() ?: Login()
                    userLocalDataSource.saveAccessToken(loginResponse.accessToken)
                    userLocalDataSource.saveRefreshToken(loginResponse.refreshToken)
                    userLocalDataSource.saveAccount(account)
                    userLocalDataSource.saveAutoLogin(isAutoLogin)
                }
            },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toLoginResult() ?: Login()
                } else {
                    val errorResult = RespMapper.errorMapper(it.errorBody()?.string() ?: "")
                    Login(errorMessage = errorResult.errorMessage)
                }
            }
        )
    }

    override suspend fun postUserEmailCheck(
        email: String,
    ): Flow<Result<Login>> {
        return apiCall(
            call = {
                userRemoteDataSource.postUserEmailCheck(email)
            },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toLoginResult() ?: Login()
                } else {
                    val errorResult = RespMapper.errorMapper(it.errorBody()?.string() ?: "")
                    Login(errorMessage = errorResult.errorMessage)
                }
            }
        )
    }

    override suspend fun postUserEmailId(
        email: String,
        onError: (String) -> Unit,
    ): Flow<Result<Boolean>> {
        return apiCall(
            call = { userRemoteDataSource.postUserEmailId(email) },
            mapper = {
                if (it.isSuccessful) {
                    true
                } else {
                    val errorResult = RespMapper.errorMapper(it.errorBody()?.string() ?: "")
                    onError(errorResult.errorMessage)
                    false
                }
            }
        )
    }

    override suspend fun getUserId(
        email: String,
        code: String,
        onError: (String) -> Unit,
    ): Flow<Result<String>> {
        return apiCall(
            call = { userRemoteDataSource.getUserId(email, code) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.account ?: ""
                } else {
                    val errorResult = RespMapper.errorMapper(it.errorBody()?.string() ?: "")
                    onError(errorResult.errorMessage)
                    ""
                }
            }
        )
    }

    override suspend fun postUserEmailPassword(
        id: String,
        email: String,
        onError: (String) -> Unit,
    ): Flow<Result<Boolean>> {
        return apiCall(
            call = { userRemoteDataSource.postUserEmailPassword(id, email) },
            mapper = {
                if (it.isSuccessful) {
                    true
                } else {
                    val errorResult = RespMapper.errorMapper(it.errorBody()?.string() ?: "")
                    onError(errorResult.errorMessage)
                    false
                }
            }
        )
    }

    override suspend fun postUserPassword(
        findPasswordReq: FindPasswordReq,
        onError: (String) -> Unit,
    ): Flow<Result<Boolean>> {
        return apiCall(
            call = { userRemoteDataSource.postUserPassword(findPasswordReq) },
            remoteData = {
                userLocalDataSource.saveAuthPasswordToken(it.body().toString())
            },
            mapper = {
                if (it.isSuccessful) {
                    true
                } else {
                    val errorResult = RespMapper.errorMapper(it.errorBody()?.string() ?: "")
                    onError(errorResult.errorMessage)
                    false
                }
            }
        )
    }

    override suspend fun setNewPassword(
        password: String,
        onError: (String) -> Unit,
    ): Flow<Result<Boolean>> {
        return apiCall(
            call = {
                val token = userLocalDataSource.getAuthPasswordToken().ifEmpty {
                    userLocalDataSource.getAccessToken()
                }
                userRemoteDataSource.patchUserMe(
                    "Bearer $token",
                    PasswordAndNicknameReq(password = password)
                )
            },
            mapper = {
                if (it.isSuccessful) {
                    true
                } else {
                    val errorResult = RespMapper.errorMapper(it.errorBody()?.string() ?: "")
                    onError(errorResult.errorMessage)
                    false
                }
            }
        )
    }

    override suspend fun postUserCheckPassword(
        password: String,
        onError: (String) -> Unit,
    ): Flow<Result<Boolean>> {
        return apiCall(
            call = { userRemoteDataSource.postUserCheckPassword(password) },
            mapper = {
                if (it.isSuccessful) {
                    true
                } else {
                    val errorResult = RespMapper.errorMapper(it.errorBody()?.string() ?: "")
                    onError(errorResult.errorMessage)
                    false
                }
            }
        )
    }

    override suspend fun postSignUp(signUpReq: SignUpReq): SignUpResp? {
        val resp = userRemoteDataSource.postSignUp(signUpReq)
        return resp.body()
    }

    override suspend fun checkAccountAvailable(account: String): RespResult<Boolean> {
        val result = userRemoteDataSource.checkAccountAvailable(account)
        return if (result.isSuccessful) {
            RespResult.Success(result.isSuccessful)
        } else {
            val errorBodyJson = result.errorBody()!!.string()
            val errorBody = RespMapper.errorMapper(errorBodyJson)
            RespResult.Error(ErrorType(errorBody.errorMessage, errorBody.code))
        }
    }

    override suspend fun setNewNickname(nickname: String): Flow<Result<User>> {
        return apiCall(
            call = { userRemoteDataSource.setNewNickname(PasswordAndNicknameReq(nickname = nickname)) },
            remoteData = {
                if (it.isSuccessful) {
                    userLocalDataSource.saveNickname(nickname)
                }
            },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toUser() ?: User()
                } else {
                    User()
                }
            }
        )
    }

    override suspend fun editUserProfile(profile: String): Flow<Result<User>> {
        return apiCall(
            call = {
                val fileBody = FormDataUtil.getImageBody("profile", Uri.parse(profile))
                userRemoteDataSource.editUserProfile(fileBody)
            },
            remoteData = {
                if (it.isSuccessful)
                    userLocalDataSource.saveProfileImage(it.body()?.profileImage?.url ?: "")
            },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toUser() ?: User()
                } else {
                    User()
                }
            }
        )
    }

    override suspend fun postUserWithdrawReason(
        withdrawalReasonReq: WithdrawalReasonReq,
        onError: (String) -> Unit,
    ): Flow<Result<Boolean>> {
        return apiCall(
            call = { userRemoteDataSource.postUserWithdrawReason(withdrawalReasonReq) },
            mapper = {
                if (it.isSuccessful) {
                    true
                } else {
                    val errorResult = RespMapper.errorMapper(it.errorBody()?.string() ?: "")
                    onError(errorResult.errorMessage)
                    false
                }
            }
        )
    }

    override suspend fun deleteUserMe(onError: (String) -> Unit): Flow<Result<Boolean>> {
        return apiCall(
            call = { userRemoteDataSource.deleteUserMe() },
            remoteData = { userLocalDataSource.clearDataStore() },
            mapper = {
                if (it.isSuccessful) {
                    true
                } else {
                    val errorResult = RespMapper.errorMapper(it.errorBody()?.string() ?: "")
                    onError(errorResult.errorMessage)
                    false
                }
            }
        )
    }

    override suspend fun logout() {
        userLocalDataSource.clearDataStore()
    }

    override fun getAutoLoginFlag(): Boolean {
        return userLocalDataSource.getAutoLoginFlag()
    }

    override fun getAccount(): String {
        return userLocalDataSource.getAccount()
    }

    override fun getNickname(): String {
        return userLocalDataSource.getNickname()
    }

    override fun getFollowers(): Int {
        return userLocalDataSource.getFollowers()
    }

    override fun getProfileImage(): String {
        return userLocalDataSource.getProfileImage()
    }

    override fun getAccessToken(): String {
        return userLocalDataSource.getAccessToken()
    }

    override suspend fun getUserSearch(
        keyWord: String?,
        pageSize: Int,
        cursor: Long
    ): Flow<Result<UserList>> {
        return apiCall(
            call = { userRemoteDataSource.getUserSearch(keyWord, pageSize, cursor) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toUserList() ?: UserList()
                } else {
                    UserList()
                }
            }
        )
    }



}
