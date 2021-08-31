package com.myntra.hackathon.models.ecom;

import com.myntra.hackathon.models.enums.BlogCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blog {
    private ObjectId _id;
    private String title;
    private String description;
    private String author;
    private Long publishedDate;
    private List<String> tags;
    private List<BlogCategory> blogCategories;
}
