plugins {
	id("org.springframework.boot") version Versions.springBoot
	id("io.spring.dependency-management") version Versions.springBootDepMan
	kotlin("jvm") version Versions.kotlin
	kotlin("plugin.spring") version Versions.kotlin
}

group = "com.example.markiiimark"
version = Versions.project
java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

dependencies {
	implementation(Dependencies.springBoot)
	implementation(Dependencies.jackson)
	implementation(Dependencies.kotlinStdlib)
	compileOnly(Dependencies.lombok)
	annotationProcessor(Dependencies.lombok)
	testImplementation(Dependencies.springBootTest)
}