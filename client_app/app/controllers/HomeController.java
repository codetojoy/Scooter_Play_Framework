package controllers;

import play.mvc.*;
import play.libs.ws.*;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.*;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller implements WSBodyReadables {

    private final WSClient ws;
    private static final String ATTENDEES_ENDPOINT = "http://localhost:9000/v1/attendees";

    @Inject
    public HomeController(WSClient ws) {
      this.ws = ws;
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    public CompletionStage<Result> go() {
        return ws.url(ATTENDEES_ENDPOINT)
            .get()
            .thenApply(response -> {
                System.out.println("TRACER api call OK cp 0");
                var json = response.asJson();
                var jsonStr = json.toPrettyString();
                System.out.println("TRACER api call OK cp 1");
                return ok(views.html.attendees.render(jsonStr));
            });
    }
    /*
     *
    public CompletionStage<Result> go() {
        // implements WSBodyReadables or use WSBodyReadables.instance.json()
        return ws.url(url).get().thenApply(r -> {
            r.getBody(json());
            return ok(views.html.attendees.render());
        });
    }
     */
}
