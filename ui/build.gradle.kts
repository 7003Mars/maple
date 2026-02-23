import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.kotlin.dsl.support.serviceOf

plugins {
    `java-library`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.compose)
    id("maven-publish")
    // TODO Prob no need to catalog these plugins since it is specific to this project only
    id("xyz.wagyourtail.jvmdowngrader") version "1.3.5"
    id("com.gradleup.shadow") version "9.0.2"
}

dependencies {
    compileOnly(libs.arc.core)
    compileOnly(libs.mindustry)

    implementation(project(":renderer"))
    implementation(project(":schema:widget")) // TODO doesnt renderer depend on this alr? Why do we need to re-declare this dep. Turns out we need to expose it as api instead of implementation
    implementation(project(":schema:compose"))
    api(project(":schema:api"))

    // Shadow deps will be excluded from being bundled into the published maven jar and are instead listed as a dependency.
    // Did this because apparently kotlin libs can't be shadowed without some functionality(such as inline functions) breaking
    shadow(kotlin("stdlib"))
    shadow(libs.kotlinx.coroutines.core)
    shadow(libs.compose.runtime)
//    api(libs.redwood.compose)

}

val archivesName: String = base.archivesName.get()

tasks.shadowJar {
    // The jar we publish to maven
}

tasks.register<ShadowJar>("modJar") {
    // The jar mindustry loads
    from(sourceSets.main.map { it.output })

    archiveFileName.set("${archivesName}Shadowed.jar")

    // We also want to bundle shadowed deps in this case as other mods will load these.
    configurations = listOf(
        project.configurations.runtimeClasspath.get(),
        project.configurations.shadow.get(),
    )

    // In our case, shadow() deps are technically api() deps
    minimize {
        exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib:.*"))
        exclude(dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core:.*"))
        exclude(dependency("androidx.compose.runtime:runtime:.*"))
    }

    val buildVer: String = rootProject.version.toString() + (project.findProperty("verSuffix")?.let { "-$it" } ?: "")
    val kotlinVer: String = libs.versions.kotlin.get()

    from("assets/") {
        include("**")
    }
    from("mod.hjson") {
        filter {
            it.replace("\$MODVER", buildVer).replace("\$KOTLINVER", kotlinVer)
        }
    }
}

tasks.register<Exec>("jarAndroid") {
    dependsOn("modJar") // d8 will desugar our stuff alr so there is no need to apply the jvm downgrader.
    val sdkRoot: String? = System.getenv("ANDROID_HOME") ?: System.getenv("ANDROID_SDK_ROOT")
    if(sdkRoot == null || !File(sdkRoot).exists()) throw GradleException("No valid Android SDK found. Ensure that ANDROID_HOME is set to your Android SDK directory.")

    val platformRoot = File("$sdkRoot/platforms/").listFiles()?.also { it.sort(); it.reverse() }?.find { File(it, "android.jar").exists()}
    if(platformRoot == null) throw GradleException("No android.jar found. Ensure that you have an Android platform installed.")
    val d8Path = System.getenv("d8_path") ?: "d8"
    // collect dependencies needed for desugaring
    val dependencies = (
            configurations.compileClasspath.get().toList() + configurations.runtimeClasspath.get().toList()
            ).joinToString(" ") { "--classpath ${it.path}" }
    val libPath = "--lib ${File(platformRoot, "android.jar").path}"
    workingDir(layout.buildDirectory.dir("libs"))

    commandLine("$d8Path $libPath $dependencies --min-api 14 --output ${archivesName}Android.jar ${archivesName}Shadowed.jar".split(" "))
}

tasks.downgradeJar {
    // Downgrade using the mod jar, not the default maven jar
    inputFile.set(tasks.named<ShadowJar>("modJar").flatMap { it.archiveFile })
}

tasks.shadeDowngradedApi {
    archiveFileName.set("${archivesName}Desktop.jar")
}

tasks.register<Jar>("deploy") {
    dependsOn("shadeDowngradedApi", "jarAndroid")
    archiveFileName.set("$archivesName.jar")
    val name = archivesName
    val buildDir = layout.buildDirectory
    from(
        buildDir.file("libs/${name}Desktop.jar").map { zipTree(it) },
        buildDir.file("libs/${name}Android.jar").map { zipTree(it) }
    )
    val fs = project.serviceOf<FileSystemOperations>()
    doLast {
        fs.delete {
            delete(buildDir.file("libs/${name}Desktop.jar"), buildDir.file("libs/${name}Android.jar"))
        }
    }
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["shadow"])

            artifactId = project.name
        }
    }
}