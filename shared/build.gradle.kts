plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.7.20"
    id("com.android.library")
    id("com.squareup.sqldelight")

}

kotlin {
    android()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    val dataStoreVersion = "1.1.0-dev01"
    val sqlDelightVersion = "1.5.3"
    val napierVersion = "2.5.0"
    val ktorVersion = "2.1.2"

    sourceSets {

        val commonMain by getting {
            dependencies {
                // Logging
                implementation("io.github.aakira:napier:$napierVersion")

                // Ktor
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-auth:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

                implementation("androidx.collection:collection:1.3.0-dev01")

                // Lower-level APIs with support for custom serialization
                implementation("androidx.datastore:datastore-core-okio:$dataStoreVersion")
                // Higher-level APIs for storing values of basic types
                implementation("androidx.datastore:datastore-preferences-core:$dataStoreVersion")

                implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")

            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")

            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 28
        targetSdk = 32
    }
    namespace = "com.example.debit72"
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.example.debit72.kmm.shared.cache"
        sourceFolders = listOf("sqldelight")
    }
}