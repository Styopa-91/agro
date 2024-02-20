package com.abi.agro_back.collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("exhibitions")
public class Exhibition {
    @Id
    private String id;

    private String title;

    private String description;

    private String content;

    private String image;

    private int phone;

    private String email;

    private String gallery_photos;

    private String address;

    private String website;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
