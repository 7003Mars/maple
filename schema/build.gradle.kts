// The schema definitions.

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.redwood)
    id("maven-publish")
}

redwoodSchema {
    type = "me.mars.maple.schema.Primitives"
}

dependencies {
    compileOnly(libs.arc.core)
    implementation(project(":schema:api"))
//    implementation(libs.redwood.schema) // Seems to be added by by the redwood plugin?
}

tasks.register("codeGen") {
    // TODO I'm not sure if we need to run :schema:build (Which calls :schema:redwoodApiCheck and may ask us to run :schema:redwoodApiGenerate)
    dependsOn(":schema:widget:redwoodKotlinGenerate", ":schema:compose:redwoodKotlinGenerate", ":schema:modifier:redwoodKotlinGenerate")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            artifactId = project.name
        }
    }
}