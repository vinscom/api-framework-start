package in.erail.tutorial;

import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;
import in.erail.glue.Glue;
import in.erail.route.OpenAPI3RouteBuilder;
import in.erail.server.Server;
import io.reactivex.Observable;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.reactivex.ext.web.client.WebClient;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
@Timeout(value = 10, timeUnit = TimeUnit.MINUTES)
public class SessionTest {

  @Test
  public void testPostRequest(VertxTestContext testContext) {

    OpenAPI3RouteBuilder routeBuilder = Glue.instance().<OpenAPI3RouteBuilder>resolve("/in/erail/route/OpenAPI3RouteBuilder");
    routeBuilder.setSecurityEnable(false);
    Server server = Glue.instance().<Server>resolve("/in/erail/server/Server");
    String content = new JsonObject().put("session", "random").toString();

    Observable
            .range(0, 5)
            .flatMapSingle((t) -> {
              return WebClient
                      .create(server.getVertx())
                      .post(server.getHttpServerOptions().getPort(), server.getHttpServerOptions().getHost(), "/v1/session")
                      .putHeader(HttpHeaders.CONTENT_TYPE, MediaType.JSON_UTF_8.toString())
                      .rxSendJsonObject(new JsonObject().put("session", "random"));
            })
            .doOnNext(response -> Assertions.assertEquals(200, response.statusCode()))
            .ignoreElements()
            .andThen(
                    WebClient
                            .create(server.getVertx())
                            .get(server.getHttpServerOptions().getPort(), server.getHttpServerOptions().getHost(), "/v1/session")
                            .putHeader(HttpHeaders.CONTENT_TYPE, MediaType.JSON_UTF_8.toString())
                            .rxSend()
            )
            .doOnSuccess(response -> Assertions.assertEquals(200, response.statusCode()))
            .doOnSuccess((t) -> {
              JsonArray data = t.bodyAsJsonArray();
              Assertions.assertEquals(5, data.size());
            })
            .subscribe(t -> testContext.completeNow(), err -> testContext.failNow(err));
  }

}
