pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        id("com.android.application") version "7.1.0-alpha06"
        id("com.android.library") version "7.1.0-alpha06"
        kotlin("org.jetbrains.kotlin.android") version "1.5.10"
    }
}

//dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
//    repositories {
//        google()
//        mavenCentral()
//    }
//}

rootProject.name = "GoldenNuggets"
include(":app")
include(":blockchain")
include(":core")
