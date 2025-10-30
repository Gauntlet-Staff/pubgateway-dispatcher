package com.pubgateway.dispatcher.model.response;

import com.pubgateway.dispatcher.model.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdGroupResponse {
    private List<Group> adGroups;
}

