package com.tgt.upcurve.aggregatorapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageRequest {
    @JsonProperty("content")
    String content;
}
