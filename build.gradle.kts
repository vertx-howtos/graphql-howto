plugins {
  java
  application
}

repositories {
  mavenCentral()
}

dependencies {
  val vertxVersion = "4.2.1"
  implementation("io.vertx:vertx-web-graphql:${vertxVersion}")
}

application {
  mainClass.set("io.vertx.howtos.graphql.GraphQLVerticle")
}

tasks.wrapper {
  gradleVersion = "7.2"
}
