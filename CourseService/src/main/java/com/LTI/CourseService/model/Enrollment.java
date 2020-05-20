package com.LTI.CourseService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "enrollment_id")
    private long enrollmentId;
    @Column(name = "user_id")
    private long wileyUserId;
    @Column(name = "user_name")
    private String userName;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    public Enrollment(final long enrollmentId, final long wileyUserId, final String userName, final long wileyCourseId) {
        this.enrollmentId = enrollmentId;
        this.wileyUserId = wileyUserId;
        this.userName = userName;
        this.course = new Course(wileyCourseId, "", "", "", "", "", "", null);
    }
}
