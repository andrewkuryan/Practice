plugins {
    java
    war
}

sourceSets {
    create("model") {
        java.srcDir(file("src/model/java"))
        resources.srcDir(file("src/model/resources"))
        compileClasspath += configurations["runtimeClasspath"]
    }
    create("repository") {
        java.srcDir(file("src/repository/java"))
        resources.srcDir(file("src/repository/resources"))
        compileClasspath += sourceSets["model"].output
        compileClasspath += configurations["runtimeClasspath"]
    }
    create("service") {
        java.srcDir(file("src/service/java"))
        resources.srcDir(file("src/service/resources"))
        compileClasspath += sourceSets["repository"].output + sourceSets["model"].output
        compileClasspath += configurations["runtimeClasspath"]
    }
    getByName("main") {
        compileClasspath += sourceSets["model"].output + sourceSets["service"].output +
                sourceSets["repository"].output
        runtimeClasspath += output + compileClasspath
    }
    getByName("test") {
        compileClasspath += sourceSets["model"].output + sourceSets["service"].output +
                sourceSets["repository"].output
        runtimeClasspath += output + compileClasspath
    }
}

repositories {
    mavenCentral()
    jcenter()
}

val springVersion = "5.2.3.RELEASE"
val springDataVersion = "2.2.4.RELEASE"
val springSecurityVersion = "5.2.1.RELEASE"
val jupiterVersion = "5.6.0"
val log4jVersion = "2.13.0"
val sl4jVersion = "2.0.0-alpha1"
val thymeleafVersion = "3.0.11.RELEASE"
val thymeleafExtrasVersion = "3.0.4.RELEASE"
val servletApiVersion = "4.0.1"
val mariaJdbcVersion = "2.5.3"
val mysqlConnectorVersion = "8.0.20"
val hibernateVersion = "5.4.10.Final"
val fileuploadVersion = "1.4"

dependencies {
    providedCompile("javax.servlet:javax.servlet-api:$servletApiVersion")
    implementation("org.springframework:spring-core:$springVersion")
    implementation("org.springframework:spring-webmvc:$springVersion")
    implementation("org.thymeleaf:thymeleaf-spring5:$thymeleafVersion")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity5:$thymeleafExtrasVersion")
    implementation("mysql:mysql-connector-java:$mysqlConnectorVersion")
    implementation("org.hibernate:hibernate-core:$hibernateVersion")
    implementation("org.springframework.data:spring-data-commons:$springDataVersion")
    implementation("org.springframework.data:spring-data-jpa:$springDataVersion")
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    implementation("org.slf4j:slf4j-api:$sl4jVersion")
    implementation("org.slf4j:slf4j-log4j12:$sl4jVersion")
    implementation("org.springframework.security:spring-security-core:$springSecurityVersion")
    implementation("org.springframework.security:spring-security-web:$springSecurityVersion")
    implementation("org.springframework.security:spring-security-config:$springSecurityVersion")
    implementation("commons-fileupload:commons-fileupload:$fileuploadVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
    testImplementation("org.springframework:spring-test:$springVersion")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
