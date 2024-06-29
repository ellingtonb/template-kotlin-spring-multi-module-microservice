dependencies {
    // Internal
    implementation(project(":api"))
    implementation(project(":business"))

    // AWS
    implementation("com.amazonaws:aws-java-sdk-s3")
    implementation("com.amazonaws:aws-java-sdk-sts")
}
