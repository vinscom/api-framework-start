package in.erail.tutorial;

import in.erail.service.RESTServiceImpl;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.eventbus.Message;
import io.vertx.reactivex.core.shareddata.LocalMap;
import java.util.ArrayList;
import java.util.List;

public class SessionPostService extends RESTServiceImpl {

  @Override
  public void process(Message<JsonObject> pMessage) {
    LocalMap<String, List<String>> data = getVertx().sharedData().<String,List<String>>getLocalMap("data");
    List<String> sessions = data.getOrDefault("sessions", new ArrayList<>());
    String paramSession = pMessage.body().getString("session");
    sessions.add(paramSession);
    data.put("sessions", sessions);
    pMessage.reply(new JsonObject());
  }

}
