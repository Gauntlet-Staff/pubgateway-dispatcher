package com.pubgateway.dispatcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Keyword {
    private Long id;

    private Long accountId;
    
    private String text;
    
    @JsonProperty("matchType")
    private String matchType;
    
    private String status;
    
    @JsonProperty("adGroupName")
    private String adGroupName;
    
    private Long campaignId;
    
    private Long adGroupId;
    
    @JsonProperty("raw")
    private String raw;
    
    private String publisher;
}

