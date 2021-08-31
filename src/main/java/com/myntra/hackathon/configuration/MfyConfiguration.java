package com.myntra.hackathon.configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MfyConfiguration extends Configuration {

    @Valid
    @NotNull
    private SwaggerBundleConfiguration swagger;

    @NotNull
    @JsonProperty("http")
    private HttpConfiguration httpConfiguration;

    @NotNull
    @JsonProperty("ElasticSearch")
    private ESConfiguration esConfiguration;

    @NotNull
    private String prometheusName;

    @NotNull
    private String prometheusPath;

    @NotNull
    @JsonProperty("mongodb")
    private MongoDbConfiguration mongoDbConfiguration;
}
