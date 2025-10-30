package com.pubgateway.dispatcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Long id;
    
    @JsonProperty("descriptiveName")
    private String name;
    
    private String status;
    
    @JsonProperty("currencyCode")
    private String currency;
    
    @JsonProperty("timeZone")
    private String timezone;
    
    private String publisher;
    
    private Boolean manager;
    
    private Long level;
    
    private String clientCustomer;
}

