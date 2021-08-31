package com.myntra.hackathon.models.ecom;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myntra.hackathon.models.common.MediaUrls;
import com.myntra.hackathon.models.enums.Gender;
import com.myntra.hackathon.models.enums.Size;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    private ObjectId id;
    private String productId;
    private String name;
    private String description;
    private String primaryImage;
    private List<MediaUrls> mediaUrls;
    private Double mrp;
    private String brand;
    private String category;
    private List<String> tags;
    private List<Size> sizeVariants;
    private List<String> colors;
    private Gender gender;
}
