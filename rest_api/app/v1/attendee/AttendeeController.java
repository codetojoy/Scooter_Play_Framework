package v1.attendee;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@With(AttendeeAction.class)
public class AttendeeController extends Controller {

    private HttpExecutionContext ec;
    private AttendeeResourceHandler handler;

    @Inject
    public AttendeeController(HttpExecutionContext ec, AttendeeResourceHandler handler) {
        this.ec = ec;
        this.handler = handler;
    }

    public CompletionStage<Result> list(Http.Request request) {
        return handler.find(request).thenApplyAsync(attendees -> {
            final List<AttendeeResource> attendeeList = attendees.collect(Collectors.toList());
            return ok(Json.toJson(attendeeList));
        }, ec.current());
    }

    public CompletionStage<Result> show(Http.Request request, String id) {
        return handler.lookup(request, id).thenApplyAsync(optionalResource -> {
            return optionalResource.map(resource ->
                ok(Json.toJson(resource))
            ).orElseGet(Results::notFound);
        }, ec.current());
    }

    public CompletionStage<Result> update(Http.Request request, String id) {
        JsonNode json = request.body().asJson();
        AttendeeResource resource = Json.fromJson(json, AttendeeResource.class);
        return handler.update(request, id, resource).thenApplyAsync(optionalResource -> {
            return optionalResource.map(r ->
                    ok(Json.toJson(r))
            ).orElseGet(Results::notFound
            );
        }, ec.current());
    }

    public CompletionStage<Result> create(Http.Request request) {
        JsonNode json = request.body().asJson();
        final AttendeeResource resource = Json.fromJson(json, AttendeeResource.class);
        return handler.create(request, resource).thenApplyAsync(savedResource -> {
            return created(Json.toJson(savedResource));
        }, ec.current());
    }
}
