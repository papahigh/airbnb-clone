subprojects {

    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        add("compileOnly", rootProject.libs.jetbrains.annotations)
        add("compileOnly", rootProject.libs.slf4j.api)

        add("compileOnly", rootProject.libs.lombok)
        add("testCompileOnly", rootProject.libs.lombok)
        add("annotationProcessor", rootProject.libs.lombok)
        add("testAnnotationProcessor", rootProject.libs.lombok)

        add("testImplementation", rootProject.libs.hamcrest.matchers)
        add("testImplementation", rootProject.libs.hamcrest.record)
        add("testImplementation", rootProject.libs.slf4j.api)
        add("testImplementation", rootProject.libs.logback.classic)

        add("testImplementation", rootProject.libs.junit.jupiter)
        add("testRuntimeOnly", rootProject.libs.junit.platform)
    }

    extensions.configure<JavaPluginExtension> {
        toolchain { languageVersion.set(JavaLanguageVersion.of(21)) }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

