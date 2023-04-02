package v1.attendee;

import akka.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

import javax.inject.Inject;

public class AttendeeExecutionContext extends CustomExecutionContext {

    @Inject
    public AttendeeExecutionContext(ActorSystem actorSystem) {
        super(actorSystem, "post.repository");
    }
}
