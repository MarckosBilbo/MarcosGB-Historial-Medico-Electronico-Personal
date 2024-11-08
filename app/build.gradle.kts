plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt) // Asegúrate de que esta línea esté incluida
    id("com.google.gms.google-services") version libs.versions.googleServices.get() // Aplica el plugin de Google Services
}

android {
    namespace = "com.example.marcosgb_historial_mdico_electrnico_personal"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.marcosgb_historial_mdico_electrnico_personal"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    sourceSets {
        getByName("main") {
            manifest.srcFile("src/main/AndroidManifest.xml")
            java.srcDirs("src/main/java")
            res.srcDirs("src/main/res")
        }
    }

    buildToolsVersion = "34.0.0"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    //implementation(libs.androidx.room.runtime) // Dependencia de Room runtime
    //kapt(libs.androidx.room.compiler) // Dependencia de Room compiler
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Firebase dependencies
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.messaging)
    implementation(libs.androidx.multidex)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    //extras
    implementation (libs.androidx.work.runtime.ktx)
}

// Aplica el plugin de Google Services
apply(plugin = "com.google.gms.google-services")

/*testImplementation(libs.junit)
testImplementation(libs.kotlinx.coroutines.test.v164)
androidTestImplementation(libs.ui.test.junit4)
androidTestImplementation(libs.androidx.junit.v113)
androidTestImplementation(libs.androidx.espresso.core.v340)
testImplementation(libs.kotlinx.coroutines.test)
*/

