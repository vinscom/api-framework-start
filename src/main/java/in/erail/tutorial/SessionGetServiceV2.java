package in.erail.tutorial;

import io.vertx.core.json.JsonArray;
import io.vertx.reactivex.core.shareddata.LocalMap;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vinay
 */
public class SessionGetServiceV2 extends SessionGetService {

  @Override
  public JsonArray getSessions() {
    LocalMap<String, List<String>> data = getVertx().sharedData().<String, List<String>>getLocalMap("data");
    List<String> sessions = data.getOrDefault("sessions", new ArrayList<>());

    JsonArray result = new JsonArray();
    sessions.forEach((s) -> {
      result.add(s);
    });

    return result;
  }

}
