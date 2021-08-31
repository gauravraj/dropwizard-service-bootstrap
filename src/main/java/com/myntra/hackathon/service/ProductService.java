package com.myntra.hackathon.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myntra.hackathon.models.common.MediaUrls;
import com.myntra.hackathon.models.ecom.Listing;
import com.myntra.hackathon.models.ecom.Product;
import com.myntra.hackathon.models.enums.*;
import com.myntra.hackathon.models.response.EchoResponse;
import com.myntra.hackathon.persist.ListingDao;
import com.myntra.hackathon.persist.ProductDao;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Singleton
public class ProductService {

    ProductDao productDao;
    ListingDao listingDao;
    ObjectMapper objectMapper;

    @Inject
    public ProductService(ProductDao productDao, ListingDao listingDao, ObjectMapper objectMapper) {
        this.productDao = productDao;
        this.listingDao = listingDao;
        this.objectMapper = objectMapper;
    }

    public void seedData() {
        log.info("Seeding data...");
        try {
            //String jsonText = new String(Files.readAllBytes(Paths.get("src/main/resources/data/MenClothing.json")));
            JSONParser parser = new JSONParser();
            JSONObject menClothingObject = (JSONObject) parser.parse(new FileReader("src/main/resources/data/MenClothing.json"));
            JSONArray productsMen = (JSONArray)((JSONObject)menClothingObject.get("hits")).get("hits");
            for (Object productMen : productsMen) {
                JSONObject mensProduct = (JSONObject) ((JSONObject) productMen).get("_source");
                String productId = (String) mensProduct.get("productId");
                JSONObject catalogEagObject = (JSONObject) mensProduct.get("catalogEag");
                String title = (String) catalogEagObject.get("title");
            }
            log.info("Text reading complete");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public EchoResponse getEchoResponse(String inputMessage) {
        //productDao.test();
//        Product insertedProduct = productDao.createProduct(Product.builder()
//                        .brand("brand1")
//                        .category("category1")
//                        .productId("productId1")
//                        .description("description1")
//                        .mrp(999.0)
//                        .name("productname1")
//                        .primaryImage("http://google.com")
//                        .mediaUrls(Lists.newArrayList(new MediaUrls(MediaType.IMAGE, "url1"), new MediaUrls(MediaType.IMAGE, "url2")))
//                        .sizeVariants(Lists.newArrayList(Size.MEDIUM, Size.LARGE))
//                        .tags(Lists.newArrayList("TAG1", "tag2"))
//                .build());
//        log.info(insertedProduct.toString());
//        List<Product> product = productDao.getProductsByIds(Lists.newArrayList("6127affc5bf8751e6961411c"));
//        log.info(product.toString());

//        Listing listing = listingDao.createListing(Listing.builder()
//                        .productId("6127affc5bf8751e6961411c")
//                        .currency(Currency.INR)
//                        .itemCondition(ItemCondition.LIKE_NEW)
//                        .mrp(500.0)
//                        .purchasePrice(400.0)
//                        .sellingPrice(799.0)
//                        .returnType(ReturnType.SELL)
//                        .stockAvailability(1)
//                        .usageType(UsageType.MODERATE)
//                        .userId("gaurav.raj")
//                        .uploadedUrls(Lists.newArrayList(new MediaUrls(MediaType.IMAGE, "url1"), new MediaUrls(MediaType.IMAGE, "url2")))
//                .build());

        Listing listing = listingDao.getListingById("6127c9e338b6f57da0fb2c69");
        log.info(listing.toString());
        List<Listing> listings = listingDao.getListingByReturnTypes(Lists.newArrayList(ReturnType.DONATE));
        return EchoResponse.builder().message("Hello "+inputMessage).build();
    }
}
