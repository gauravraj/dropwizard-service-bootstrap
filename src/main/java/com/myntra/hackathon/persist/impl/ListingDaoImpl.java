package com.myntra.hackathon.persist.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import com.myntra.hackathon.models.ecom.Listing;
import com.myntra.hackathon.models.ecom.Product;
import com.myntra.hackathon.models.enums.ReturnType;
import com.myntra.hackathon.persist.ListingDao;
import org.assertj.core.util.Lists;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class ListingDaoImpl implements ListingDao {

    private ObjectMapper objectMapper;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Listing> listingCollection;
    private static final String collectionName = "listings";

    @Inject
    public ListingDaoImpl(ObjectMapper objectMapper, MongoDatabase mongoDatabase) {
        this.objectMapper = objectMapper;
        this.mongoDatabase = mongoDatabase;
        this.listingCollection = this.mongoDatabase.getCollection(collectionName, Listing.class);
    }

    @Override
    public Listing createListing(Listing listing) {
        InsertOneResult insertResult = listingCollection.insertOne(listing);
        Bson filter = Filters.eq("_id", insertResult.getInsertedId());
        return listingCollection.find(filter).first();
    }

    @Override
    public Listing getListingById(String id) {
        Bson filter = Filters.eq("_id", new ObjectId(id));
        return listingCollection.find(filter).first();
    }

    @Override
    public List<Listing> getListingByReturnTypes(List<ReturnType> returnTypes) {
        List<String> returnTypeStrings = returnTypes.stream().map(Enum::toString).collect(Collectors.toList());
        Bson filter = Filters.in("returnType", returnTypeStrings);
        try (MongoCursor<Listing> findCursor = listingCollection.find(filter).iterator()) {
            List<Listing> selectedProducts = Lists.newArrayList();
            while (findCursor.hasNext()) {
                selectedProducts.add(findCursor.next());
            }
            return selectedProducts;
        }
    }
}
