package com.LTI.CourseService.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private long wileyId;
    private String givenName;
    private String familyName;
    private String email;

}
