package com.myntra.hackathon.models.common;

import com.myntra.hackathon.models.enums.MediaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MediaUrls {
    private MediaType mediaType;
    private String url;
}
