package com.Art.Data.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String userName;
    private String password;
    private String email;
    private userRoles role;
}
