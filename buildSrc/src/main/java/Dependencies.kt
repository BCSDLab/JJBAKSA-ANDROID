object KotlinDependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
    const val coroutine =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutineVersion}"
}

object AndroidXDependencies {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val hilt = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
}

object KTXDependencies {
    const val coreKTX = "androidx.core:core-ktx:${Versions.coreKtxVersion}"
    const val activityKTX = "androidx.activity:activity-ktx:${Versions.activityKTXVersion}"
    const val lifecycleKTX =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleKTXVersion}"
    const val fragmentKTX = "androidx.fragment:fragment-ktx:${Versions.fragmentKTXVersion}"
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
    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"
}

object ClassPathPlugins {
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltVersion}"
}
