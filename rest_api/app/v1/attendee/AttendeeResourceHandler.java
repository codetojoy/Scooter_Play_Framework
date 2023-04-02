package v1.attendee;

import com.palominolabs.http.url.UrlBuilder;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Http;

import javax.inject.Inject;
import java.nio.charset.CharacterCodingException;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

/**
 * Handles presentation of Attendee resources, which map to JSON.
 */
public class AttendeeResourceHandler {

    private final AttendeeRepository repository;
    private final HttpExecutionContext ec;

    @Inject
    public AttendeeResourceHandler(AttendeeRepository repository, HttpExecutionContext ec) {
        this.repository = repository;
        this.ec = ec;
    }

    public CompletionStage<Stream<AttendeeResource>> find(Http.Request request) {
        return repository.list().thenApplyAsync(attendeeDataStream -> {
            return attendeeDataStream.map(data -> new AttendeeResource(data, link(request, data)));
        }, ec.current());
    }

    public CompletionStage<AttendeeResource> create(Http.Request request, AttendeeResource resource) {
        final AttendeeData data = new AttendeeData(resource.getName());
        return repository.create(data).thenApplyAsync(savedData -> {
            return new AttendeeResource(savedData, link(request, savedData));
        }, ec.current());
    }

    public CompletionStage<Optional<AttendeeResource>> lookup(Http.Request request,String id) {
        return repository.get(Long.parseLong(id)).thenApplyAsync(optionalData -> {
            return optionalData.map(data -> new AttendeeResource(data, link(request, data)));
        }, ec.current());
    }

    public CompletionStage<Optional<AttendeeResource>> update(Http.Request request,String id, AttendeeResource resource) {
        final AttendeeData data = new AttendeeData(resource.getName());
        return repository.update(Long.parseLong(id), data).thenApplyAsync(optionalData -> {
            return optionalData.map(op -> new AttendeeResource(op, link(request, op)));
        }, ec.current());
    }

    private String link(Http.Request request, AttendeeData data) {
        final String[] hostPort = request.host().split(":");
        String host = hostPort[0];
        int port = (hostPort.length == 2) ? Integer.parseInt(hostPort[1]) : -1;
        final String scheme = request.secure() ? "https" : "http";
        try {
            return UrlBuilder.forHost(scheme, host, port)
                .pathSegments("v1", "attendees", data.id.toString())
                .toUrlString();
        } catch (CharacterCodingException e) {
            throw new IllegalStateException(e);
        }
    }
}
