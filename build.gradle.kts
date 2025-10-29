plugins {
	java
	id("org.springframework.boot") version "3.5.7"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "mo.gov.dsscu"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("com.google.code.gson:gson:2.10.1")
	implementation("at.favre.lib:bcrypt:0.10.2")
	// jwt
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.15.2")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
	implementation("org.apache.httpcomponents:httpclient:4.5.13")
	implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
	implementation("org.hibernate:hibernate-core:7.1.1.Final")
	implementation("com.microsoft.sqlserver:mssql-jdbc:12.4.1.jre11")
	compileOnly("org.projectlombok:lombok:1.18.30")
	annotationProcessor("org.projectlombok:lombok:1.18.30")
	runtimeOnly("com.h2database:h2")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
