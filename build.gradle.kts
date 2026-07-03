import org.jetbrains.changelog.Changelog

plugins {
    id("java")
    id("org.jetbrains.intellij.platform")
    id("org.jetbrains.changelog")
}

dependencies {
    // IntelliJ Platform Gradle Plugin Dependencies Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html
    intellijPlatform {
        intellijIdeaCommunity("2024.2.4")
    }
}

intellijPlatform {
    // The plugin ships only theme resources; there is no bytecode to instrument.
    instrumentCode = false

    pluginConfiguration {
        ideaVersion {
            sinceBuild = "242"
            untilBuild = provider { null }
        }

        changeNotes = provider {
            changelog.renderItem(changelog.getUnreleased(), Changelog.OutputType.HTML)
        }
    }

    publishing {
        token = providers.environmentVariable("PUBLISH_TOKEN")
    }
}

changelog {
    repositoryUrl = providers.gradleProperty("pluginRepositoryUrl")
    groups.empty()
}

// Convenience alias so `./gradlew generateThemes` works from the root.
tasks.register("generateThemes") {
    group = "gruvbox"
    description = "Regenerates the committed theme files from the palette definition."
    dependsOn(":generator:generateThemes")
}
