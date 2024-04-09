import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.example.crimeguardian"
    compileSdk = 34

    val apikeyPropertiesFile = rootProject.file("apikey.properties")
    val apikeyProperties = Properties().apply {
        load(apikeyPropertiesFile.inputStream())
    }

    defaultConfig {
        applicationId = "com.example.crimeguardian"
        minSdk = 27
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "NEWS_API_KEY", apikeyProperties["NEWS_API_KEY"] as String)

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            buildConfigField("String", "BASE_URL", "\"https://newsapi.org/v2/\"")
        }

        release {
            buildConfigField("String", "BASE_URL", "\"https://newsapi.org/v2/\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.material)
    //Navigation Component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)


    //Google Cloud and Map
    implementation(libs.play.services.maps)
    implementation (libs.play.services.location)
    implementation (libs.android.maps.utils)
    //API
    implementation(libs.converter.gson)
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.converter.kotlinx.serialization)
    implementation(libs.okhttp.logging.interceptor)

    //GLIDE
    implementation(libs.glide)

    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)

}