import io.grpc.InternalChannelz.id

plugins {

    id("com.android.application")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.virtualfarm"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.virtualfarm"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("org.java-websocket:Java-WebSocket:1.5.6")
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("com.github.Mathias-Boulay:virtual-joystick-android:1.13.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("tech.gusavila92:java-android-websocket-client:1.2.2")
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation ("com.google.android.material:material:1.3.0-alpha03")
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-database:20.2.1")
    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))



}