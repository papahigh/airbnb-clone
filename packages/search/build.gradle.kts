plugins {
    kotlin("jvm") version "1.9.25"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    implementation(libs.org.json)
    compileOnly(libs.opensearch.client)

    testCompileOnly(libs.junit.params)
    testRuntimeOnly(libs.junit.platform)
    testImplementation(libs.junit.jupiter)
}

tasks.test {
    useJUnitPlatform()
}
