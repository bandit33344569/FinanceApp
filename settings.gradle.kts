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
include(":features:expenses")
include(":features:incomes")
include(":features:settings")
include(":network")
include(":core")
include(":features:history")
include(":transactiondata")
