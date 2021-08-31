package com.myntra.hackathon.persist.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import com.myntra.hackathon.models.ecom.Product;
import com.myntra.hackathon.persist.ProductDao;
import org.assertj.core.util.Lists;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDaoImpl implements ProductDao {

    private ObjectMapper objectMapper;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Product> productCollection;
    private static final String collectionName = "products";

    @Inject
    public ProductDaoImpl(ObjectMapper objectMapper, MongoDatabase mongoDatabase) {
        this.objectMapper = objectMapper;
        this.mongoDatabase = mongoDatabase;
        this.productCollection = this.mongoDatabase.getCollection(collectionName, Product.class);
    }

    @Override
    public Product createProduct(Product product) {
        InsertOneResult insertResult = productCollection.insertOne(product);
        Bson filter = Filters.eq("_id", insertResult.getInsertedId());
        return productCollection.find(filter).first();
    }

    @Override
    public Product getProductById(String id) {
        Bson filter = Filters.eq("_id", new ObjectId(id));
        return productCollection.find(filter).first();
    }

    @Override
    public List<Product> getProductsByIds(List<String> ids) {
        List<ObjectId> productIds = ids.stream().map(ObjectId::new).collect(Collectors.toList());
        Bson filter = Filters.in("_id", productIds);

        try (MongoCursor<Product> findCursor = productCollection.find(filter).iterator()) {
            List<Product> selectedProducts = Lists.newArrayList();
            while (findCursor.hasNext()) {
                selectedProducts.add(findCursor.next());
            }
            return selectedProducts;
        }
    }
}
