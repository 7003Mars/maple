// Generates the Composables that users will use.

plugins {
    kotlin("multiplatform")
    id("app.cash.redwood.generator.compose")
}

kotlin {
    jvm()
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":schema:widget"))
                implementation(project(":schema:modifier"))
                implementation(project(":schema:api"))
                compileOnly(libs.arc.core)
//                implementation(libs.redwood.compose) // These deps seem to be provided by the plugin
//                implementation(libs.redwood.widget)
            }
        }
    }
}

redwoodSchema {
    source = project(":schema")
    type = "me.mars.maple.schema.Primitives"
}
