package com.LTI.CourseService.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Enrollment {

    @Id
    @GeneratedValue
    @Column(name="enrollment_id")
    private int enrollmentId;
    @Column(name="user_id")
    private long wileyUserId;
    @Column(name = "user_name")
    private String userName;
    @ManyToOne
    private Course course;

    public Enrollment(int enrollmentId, long wileyUserId, String userName, long wileyCourseId) {
        this.enrollmentId = enrollmentId;
        this.wileyUserId = wileyUserId;
        this.userName = userName;
        this.course = new Course(wileyCourseId, "", "", "","","","");
    }
}
