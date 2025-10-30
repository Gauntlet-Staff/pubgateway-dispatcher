package com.pubgateway.dispatcher.controller;

import com.pubgateway.dispatcher.client.GooglePubGatewayClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
@Tag(name = "Test", description = "Test endpoints for verifying service connectivity")
public class TestController {

    private final GooglePubGatewayClient googleClient;

    @GetMapping("/google/hello")
    @Operation(
        summary = "Test Google service connectivity",
        description = "Simple hello endpoint to verify that the dispatcher can communicate with the Google PubGateway Plugin",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully connected to Google service"),
            @ApiResponse(responseCode = "503", description = "Google service unavailable")
        }
    )
    public String testGoogleHello() {
        return googleClient.hello();
    }
}

