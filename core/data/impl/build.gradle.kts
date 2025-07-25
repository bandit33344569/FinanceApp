import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    id("com.google.devtools.ksp") version "2.1.21-2.0.1"
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.abrosimov.impl"
    compileSdk = 36

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val properties = Properties()
        val localPropertiesFile = project.rootProject.file("local.properties")

        if (localPropertiesFile.exists()) {
            properties.load(localPropertiesFile.inputStream())
        } else {
            throw GradleException("Файл local.properties не найден в корне проекта")
        }

        val bearerToken = properties.getProperty("BEARER_TOKEN", "")

        if (bearerToken.isBlank()) {
            throw GradleException("BEARER_TOKEN не задан в local.properties")
        }

        buildConfigField("String", "BEARER_TOKEN", "\"$bearerToken\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation("androidx.datastore:datastore-preferences:1.1.7")
    implementation(project(":core:data:api"))
    implementation(project(":core:utils"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)

    implementation(libs.kotlinx.serialization.core)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.material.icons.extended)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("androidx.compose.material3:material3:1.3.2")
    implementation("androidx.compose.material3:material3-window-size-class:1.3.2")
    implementation("androidx.compose.material3:material3-adaptive-navigation-suite:1.4.0-alpha15")

    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.squareup.retrofit2:retrofit:3.0.0")

    implementation("androidx.room:room-runtime:2.7.2")
    ksp("androidx.room:room-compiler:2.7.2")
    implementation("androidx.room:room-ktx:2.7.2")

    implementation("androidx.work:work-runtime-ktx:2.10.2")

    implementation ("com.google.dagger:dagger:2.56.2")
    ksp("com.google.dagger:dagger-compiler:2.56.2")
}