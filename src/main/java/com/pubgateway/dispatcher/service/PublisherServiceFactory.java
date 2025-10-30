package com.pubgateway.dispatcher.service;

import com.pubgateway.dispatcher.client.GooglePubGatewayClient;
import com.pubgateway.dispatcher.client.MetaPubGatewayClient;
import com.pubgateway.dispatcher.enumeration.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublisherServiceFactory {

    private final GooglePubGatewayClient googleClient;
    private final MetaPubGatewayClient metaClient;

    public PublisherService getService(Publisher publisher) {
        return switch (publisher) {
            case GOOGLE -> new GooglePublisherService(googleClient);
            case META -> new MetaPublisherService(metaClient);
        };
    }
}

