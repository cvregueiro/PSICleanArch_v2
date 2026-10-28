// Domain layer: business rules in plain Java.
// It is a java-library, so it cannot depend on Android, Retrofit or any other framework.
plugins {
    `java-library`
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(17)
}

dependencies {
    testImplementation(libs.junit)
}
