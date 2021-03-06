plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("com.squareup.sqldelight")
    id("de.mannodermaus.android-junit5")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization")
}

android {
    compileSdk = 30

    defaultConfig {
        minSdk = 23
        targetSdk = 30
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    packagingOptions {
        exclude("**/attach_hotspot_windows.dll")
        exclude("META-INF/licenses/**")
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
        exclude("META-INF/INDEX.LIST")
        exclude("META-INF/*")
    }

    buildTypes {
        getByName("debug") {
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions("env")
    productFlavors {
        create("local") {
            dimension = "env"
        }
        create("integration") {
            dimension = "env"
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
                        variantBuilder.buildType == "release" && flavourMap.containsValue("local") ||
                        variantBuilder.buildType == "release" && flavourMap.containsValue("integration")
                -> variantBuilder.enabled = false
                else -> variantBuilder.enabled = true
            }
        }
    }
}

dependencies {
    implementation(project(":core"))

    /* Kotlin */
    implementation(Dependencies.Kotlin.datetime)
    implementation(Dependencies.Kotlin.jsonSerialization)

    /* Android */
    implementation(Dependencies.Android.coreKtx)
    implementation(Dependencies.Android.appCompat)

    /* Hilt */
    implementation(Dependencies.Hilt.android)
    implementation(Dependencies.Hilt.navigation)
    implementation(Dependencies.Hilt.navigationFragment)
    kapt(Dependencies.Hilt.androidCompiler)

    /* Ktor */
    implementation(Dependencies.Ktor.ktor)
    implementation(Dependencies.Ktor.Server.cioEngine)
    implementation(Dependencies.Ktor.Server.serialization)
    implementation(Dependencies.Ktor.Server.logging)

    /* SQL Delight */
    implementation(Dependencies.SqlDelight.androidDriver)
    implementation(Dependencies.SqlDelight.coroutines)

    testImplementation(Dependencies.Junit.api)
    testImplementation(Dependencies.Junit.engine)
    testImplementation(Dependencies.Junit.parameterized)
    testImplementation(Dependencies.Mockito.core)
    testImplementation(Dependencies.Mockito.kotlin)
    testImplementation(Dependencies.Kotlin.test)
    testImplementation(Dependencies.Kotlin.testJunit5)
    testImplementation(Dependencies.Kotlin.coroutineTest)
}