package in.erail.tutorial;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import in.erail.model.RequestEvent;
import in.erail.model.ResponseEvent;
import in.erail.service.RESTServiceImpl;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.shareddata.LocalMap;
import java.util.List;

/**
 * Store session data in LocalMap.
 */
public class SessionPostService extends RESTServiceImpl {

  @Override
  public ResponseEvent process(RequestEvent pRequest) {
    LocalMap<String, String> data = getVertx().sharedData().getLocalMap("data");
    String sessions = data.getOrDefault("sessions", "");
    JsonObject paramSession = new JsonObject(pRequest.bodyAsString());
    List<String> items = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(sessions + "," + paramSession.getString("session"));
    data.put("sessions", Joiner.on(",").join(items));
    return new ResponseEvent();
  }

}
