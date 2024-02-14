package com.abi.agro_back.collection;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document("pages")
public class Page {
    @Id
    private String id;

    private String title;

    private String description;

    private String image;

    private LocalDate createdAt;

}
