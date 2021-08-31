package com.myntra.hackathon;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.myntra.hackathon.configuration.ESConfiguration;
import com.myntra.hackathon.configuration.MfyConfiguration;
import com.myntra.hackathon.persist.ListingDao;
import com.myntra.hackathon.persist.ProductDao;
import com.myntra.hackathon.persist.impl.ListingDaoImpl;
import com.myntra.hackathon.persist.impl.ProductDaoImpl;
import com.myntra.hackathon.resource.v1.ProductController;
import com.myntra.hackathon.service.ProductService;
import com.palominolabs.metrics.guice.MetricsInstrumentationModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MfyServiceModule extends AbstractModule {

    private MetricRegistry metricRegistry;
    private ObjectMapper objectMapper;

    public MfyServiceModule(MetricRegistry metricRegistry, ObjectMapper objectMapper) {
        this.metricRegistry = metricRegistry;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void configure() {
        super.configure();
        // Bind resources as singleton
        bind(ProductController.class).in(Singleton.class);

        //Bind Manager classes as singleton
        bind(ProductService.class).in(Singleton.class);

        //Bind DAOs as singleton
        bind(ProductDao.class).to(ProductDaoImpl.class).in(Singleton.class);
        bind(ListingDao.class).to(ListingDaoImpl.class).in(Singleton.class);
        //bind(TrendsDAO.class).to(ESTrendsDAO.class).in(Singleton.class);

        install(new MetricsInstrumentationModule(metricRegistry));
    }

    @Provides
    @Singleton
    public ObjectMapper getObjectMapper() {
        objectMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        return objectMapper;
    }

    @Provides
    @Singleton
    public MongoDatabase getMongoDatabase(MfyConfiguration mfyConfiguration) {
        try {
            CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                    MongoClientSettings.getDefaultCodecRegistry(),
                    CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
            );

            ConnectionString mongoConnectionString = new ConnectionString(mfyConfiguration.getMongoDbConfiguration().getUri());

            MongoClientSettings settings = MongoClientSettings.builder()
                    .codecRegistry(codecRegistry)
                    .applyConnectionString(mongoConnectionString)
                    .applyToConnectionPoolSettings(builder -> builder.maxSize(15000)
                            .maxConnectionIdleTime(2, TimeUnit.MINUTES))
                    .writeConcern(WriteConcern.MAJORITY.withJournal(true).withWTimeout(60, TimeUnit.SECONDS))
                    .build();
            MongoClient mongoClient = MongoClients.create(settings);
            return mongoClient.getDatabase(mfyConfiguration.getMongoDbConfiguration().getDbName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Provides
    @Singleton
    public RestHighLevelClient getRestHighLevelClient(MfyConfiguration mfyConfiguration) {
        ESConfiguration esConfig = mfyConfiguration.getEsConfiguration();

        int totalNodes = esConfig.getHosts().size();
        HttpHost hosts[] = new HttpHost[totalNodes];
        for(int i= 0 ; i< totalNodes ; i++) {
            hosts[i] = new HttpHost(
                    esConfig.getHosts().get(i),
                    esConfig.getPorts().get(i),
                    esConfig.getSchemes().get(i)
            );
        }
        return new RestHighLevelClient(
                RestClient.builder(
                        hosts
                ));
    }
}