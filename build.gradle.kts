plugins {
    id("java")
    id("io.freefair.lombok") version "8.13.1"
    id("com.gradleup.shadow") version "8.3.0"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.17"
}

group = "nl.craftsmp.essentials"
version = "1.0"

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    /* Paper */
    paperweight.paperDevBundle("1.21.5-R0.1-SNAPSHOT")

    /* Lamp */
    val lampVersion = "4.0.0-rc.12"
    implementation("io.github.revxrsal:lamp.common:$lampVersion")
    implementation("io.github.revxrsal:lamp.bukkit:$lampVersion")
    implementation("io.github.revxrsal:lamp.brigadier:$lampVersion")

    /* Reflection */
    implementation("org.reflections:reflections:0.10.2")

    /* Database */
    compileOnly("com.zaxxer:HikariCP:6.3.0")
    compileOnly("org.mariadb.jdbc:mariadb-java-client:3.5.3")
    compileOnly("org.xerial:sqlite-jdbc:3.49.1.0")
    implementation("com.github.Mindgamesnl:storm:e1f961b480")


}


java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

tasks {
    withType<JavaCompile> {
        options.compilerArgs.add("-parameters")
    }
}