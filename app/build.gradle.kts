plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    id("com.google.gms.google-services")
}

android {
    compileSdk = Constants.compileSdk

    defaultConfig {
        applicationId = Constants.packageName
        minSdk = Constants.minSdk
        targetSdk = Constants.targetSdk
        versionCode = Constants.versionCode
        versionName = Constants.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = Versions.javaVersion
        targetCompatibility = Versions.javaVersion
    }

    kotlinOptions {
        jvmTarget = Versions.jvmVersion
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation("androidx.appcompat:appcompat:1.5.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    KotlinDependencies.run {
        implementation(kotlin)
        implementation(coroutine)
    }

    KTXDependencies.run {
        implementation(coreKTX)
        implementation(activityKTX)
        implementation(fragmentKTX)
        implementation(lifecycleKTX)
        implementation(viewModelKTX)
        implementation(navigationFragmentKTX)
        implementation(navigationUiKTX)
    }

    AndroidXDependencies.run {
        implementation(appCompat)
        implementation(constraintLayout)
        implementation(hilt)
    }

    MaterialDependencies.run {
        implementation(material)
    }

    TestDependencies.run {
        implementation(jUnit)
        implementation(androidTest)
        implementation(espresso)
    }

    KaptDependencies.run {
        kapt(hiltKapt)
    }

    ThirdPartyDependencies.run {
        implementation(coil)
        implementation(timber)
        implementation(interceptor)
        implementation(gson)
        implementation(retrofit2)
        implementation(retrofit2Converter)
        implementation(roomRuntime)
        implementation(roomKtx)
        annotationProcessor(roomCompiler)
        kapt(roomKapComplier)
    }
    FirebaseDependencies.run {
        implementation(platform(FirebaseDependencies.firebaseBom))
        implementation(FirebaseDependencies.firebaseAnalytics)
        implementation(FirebaseDependencies.firebaseAuth)
    }
}

ktlint {
    android.set(true)
    coloredOutput.set(true)
    verbose.set(true)
    outputToConsole.set(true)
    disabledRules.set(setOf("max-line-length", "import-ordering"))
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
}
