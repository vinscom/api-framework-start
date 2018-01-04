package in.erail.tutorial;

import io.vertx.core.json.JsonArray;
import io.vertx.reactivex.core.shareddata.LocalMap;
import java.util.Arrays;

public class SessionGetServiceV2 extends SessionGetService {

  @Override
  public JsonArray getSessions() {
    LocalMap<String, String> data = getVertx().sharedData().<String, String>getLocalMap("data");
    String sessions = data.getOrDefault("sessions", "");

    JsonArray result = new JsonArray();
    Arrays.stream(sessions.split(",")).forEach((s) -> {
      result.add(s);
    });

    return result;
  }

}
