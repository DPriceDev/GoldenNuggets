
object Dependencies {

    object Kotlin {
        const val test = "org.jetbrains.kotlin:kotlin-test:${ Version.kotlin }"
        const val testJunit5 = "org.jetbrains.kotlin:kotlin-test-junit5:${ Version.kotlin }"
    }

    object Android {
        const val coreKtx = "androidx.core:core-ktx:1.6.0"
        const val appCompat = "androidx.appcompat:appcompat:1.3.0"
        const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-alpha02"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:${ Version.fragmentKtx }"
        const val fragmentTesting = "androidx.fragment:fragment-testing:${ Version.fragmentKtx }"

        const val material = "com.google.android.material:material:1.4.0"
    }

    object Compose {
        const val ui = "androidx.compose.ui:ui:${ Version.compose }"
        const val material = "androidx.compose.material:material:${ Version.compose }"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${ Version.compose }"
        const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${ Version.compose }"
        const val animation = "androidx.compose.animation:animation:${ Version.compose }"
        const val navigation = "androidx.navigation:navigation-compose:${ Version.composeNavigation }"
    }

    object Hilt {
        const val android = "com.google.dagger:hilt-android:${ Version.hilt }"

        const val navigation = "androidx.hilt:hilt-navigation-compose:${ Version.hiltNavigation }"
        const val navigationFragment = "androidx.hilt:hilt-navigation-fragment:${ Version.hiltNavigationFragment }"

        const val androidCompiler = "com.google.dagger:hilt-android-compiler:${ Version.hilt }"

        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${ Version.hilt }"
    }

    object Room {
        const val runtime = "androidx.room:room-runtime:${ Version.room }"
        const val compiler = "androidx.room:room-compiler:${ Version.room }"
        const val ktx = "androidx.room:room-ktx:${ Version.room }"
    }

    object Junit {
        const val api = "org.junit.jupiter:junit-jupiter-api:${ Version.junit5 }"
        const val engine = "org.junit.jupiter:junit-jupiter-engine:${ Version.junit5 }"
        const val parameterized = "org.junit.jupiter:junit-jupiter-params:${ Version.junit5 }"
    }

    object Experimental {
        const val androidx = "https://androidx.dev/snapshots/builds/${ Version.snapshot }/artifacts/repository"
    }
}