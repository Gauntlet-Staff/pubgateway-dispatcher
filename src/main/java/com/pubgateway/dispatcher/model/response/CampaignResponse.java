package com.pubgateway.dispatcher.model.response;

import com.pubgateway.dispatcher.model.Campaign;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignResponse {
    private List<Campaign> campaigns;
}

