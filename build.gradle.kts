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
  implementation(platform("io.vertx:vertx-stack-depchain:5.0.0.CR2"))
  implementation("io.vertx:vertx-web-graphql")
}

application {
  mainClass.set("io.vertx.howtos.graphql.GraphQLVerticle")
}
