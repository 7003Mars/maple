// Generates the Widget interfaces backends implement

plugins {
    kotlin("multiplatform")
    id("app.cash.redwood.generator.widget")

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