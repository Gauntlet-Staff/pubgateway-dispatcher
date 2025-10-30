package com.pubgateway.dispatcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    private Long id;
    private Long accountId;
    private Long campaignId;
    private String name;
    private String status;
    
    @JsonProperty("type")
    private String type;
    
    private String bidStrategy;
    private String publisher;
}

