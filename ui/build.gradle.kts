import org.gradle.kotlin.dsl.support.serviceOf

plugins {
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
    api(kotlin("stdlib"))
    api(libs.kotlinx.coroutines.core)
    api(libs.compose.runtime)
//    api(libs.redwood.compose)

}

val archivesName: String = base.archivesName.get()

tasks.shadowJar {
    archiveFileName.set("${archivesName}Shadowed.jar")

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

    minimize()
//    enableAutoRelocation = true
}

tasks.register<Exec>("jarAndroid") {
    dependsOn("shadowJar") // d8 will desugar our stuff alr so there is no need to apply the jvm downgrader.
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
            from(components["java"])

            artifactId = project.name
        }
    }
}