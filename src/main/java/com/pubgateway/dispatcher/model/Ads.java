package com.pubgateway.dispatcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ads {
    private Long id;
    
    private Long accountId;
    @JsonProperty("adGroupId")
    private Long groupId;
    
    private Long campaignId;
    
    private String name;
    private String status;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("raw")
    private String raw;
    
    private String creativeUrl;
    private String publisher;
}

