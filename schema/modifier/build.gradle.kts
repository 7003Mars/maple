// Generates the modifiers backends implement

plugins {
    kotlin("multiplatform")
    id("app.cash.redwood.generator.modifiers")

}

kotlin {
    jvm()
    sourceSets {
        commonMain {
            dependencies {
                compileOnly(libs.arc.core)
            }
        }
    }
}

redwoodSchema {
    source = project(":schema")
    type = "me.mars.maple.schema.Primitives"
}