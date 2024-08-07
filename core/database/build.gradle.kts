plugins {
    id("core-convention")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
}

android {
    namespace = "pet.project.database"
    compileSdk = 34
}
dependencies {
    implementation(project(":domain"))
    implementation(project(":core:utils"))
}
