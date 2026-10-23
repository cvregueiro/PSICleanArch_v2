import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}

// Private values (sdk.dir, API keys...) that must never be committed
val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        file.inputStream().use { load(it) }
    }
}

android {
    namespace = "es.udc.psi.cleanarchv2"
    compileSdk = 37

    defaultConfig {
        applicationId = "es.udc.psi.cleanarchv2"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // setlist.fm API key: add "setlistfm.apikey=YOUR_API_KEY" to local.properties
        // (get one at https://www.setlist.fm/settings/api)
        buildConfigField(
            "String",
            "SETLISTFM_API_KEY",
            "\"${localProperties.getProperty("setlistfm.apikey", "")}\""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    // Android
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson) { exclude(module = "gson") }
    implementation(libs.gson)
    implementation(libs.okhttp.logging.interceptor)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
