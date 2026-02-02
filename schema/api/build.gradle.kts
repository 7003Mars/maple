// Typedefs and data classes that the schema uses.

plugins {
    alias(libs.plugins.kotlin.jvm)
    id("maven-publish")
}

dependencies {
    compileOnly(libs.arc.core)
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            artifactId = project.path.removePrefix(":").replace(":", "-")
        }
    }
}