dependencies {
    implementation(project(":packages:search:search-client"))
    implementation(project(":packages:search:search-dsl"))
    implementation(libs.org.json)
    compileOnly(libs.opensearch.client)
}
