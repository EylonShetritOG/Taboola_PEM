plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://taboolapublic.jfrog.io/artifactory/mobile-release")
        }
    }
}