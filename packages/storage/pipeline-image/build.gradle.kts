dependencies {
    implementation(project(":packages:storage:pipeline"))
    implementation(libs.metadata.extractor)
    implementation(libs.ai.djl.api)
    testImplementation(libs.ai.djl.pytorch)
}
