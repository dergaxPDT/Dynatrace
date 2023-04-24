plugins {
    java
    id("org.springframework.boot") version "2.6.1"
    id("io.spring.dependency-management") version "1.1.0"
    id("io.freefair.lombok") version "8.0.1"
}

group = "com.pdt"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.14.2")
    implementation("io.springfox:springfox-swagger2:2.9.2")
    implementation("io.springfox:springfox-swagger-ui:2.9.2")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
