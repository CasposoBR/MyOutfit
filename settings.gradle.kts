pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("myLibs") {
            from(files("gradle/libs.versions.toml")) // ✅ SÓ UMA CHAMADA AO from
        }
    }
}


rootProject.name = "MyOutfit"
include(":app")