// Data layer: implements the repositories of the domain with a remote source (the APIs)
// and a local source (JSON files).
// Plain Java library: it depends on the domain and on Retrofit/OkHttp/Gson, never on Android or the presentation.
plugins {
    `java-library`
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(17)
}

dependencies {
    // The public API of this module (repositories) uses the types of the domain
    api(project(":domain"))

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson) { exclude(module = "gson") }
    implementation(libs.gson)
    implementation(libs.okhttp.logging.interceptor)

    // Testing
    testImplementation(libs.junit)
}
