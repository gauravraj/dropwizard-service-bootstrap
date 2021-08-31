package com.myntra.hackathon.persist;

import com.myntra.hackathon.models.ecom.Listing;
import com.myntra.hackathon.models.enums.ReturnType;

import java.util.List;

public interface ListingDao {
    Listing createListing(Listing listing);
    Listing getListingById(String id);
    List<Listing> getListingByReturnTypes(List<ReturnType> returnTypes);
}
