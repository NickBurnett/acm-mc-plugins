import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
plugins {
    id("java") apply true;
    id("com.github.johnrengelman.shadow") version "6.0.0" apply true
}
group = "co.acmutd"
version = "1.0.0-SNAPSHOT"
repositories {
    mavenCentral()
    jcenter()
    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}
dependencies {
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
}
project(":API") {
    apply(plugin = "java")
    apply(plugin = "com.github.johnrengelman.shadow")
    repositories {
        mavenCentral()
        jcenter()
        maven {
            url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        }
    }
    dependencies {
        compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
        compileOnly("com.googlecode.json-simple:json-simple:1.1.1")
    }
}
project(":CORE") {
    apply(plugin="java")
    apply(plugin = "com.github.johnrengelman.shadow")
    repositories {
        mavenCentral()
        jcenter()
        maven {
            url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        }
    }
    dependencies {
        compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
        compileOnly("com.googlecode.json-simple:json-simple:1.1.1")
        implementation(project(":API"))
    }
    tasks.withType<ShadowJar> {
        archiveBaseName.set("ACM")
        archiveClassifier.set("DEV")
        archiveVersion.set("1.0.0")
    }
}