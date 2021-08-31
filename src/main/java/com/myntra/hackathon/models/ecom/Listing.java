package com.myntra.hackathon.models.ecom;

import com.myntra.hackathon.models.common.MediaUrls;
import com.myntra.hackathon.models.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Listing {
    private ObjectId id;
    private String productId;
    private Currency currency;
    private Double mrp;
    private Double sellingPrice;
    private Double purchasePrice;
    private Integer stockAvailability;
    private UsageType usageType;
    private ItemCondition itemCondition;
    private Size size;
    private ReturnType returnType;
    private List<MediaUrls> uploadedUrls;
    private String userId;
}
