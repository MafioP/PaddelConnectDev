buildscript {
    repositories {
        google()
    }
    dependencies {
        // Aquí se agrega la dependencia del complemento de Google Services
        classpath("com.google.gms:google-services:4.3.8")

    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.3" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}