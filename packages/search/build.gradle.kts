subprojects {

    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        add("compileOnly", rootProject.libs.jetbrains.annotations)
        add("compileOnly", rootProject.libs.slf4j.api)

        add("testCompileOnly", rootProject.libs.junit.params)
        add("testRuntimeOnly", rootProject.libs.junit.platform)
        add("testImplementation", rootProject.libs.junit.jupiter)
    }

    extensions.configure<JavaPluginExtension> {
        toolchain { languageVersion.set(JavaLanguageVersion.of(21)) }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}