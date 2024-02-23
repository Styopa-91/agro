package com.abi.agro_back.collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("image_pages")
public class ImagePage {
    @Id
    private String id;

    private String title;

    private String description;

    private String phone;

    private String image;

    private String oblast;

    private String video;

    private String email;

    private String address;

    private String website;

    private String owner;

    private List<String> keyWords;

}
