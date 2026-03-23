// Generates the Widget interfaces backends implement

plugins {
    kotlin("multiplatform")
    alias(libs.plugins.redwood.widget)
    id("maven-publish")
}

kotlin {
    jvm()
    sourceSets {
        commonMain {
            dependencies {
                compileOnly(libs.arc.core)
                implementation(project(":schema:api"))
            }
        }
    }
}

redwoodSchema {
    source = project(":schema")
    type = "me.mars.maple.schema.Primitives"
}

publishing {
    publications {
        withType<MavenPublication> {
            val id = project.path.removePrefix(":").replace(":", "-")

            artifactId = if (name == "kotlinMultiplatform") id else "$id-$name"
        }
    }
}