plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.redwood.schema) apply false
    alias(libs.plugins.redwood.widget) apply false
    alias(libs.plugins.redwood.modifiers) apply false
    alias(libs.plugins.redwood.compose) apply false
}

version = "0.1.0"

subprojects {
    group = "me.mars.maple"
    version = rootProject.version

    plugins.withType<JavaPlugin> {
        configure<JavaPluginExtension> {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(17))
            }
        }
    }

    repositories {
        google()
        mavenCentral()
        maven("https://www.jitpack.io")
        maven("https://raw.githubusercontent.com/Zelaux/MindustryRepo/master/repository")
        maven("https://maven.xpdustry.com/mindustry")
    }
}