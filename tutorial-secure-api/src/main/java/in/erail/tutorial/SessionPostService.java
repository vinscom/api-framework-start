package in.erail.tutorial;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import in.erail.common.FrameworkConstants;
import in.erail.service.RESTServiceImpl;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.eventbus.Message;
import io.vertx.reactivex.core.shareddata.LocalMap;
import java.util.List;

/**
 * Store session data in LocalMap.
 */
public class SessionPostService extends RESTServiceImpl {

  @Override
  public void process(Message<JsonObject> pMessage) {
    LocalMap<String, String> data = getVertx().sharedData().getLocalMap("data");
    String sessions = data.getOrDefault("sessions", "");
    JsonObject paramSession = pMessage.body().getJsonObject(FrameworkConstants.RoutingContext.Json.BODY);
    List<String> items = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(sessions + "," + paramSession.getString("session"));
    data.put("sessions", Joiner.on(",").join(items));
    pMessage.reply(new JsonObject());
  }

}
