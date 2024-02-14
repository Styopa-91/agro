package com.abi.agro_back.collection;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("image_pages")
public class ImagePage {
    @Id
    private String id;

    private String title;

    private String description;

    private String phone;

    private String image;

    private String email;

    private String address;

    private String website;

    private String owner;

}
