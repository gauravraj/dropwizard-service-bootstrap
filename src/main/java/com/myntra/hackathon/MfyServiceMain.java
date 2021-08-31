package com.myntra.hackathon;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.google.inject.Stage;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.myntra.hackathon.configuration.MfyConfiguration;
import io.dropwizard.Application;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.dropwizard.DropwizardExports;
import io.prometheus.client.exporter.MetricsServlet;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;


@Slf4j
public class MfyServiceMain extends Application<MfyConfiguration> {

    private GuiceBundle<MfyConfiguration> guiceBundle;

    @Override
    public void initialize(Bootstrap<MfyConfiguration> bootstrap) {
        guiceBundle = GuiceBundle.<MfyConfiguration>newBuilder()
                .setConfigClass(MfyConfiguration.class)
                .addModule(new MfyServiceModule(bootstrap.getMetricRegistry(), bootstrap.getObjectMapper()))
                .enableAutoConfig("com.myntra.hackathon")
                .build(Stage.DEVELOPMENT);

        bootstrap.addBundle(guiceBundle);
        bootstrap.addBundle(new SwaggerBundle<MfyConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(MfyConfiguration mfyConfiguration) {
                return mfyConfiguration.getSwagger();
            }
        });
    }

    @Override
    @Inject
    public void run(MfyConfiguration mfyConfiguration, Environment environment) throws Exception {

        environment.jersey().setUrlPattern("/api/*");
        environment.jersey().register(new JsonProcessingExceptionMapper(true));
        environment.lifecycle().addServerLifecycleListener(server -> log.info("MFY service started"));

        CollectorRegistry collectorRegistry = new CollectorRegistry();
        collectorRegistry.register(new DropwizardExports(environment.metrics()));
        environment.admin()
                .addServlet(mfyConfiguration.getPrometheusName(), new MetricsServlet(collectorRegistry))
                .addMapping(mfyConfiguration.getPrometheusPath());

        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        ObjectMapper objectMapper = guiceBundle.getInjector().getInstance(ObjectMapper.class);
        objectMapper.findAndRegisterModules();
        provider.setMapper(objectMapper);
        environment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.ALWAYS);
        environment.jersey().register(provider);
    }

    public static void main(String[] args) throws Exception {
        log.info("Starting MFY service...");
        new MfyServiceMain().run(args);
    }
}
