package com.myntra.hackathon.models.user;

import com.myntra.hackathon.models.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private ObjectId _id;
    private String name;
    private Gender gender;
    private List<Address> addresses;
    private List<Contact> contacts;
    private String profileImage;
}
