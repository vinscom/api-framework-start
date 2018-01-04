package in.erail.tutorial;

import in.erail.glue.Glue;
import in.erail.server.Server;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.Timeout;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(VertxUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SessionTest {

  @Rule
  public Timeout rule = Timeout.seconds(2000);

  @Test
  public void testAPostRequest(TestContext context) {

    Async async = context.async(5);

    Server server = Glue.instance().<Server>resolve("/in/erail/server/Server");
    String content = new JsonObject().put("session", "random").toString();

    for (int i = 0; i < 5; ++i) {
      server
              .getVertx()
              .createHttpClient()
              .post(server.getPort(), server.getHost(), "/v1/session")
              .putHeader("content-type", "application/json")
              .putHeader("content-length", Integer.toString(content.length()))
              .handler(response -> {
                context.assertEquals(response.statusCode(), 200, response.statusMessage());
                async.countDown();
              })
              .write(content)
              .end();
    }

  }

  @Test
  public void testBGetRequest(TestContext context) {

    Async async = context.async(2);
    Server server = Glue.instance().<Server>resolve("/in/erail/server/Server");
    server
            .getVertx()
            .createHttpClient()
            .get(server.getPort(), server.getHost(), "/v1/session")
            .handler(response -> {
              context.assertEquals(response.statusCode(), 200, response.statusMessage());
              response.bodyHandler((event) -> {
                JsonArray data = event.toJsonArray();
                context.assertEquals(5, data.size());
                async.countDown();
              });
              async.countDown();
            })
            .end();
  }

}
