pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://devrepo.kakao.com/nexus/content/groups/public/")
        }
        maven {
            url = uri("https://naver.jfrog.io/artifactory/maven/")
        }
    }
}
rootProject.name = "JJBAKSA"
include(":app")
include(":data")
include(":domain")
include(":imageselector")
