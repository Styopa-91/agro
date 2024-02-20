package com.abi.agro_back.collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("banners")
public class Banner {
    @Id
    private String  id;

    private String title;

    private String content;

    private String image;

    private LocalDate createdAt;

}
