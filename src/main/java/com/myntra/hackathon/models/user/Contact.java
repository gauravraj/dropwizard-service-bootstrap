package com.myntra.hackathon.models.user;

import com.myntra.hackathon.models.enums.ContactType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {
    ContactType contactType;
    private String value;
}
