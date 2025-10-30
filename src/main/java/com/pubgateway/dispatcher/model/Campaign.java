package com.pubgateway.dispatcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {
    private Long id;
    private String accountId;
    private String name;
    private String status;
    
    @JsonProperty("servingStatus")
    private String servingStatus;
    
    @JsonProperty("startDate")
    private String startDate;
    
    @JsonProperty("endDate")
    private String endDate;
    
    @JsonProperty("optimizationScore")
    private Double optimizationScore;
    
    @JsonProperty("advertisingChannelType")
    private String advertisingChannelType;
    
    @JsonProperty("biddingStrategyType")
    private String biddingStrategyType;
    
    private String objective;
    private String budget;
    private String publisher;
}

