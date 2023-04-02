package v1.attendee;

import javax.persistence.*;

@Entity
@Table(name = "attendee")
public class AttendeeData {

    public AttendeeData() {
    }

    public AttendeeData(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    public String name;
}
