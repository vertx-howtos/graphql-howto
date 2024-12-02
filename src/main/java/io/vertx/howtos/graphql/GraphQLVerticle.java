package io.vertx.howtos.graphql;

import graphql.GraphQL;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.graphql.GraphQLHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class GraphQLVerticle extends VerticleBase {

  // tag::start[]
  private Map<String, Task> tasks;

  @Override
  public Future<?> start() {
    tasks = initData();

    var graphQL = setupGraphQL();
    var graphQLHandler = GraphQLHandler.create(graphQL); // <1>

    var router = Router.router(vertx);
    router.route().handler(BodyHandler.create()); // <2>
    router.route("/graphql").handler(graphQLHandler); // <3>

    return vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080);
  }
  // end::start[]

  // tag::initData[]
  private Map<String, Task> initData() {
    return Stream.of(
      new Task("Learn GraphQL"),
      new Task("Build awesome GraphQL server"),
      new Task("Profit")
    ).collect(toMap(Task::id, identity()));
  }
  // end::initData[]

  // tag::setupGraphQL[]
  private GraphQL setupGraphQL() {
    var schema = vertx.fileSystem().readFileBlocking("tasks.graphqls").toString(); // <1>

    var schemaParser = new SchemaParser();
    var typeDefinitionRegistry = schemaParser.parse(schema); // <2>

    var runtimeWiring = newRuntimeWiring() // <3>
      .type("Query", builder -> builder.dataFetcher("allTasks", this::allTasks))
      .type("Mutation", builder -> builder.dataFetcher("complete", this::complete))
      .build();

    var schemaGenerator = new SchemaGenerator();
    var graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring); // <4>

    return GraphQL.newGraphQL(graphQLSchema).build(); // <5>
  }
  // end::setupGraphQL[]

  // tag::allTasks[]
  private List<Task> allTasks(DataFetchingEnvironment env) {
    boolean uncompletedOnly = env.getArgument("uncompletedOnly");
    return tasks.values().stream()
      .filter(task -> !uncompletedOnly || !task.completed())
      .collect(toList());
  }
  // end::allTasks[]

  // tag::complete[]
  private boolean complete(DataFetchingEnvironment env) {
    String id = env.getArgument("id");
    var task = tasks.get(id);
    if (task == null) {
      return false;
    }
    tasks.put(id, new Task(task.id(), task.description(), true));
    return true;
  }
  // end::complete[]

  // tag::main[]
  public static void main(String[] args) {
    var vertx = Vertx.vertx(); // <1>
    vertx.deployVerticle(new GraphQLVerticle()).await(); // <2>
    System.out.println("Verticle deployed");
  }
  // end::main[]
}
