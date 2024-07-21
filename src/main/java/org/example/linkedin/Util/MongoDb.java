package org.example.linkedin.Util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class MongoDb {

    private static final String connectionString = "mongodb://localhost:27017/Library";

        public static MongoCollection<Document> mongoCollection (String collectionName) {
            MongoClient mongoClient = MongoClients.create(connectionString);
            MongoDatabase database = mongoClient.getDatabase("Post");
            return database.getCollection(collectionName);
        }

}
