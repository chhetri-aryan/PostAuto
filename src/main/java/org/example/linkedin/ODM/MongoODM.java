package org.example.linkedin.ODM;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.linkedin.Model.Session;
import org.example.linkedin.Model.User;
import org.example.linkedin.Util.MongoDb;

import java.util.Date;

public class MongoODM {
    private static final MongoCollection<Document> collection = MongoDb.mongoCollection("User");

    public boolean insertIntoDbUser(User u) {
        Document user = new Document("_id", new ObjectId())
                .append("username", u.getUsername())
                .append("email", u.getEmail())
                .append("phone", u.getPhone())
                .append("accessKey", u.getUrl())
                .append("password", u.getPassword())
                .append("date", new Date());

        InsertOneResult result = collection.insertOne(user);

        return result.wasAcknowledged();
    }

    public boolean isUser(String s) {
        Document found = collection.find(Filters.eq("email",s)).first();
        return found != null;
    }

    public User getUser(String s) {
        Document found = collection.find(Filters.eq("email",s)).first();
        assert found != null;
        return new User(found.getString("username"),
                found.getString("email"),
                found.getString("phone"),
                found.getString("accessKey"),
                found.getString("password")
                );
    }

    public void updateURL(String key) {
        collection.updateOne(Filters.eq("email", Session.getCurrentUser().getEmail()), Updates.set("accessKey", key));

    }

}
