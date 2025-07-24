pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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
}

rootProject.name = "Finance App"
include(":app")
include(":features")
include(":features:account")
include(":features:categories")
include(":features:settings")
include(":core")
include(":features:transactions")
include(":core:data")
include(":core:data:api")
include(":core:data:impl")
include(":core:ui")
include(":core:utils")
include(":core:ui:graphics")
