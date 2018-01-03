package in.erail.tutorial;

import in.erail.service.RESTServiceImpl;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.eventbus.Message;

public class SessionService extends RESTServiceImpl {

  @Override
  public void process(Message<JsonObject> pMessage) {
    pMessage.reply(new JsonObject().put("status", "done"));
  }

}
