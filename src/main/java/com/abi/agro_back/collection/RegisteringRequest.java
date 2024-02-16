package com.abi.agro_back.collection;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("registering_requests")
public class RegisteringRequest {
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;
}
