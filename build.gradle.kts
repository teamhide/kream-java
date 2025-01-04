plugins {
	java
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.diffplug.spotless") version "6.20.0"
	id("jacoco")
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

allprojects {
	group = "com.teamhide"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "java")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "com.diffplug.spotless")
	apply(plugin = "jacoco")

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("org.springframework.boot:spring-boot-starter-data-redis")
		implementation("org.springframework.boot:spring-boot-starter-security")
		implementation("org.springframework.boot:spring-boot-starter-validation")
		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("org.flywaydb:flyway-core")
		implementation("org.flywaydb:flyway-mysql")
		runtimeOnly("com.mysql:mysql-connector-j")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("org.springframework.security:spring-security-test")
		testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	}

	spotless {
		java {
			removeUnusedImports()
			replaceRegex(
				"Remove wildcard imports",
				"import\\s+[^\\*\\s]+\\*;(\\r\\n|\\r|\\n)",
				"$1"
			)
			googleJavaFormat()
			importOrder(
				"java",
				"jakarta",
				"javax",
				"lombok",
				"org.springframework",
				"",
				"\\#",
				"org.junit",
				"\\#org.junit",
				"com.teamhide",
				"\\#com.teamhide"
			)
			indentWithTabs(2)
			indentWithSpaces(4)
			trimTrailingWhitespace()
			endWithNewline()
		}
	}

	tasks.jacocoTestReport {
		dependsOn(tasks.test)
		reports {
			html.required.set(true)
			xml.required.set(true)
			csv.required.set(false)
		}
		finalizedBy(tasks.jacocoTestCoverageVerification)
		classDirectories.setFrom(
			files(classDirectories.files.map {
				fileTree(it) {
					exclude(
						"**/*Application*",
						"**/Q*Entity*",
					)
				}
			})
		)
	}

	tasks.jacocoTestCoverageVerification {
		val queryDslClasses = ('A'..'Z').map { "*.Q${it}*" }
		violationRules {
			rule {
				element = "CLASS"
				limit {
					counter = "LINE"
					value = "COVEREDRATIO"
					minimum = "1.00".toBigDecimal()
				}
				classDirectories.setFrom(sourceSets.main.get().output.asFileTree)
				excludes = listOf(
					"com.teamhide.kream.KreamApplication",
				) + queryDslClasses
			}
		}
	}

	val preSpotless by tasks.registering {
		dependsOn("spotlessCheck", "spotlessApply")
		finalizedBy(tasks.jacocoTestReport)
	}

	val testAll by tasks.registering {
		dependsOn("spotlessCheck", "test", "jacocoTestReport", "jacocoTestCoverageVerification")
		tasks["test"].mustRunAfter(tasks["spotlessCheck"])
		tasks["jacocoTestReport"].mustRunAfter(tasks["test"])
		tasks["jacocoTestCoverageVerification"].mustRunAfter(tasks["jacocoTestReport"])
	}

	tasks.test {
		useJUnitPlatform()
		systemProperties["spring.profiles.active"] = "test"
	}
}

project(":presentation") {
	dependencies {
		implementation(project(":core"))
	}
}

project(":infrastructure") {
	dependencies {
		implementation(project(":core"))
	}
}

project(":application") {
	dependencies {
		implementation(project(":core"))
	}
}

project(":domain") {
	dependencies {
		implementation(project(":core"))
	}
}
