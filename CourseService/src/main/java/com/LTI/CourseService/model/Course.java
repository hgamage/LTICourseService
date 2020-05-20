package com.LTI.CourseService.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Course {

    @Id
    @GeneratedValue
    @Column(name = "course_id")
    private long wileyCourseId;
    @Column(name = "title")
    private String title;
    @Column(name = "type")
    private String type;
    @Column(name = "createDate")
    private String createDate;
    @Column(name = "institution_id")
    private String institutionId;
    @Column(name = "start_date")
    private String courseStartDate;
    @Column(name = "end_date")
    private String courseEndDate;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;

}
