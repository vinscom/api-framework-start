package in.erail.tutorial;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import in.erail.model.Event;
import in.erail.model.RequestEvent;
import in.erail.model.ResponseEvent;
import in.erail.service.RESTServiceImpl;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.shareddata.LocalMap;
import java.util.List;

/**
 * Store session data in LocalMap.
 */
public class SessionPostService extends RESTServiceImpl {
  
  @Override
  public MaybeSource<Event> process(Maybe<Event> pEvent) {
    return pEvent.flatMap(this::handle);
  }

  public Maybe<Event> handle(Event pEvent) {
    LocalMap<String, String> data = getVertx().sharedData().getLocalMap("data");
    String sessions = data.getOrDefault("sessions", "");
    JsonObject paramSession = new JsonObject(pEvent.getRequest().bodyAsString());
    List<String> items = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(sessions + "," + paramSession.getString("session"));
    data.put("sessions", Joiner.on(",").join(items));
    return Maybe.just(pEvent);
  }

}
