package com.telusko.joblisting.common.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document("User")
@Data
public class User implements Serializable {
    @Id
    private String id;
    @Indexed(name = "first_user_index", direction = IndexDirection.DESCENDING)
    private String userName;
    private String password;
}
