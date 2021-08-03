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

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

    implementation(Dependencies.Compose.uiToolingPreview)
    debugImplementation(Dependencies.Compose.uiTooling)

    /* Hilt */
    implementation(Dependencies.Hilt.android)
    implementation(Dependencies.Hilt.navigation)
    implementation(Dependencies.Hilt.navigationFragment)
    kapt(Dependencies.Hilt.androidCompiler)

    /* Unit Test Dependencies */
    testImplementation(Dependencies.Junit.api)
    testImplementation(Dependencies.Junit.engine)
    testImplementation(Dependencies.Junit.parameterized)
    testImplementation("org.mockito:mockito-core:3.11.2")
    testImplementation(Dependencies.Kotlin.test)
    testImplementation(Dependencies.Kotlin.testJunit5)
    debugImplementation(Dependencies.Android.fragmentTesting)

    /* UI Test Dependencies */
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${ Version.compose }")
}