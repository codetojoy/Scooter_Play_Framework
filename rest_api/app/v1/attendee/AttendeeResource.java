package v1.attendee;

/**
 * Resource for the API.  This is a presentation class for frontend work.
 */
public class AttendeeResource {
    private String id;
    private String name;

    public AttendeeResource() {
    }

    public AttendeeResource(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public AttendeeResource(AttendeeData data, String linkStub) {
        this.id = data.id.toString();
        this.name = data.name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
