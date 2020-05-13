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
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Course {

    @Id
    @GeneratedValue
    @Column(name="course_id")
    private long wileyCourseId;
    @Column(name="title")
    private String title;
    @Column(name="label")
    private String label;
    @Column(name="type")
    private String type;
    @Column(name="createDate")
    private String createDate;

}
