plugins {
    id("org.jetbrains.kotlin.jvm")
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    // The root gradle.properties opts out of the stdlib default dependency
    // (correct for the plugin module, which ships no code); opt back in here.
    implementation(kotlin("stdlib"))

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
    testImplementation("com.google.code.gson:gson:2.11.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<JavaExec>("generateThemes") {
    group = "gruvbox"
    description = "Regenerates the committed theme files from the palette definition."
    mainClass = "io.github.flyaif.gruvbox.generator.MainKt"
    classpath = sourceSets.main.get().runtimeClasspath
    args(rootProject.layout.projectDirectory.dir("src/main/resources/themes").asFile.absolutePath)
}
