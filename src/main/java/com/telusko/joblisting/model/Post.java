package com.telusko.joblisting.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document("JobPost")
@Data
@CompoundIndex(def = "{'profile':1, 'exp':1}", name = "profile_exp_first_compound_index")
public class Post implements Serializable {
    @Id
    private String id;
    private String desc;
    private Integer exp;
    @Indexed(name = "first_profile_index", direction = IndexDirection.ASCENDING)
    private String profile;
    private List<String> techs;
}
