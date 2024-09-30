plugins {
    id("java")
    id("com.diffplug.spotless") version "6.22.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
    reports {
        html.required.set(true)
        junitXml.required.set(true)
    }
}

spotless {
    java {
        googleJavaFormat("1.17.0")
        target("src/**/*.java")
    }
}