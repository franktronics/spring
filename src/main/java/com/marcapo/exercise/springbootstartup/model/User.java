package com.marcapo.exercise.springbootstartup.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "user")
public class User {
    @Id
    private String username;
    private String password;
}
