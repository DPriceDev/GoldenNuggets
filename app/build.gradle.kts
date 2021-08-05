plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("de.mannodermaus.android-junit5")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = 30
    defaultConfig {
        applicationId = "com.dpricedev.crypto.goldennuggets"
        minSdk = 23
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Version.compose
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }

    testBuildType = "espresso"

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
        }
        create("espresso") {
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
        }
        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions("env")
    productFlavors {
        create("local") {
            dimension = "env"
            applicationIdSuffix = ".local"
            versionNameSuffix = "-local"
        }
        create("integration") {
            dimension = "env"
            applicationIdSuffix = ".int"
            versionNameSuffix = "-int"
        }
        create("prod") {
            dimension = "env"
        }
    }

    androidComponents {
        beforeVariants { variantBuilder ->
            val flavourMap = variantBuilder.productFlavors.toMap()
            when {
                variantBuilder.buildType == "debug" && flavourMap.containsValue("prod") ||
                        variantBuilder.buildType == "espresso" && flavourMap.containsValue("prod") ||
                        variantBuilder.buildType == "espresso" && flavourMap.containsValue("integration") ||
                        variantBuilder.buildType == "release" && flavourMap.containsValue("local") ||
                        variantBuilder.buildType == "release" && flavourMap.containsValue("integration")
                -> variantBuilder.enabled = false
                else -> variantBuilder.enabled = true
            }
        }
    }
}

dependencies {

    /* Android */
    implementation(Dependencies.Android.coreKtx)
    implementation(Dependencies.Android.appCompat)
    implementation(Dependencies.Android.runtimeKtx)
    implementation(Dependencies.Android.fragmentKtx)

    /* Compose */
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.material)
    implementation(Dependencies.Compose.navigation)
    implementation(Dependencies.Compose.animation)
    implementation("androidx.activity:activity-compose:1.3.0")

    implementation(Dependencies.Compose.uiTooling)

    /* Hilt */
    implementation(Dependencies.Hilt.android)
    implementation(Dependencies.Hilt.navigation)
    implementation(Dependencies.Hilt.navigationFragment)
    kapt(Dependencies.Hilt.androidCompiler)

    /* Unit Test Dependencies */
    testImplementation(Dependencies.Junit.api)
    testImplementation(Dependencies.Junit.engine)
    testImplementation(Dependencies.Junit.parameterized)
    testImplementation(Dependencies.Mockito.core)
    testImplementation(Dependencies.Mockito.kotlin)
    testImplementation(Dependencies.Kotlin.test)
    testImplementation(Dependencies.Kotlin.testJunit5)
    debugImplementation(Dependencies.Android.fragmentTesting)

    /* UI Test Dependencies */
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation(Dependencies.Kotlin.test)
    androidTestImplementation(Dependencies.Mockito.core)
    androidTestImplementation(Dependencies.Mockito.kotlin)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${ Version.compose }")
}