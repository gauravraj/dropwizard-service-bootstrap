package com.myntra.hackathon.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MongoDbConfiguration {
    String uri;
    String dbName;
}
