plugins {
    id("java")
    kotlin("jvm") version "1.9.25" apply false
    kotlin("plugin.spring") version "1.9.25" apply false
    kotlin("plugin.jpa") version "1.9.25" apply false
    id("org.springframework.boot") version "3.4.4" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
    id("org.hibernate.orm") version "6.6.11.Final" apply false
    id("org.graalvm.buildtools.native") version "0.10.6" apply false
}

subprojects {
    repositories { mavenCentral() }
}
