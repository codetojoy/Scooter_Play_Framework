package v1.attendee;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public interface AttendeeRepository {

    CompletionStage<Stream<AttendeeData>> list();

    CompletionStage<AttendeeData> create(AttendeeData attendeeData);

    CompletionStage<Optional<AttendeeData>> get(Long id);

    CompletionStage<Optional<AttendeeData>> update(Long id, AttendeeData attendeeData);
}

