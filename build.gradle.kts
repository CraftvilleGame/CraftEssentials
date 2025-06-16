plugins {
    id("java")
    id("io.freefair.lombok") version "8.13.1"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.17"
}

group = "nl.craftsmp.essentials"
version = "1.0"

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    /* Paper */
    paperweight.paperDevBundle("1.21.5-R0.1-SNAPSHOT")

    /* Lamp */
    val lampVersion = "4.0.0-beta.19"
    implementation("io.github.revxrsal:lamp.common:$lampVersion")
    implementation("io.github.revxrsal:lamp.paper:$lampVersion")

}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

tasks {
    withType<JavaCompile> {
        options.compilerArgs.add("-parameters")
    }
}