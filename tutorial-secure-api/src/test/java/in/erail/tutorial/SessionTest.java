package in.erail.tutorial;

import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;
import in.erail.glue.Glue;
import in.erail.server.Server;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.Timeout;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class SessionTest {

  @Rule
  public Timeout rule = Timeout.seconds(2000);

  @Test
  public void testPostRequest(TestContext context) {

    Async async = context.async(5);

    Server server = Glue.instance().<Server>resolve("/in/erail/server/Server");
    String content = new JsonObject().put("session", "random").toString();

    for (int i = 0; i < 5; ++i) {
      server
              .getVertx()
              .createHttpClient()
              .post(server.getPort(), server.getHost(), "/v1/session")
              .putHeader(HttpHeaders.CONTENT_TYPE, MediaType.JSON_UTF_8.toString())
              .putHeader(HttpHeaders.CONTENT_LENGTH, Integer.toString(content.length()))
              .handler(response -> {
                context.assertEquals(response.statusCode(), 200, response.statusMessage());
                async.countDown();
              })
              .write(content)
              .end();
    }

  }

  @Test
  public void testGetRequest(TestContext context) {

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
