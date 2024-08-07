package com.Art.Data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class ArtWork {
    private String id;
    private String title;
    private String description;
    private String artist;
    private String imageUrl;
}
