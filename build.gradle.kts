plugins {
    `java-library`
}

repositories {
    jcenter()
}

dependencies {
    implementation("org.assertj:assertj-db:1.20")
    implementation("org.apache.poi:poi:4.0.1")
    implementation("org.apache.poi:poi-ooxml:4.0.1")

    testImplementation("junit:junit:4.12")
}
