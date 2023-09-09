object KotlinDependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
    const val coroutine =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutineVersion}"
    const val inject = "javax.inject:javax.inject:1"
}

object AndroidXDependencies {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val hilt = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    const val fragment = "androidx.fragment:fragment:${Versions.fragmentKTXVersion}"
}

object KTXDependencies {
    const val coreKTX = "androidx.core:core-ktx:${Versions.coreKtxVersion}"
    const val activityKTX = "androidx.activity:activity-ktx:${Versions.activityKTXVersion}"
    const val lifecycleKTX =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleKTXVersion}"
    const val viewModelKTX =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModelKTXVersion}"
    const val fragmentKTX = "androidx.fragment:fragment-ktx:${Versions.fragmentKTXVersion}"
    const val navigationFragmentKTX = "androidx.navigation:navigation-fragment:${Versions.navigationFragmentKTXVersion}"
    const val navigationUiKTX = "androidx.navigation:navigation-ui:${Versions.navigationUiKTXVersion}"
}

object TestDependencies {
    const val jUnit = "junit:junit:${Versions.jUnitVersion}"
    const val androidTest = "androidx.test.ext:junit:${Versions.androidTestVersion}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
}

object MaterialDependencies {
    const val material = "com.google.android.material:material:${Versions.materialVersion}"
}

object KaptDependencies {
    const val hiltKapt = "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"
}

object ThirdPartyDependencies {
    const val coil = "io.coil-kt:coil:${Versions.coilVersion}"
    const val interceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.interceptorVersion}"
    const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}"
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2Version}"
    const val retrofit2Converter =
        "com.squareup.retrofit2:converter-gson:${Versions.retrofit2Version}"
    const val retrofit2ScalarConverter =
        "com.squareup.retrofit2:converter-scalars:${Versions.retrofit2Version}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"
    const val roomRuntime =  "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val roomKapComplier = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val datastore = "androidx.datastore:datastore-preferences:${Versions.datastoreVersion}"
}
object FirebaseDependencies {
    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBomVersion}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
    const val firebaseAuth = "com.google.firebase:firebase-auth-ktx"
}

object NaverDependencies{
    const val naverMaps = "com.naver.maps:map-sdk:${Versions.naverMapsVersion}"
}

object GooglePlayServiceDependencies {
    const val googlePlayServiceLocation = "com.google.android.gms:play-services-location:${Versions.googlePlayServiceLocationVersion}"
}

object ClassPathPlugins {
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltVersion}"
}

object GlideDependencies {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
}