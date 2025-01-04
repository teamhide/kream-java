plugins {
    id("java")
}

tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = true
}

dependencies {
    implementation(project(":core"))
}
