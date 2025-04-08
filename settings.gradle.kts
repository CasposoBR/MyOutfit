pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
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