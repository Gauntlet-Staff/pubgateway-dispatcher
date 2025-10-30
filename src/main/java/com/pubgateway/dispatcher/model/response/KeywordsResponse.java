package com.pubgateway.dispatcher.model.response;

import com.pubgateway.dispatcher.model.Keyword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeywordsResponse {
    private List<Keyword> keywords;
}

