package com.myntra.hackathon.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpConfiguration {

    private int httpClientConnectTimeoutMs;
    private int httpClientSocketTimeoutMs;
    private int httpClientTimeToLiveInMs;
    private int httpClientMaxTotal;
    private int httpClientMaxTotalPerRoute;
}
