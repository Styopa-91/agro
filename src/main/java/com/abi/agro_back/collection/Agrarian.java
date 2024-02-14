package com.abi.agro_back.collection;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("agrarians")
public class Agrarian {
    @Id
    private String id;

    private String title;

    private String head;

    private String phone;

    private String email;

    private String address;

    private String oblast;

    private String region;

    private String EDRPOU;

    private String area;

    private String website;

    private String prod_types;

}
