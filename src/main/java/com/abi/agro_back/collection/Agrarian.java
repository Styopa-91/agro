package com.abi.agro_back.collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.net.URL;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("agrarians")
public class Agrarian {
    @Id
    private String id;

    private String title;

    private String head;

    private String phone;

    private Photo image;

    private String email;

    private String address;

    private String oblast;

    private String region;

    private String oldRegion;

    private boolean isPriority;

    private URL redirect;

    private String EDRPOU;

    private String area;

    private String website;

    private List<String> prod_types;

}
