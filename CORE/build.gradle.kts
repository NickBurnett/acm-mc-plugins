plugins {
    java
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
    implementation(project(":API"))
}
