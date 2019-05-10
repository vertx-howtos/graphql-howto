plugins {
  java
  application
}

repositories {
  mavenCentral()
}

dependencies {
  val vertxVersion = "3.7.0"
  implementation("io.vertx:vertx-web-graphql:${vertxVersion}")
}

application {
  mainClassName = "io.vertx.howtos.graphql.GraphQLVerticle"
}

tasks.wrapper {
  gradleVersion = "5.2"
}
