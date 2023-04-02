package v1.attendee;

import net.jodah.failsafe.CircuitBreaker;
import net.jodah.failsafe.Failsafe;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * A repository that provides a non-blocking API with a custom execution context
 * and circuit breaker.
 */
@Singleton
public class JPAAttendeeRepository implements AttendeeRepository {

    private final JPAApi jpaApi;
    private final AttendeeExecutionContext ec;
    private final CircuitBreaker<Optional<AttendeeData>> circuitBreaker = new CircuitBreaker<Optional<AttendeeData>>().withFailureThreshold(1).withSuccessThreshold(3);

    @Inject
    public JPAAttendeeRepository(JPAApi api, AttendeeExecutionContext ec) {
        this.jpaApi = api;
        this.ec = ec;
    }

    @Override
    public CompletionStage<Stream<AttendeeData>> list() {
        return supplyAsync(() -> wrap(em -> select(em)), ec);
    }

    @Override
    public CompletionStage<AttendeeData> create(AttendeeData attendeeData) {
        return supplyAsync(() -> wrap(em -> insert(em, attendeeData)), ec);
    }

    @Override
    public CompletionStage<Optional<AttendeeData>> get(Long id) {
        return supplyAsync(() -> wrap(em -> Failsafe.with(circuitBreaker).get(() -> lookup(em, id))), ec);
    }

    @Override
    public CompletionStage<Optional<AttendeeData>> update(Long id, AttendeeData attendeeData) {
        return supplyAsync(() -> wrap(em -> Failsafe.with(circuitBreaker).get(() -> modify(em, id, attendeeData))), ec);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Optional<AttendeeData> lookup(EntityManager em, Long id) throws SQLException {
        throw new SQLException("Call this to cause the circuit breaker to trip");
        //return Optional.ofNullable(em.find(AttendeeData.class, id));
    }

    private Stream<AttendeeData> select(EntityManager em) {
        TypedQuery<AttendeeData> query = em.createQuery("SELECT p FROM AttendeeData p", AttendeeData.class);
        return query.getResultList().stream();
    }

    private Optional<AttendeeData> modify(EntityManager em, Long id, AttendeeData attendeeData) throws InterruptedException {
        final AttendeeData data = em.find(AttendeeData.class, id);
        if (data != null) {
            data.name = attendeeData.name;
        }
        Thread.sleep(10000L);
        return Optional.ofNullable(data);
    }

    private AttendeeData insert(EntityManager em, AttendeeData attendeeData) {
        return em.merge(attendeeData);
    }
}
