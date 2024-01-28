import java.util.Properties

plugins {
    id("com.android.application")
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")

}


android {

    val apikeyPropertiesFile = rootProject.file("apiKeys.properties")
    val apikeyProperties = Properties()

    if (apikeyPropertiesFile.exists()) {
        apikeyProperties.load(apikeyPropertiesFile.inputStream())
    } else {
        throw GradleException("apikey.properties file not found. Please create it in the root project directory.")
    }

    namespace = "com.jetpack.firebasekit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jetpack.firebasekit"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {

        debug {
            buildConfigField("String", "WEB_CLIENT_ID",
                apikeyProperties["WEB_CLIENT_ID"].toString()
            )
        }

        release {
            buildConfigField("String", "WEB_CLIENT_ID",
                apikeyProperties["WEB_CLIENT_ID"].toString()
            )
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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.firebase:firebase-crashlytics-buildtools:2.9.9")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Import the Firebase BoM - Bill of Materials
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))

    // 1. Firebase Authentication
    implementation("com.google.firebase:firebase-auth")

    // 2. Firebase Remote Config
    implementation("com.google.firebase:firebase-config")

    // Add the dependency for the Google Play services library
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // Compose navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // Dagger Hilt - DI
    implementation("com.google.dagger:hilt-android:2.48.1")
    kapt("com.google.dagger:hilt-android-compiler:2.48.1")
    kapt("androidx.hilt:hilt-compiler:1.1.0")


    // Coroutines - Light weight thread, asynchronous
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Coroutine Lifecycle Scopes
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    // Coil for image loading library
    implementation("io.coil-kt:coil-compose:2.5.0")

    /** Animated Compose Bottom navigation */
    implementation("com.exyte:animated-navigation-bar:1.0.0")

    // Gson - Json to Java Objects
    implementation("com.google.code.gson:gson:2.10.1")

    //Moshi - modern JSON library for Android
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")
}
