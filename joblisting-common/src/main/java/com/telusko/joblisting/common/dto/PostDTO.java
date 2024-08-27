package com.telusko.joblisting.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.util.List;

@Data
public class PostDTO {
    private String desc;
    @NotNull(message = "Experience cannot be null") // Annotation with validation api dependency
    private Integer exp;
    @NotBlank(message = "Profile cannot be blank")
    private String profile;
    private List<String> techs;
}
