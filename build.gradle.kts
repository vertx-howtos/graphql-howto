plugins {
  java
  application
}

repositories {
  mavenCentral()
}

dependencies {
  val vertxVersion = "4.0.0"
  implementation("io.vertx:vertx-web-graphql:${vertxVersion}")
}

application {
  mainClassName = "io.vertx.howtos.graphql.GraphQLVerticle"
}

tasks.wrapper {
  gradleVersion = "5.2"
}
