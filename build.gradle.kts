plugins {
  java
  application
}

repositories {
  mavenCentral()
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
  }
}

dependencies {
  implementation(platform("io.vertx:vertx-stack-depchain:4.4.0"))
  implementation("io.vertx:vertx-web-graphql")
}

application {
  mainClass.set("io.vertx.howtos.graphql.GraphQLVerticle")
}
