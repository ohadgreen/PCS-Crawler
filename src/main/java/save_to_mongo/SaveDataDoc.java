package save_to_mongo;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import config.ReadConfigurations;
import model.Rider;
import model.Team;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class SaveDataDoc {
    private String dbUri;

    public SaveDataDoc(String dbUri) {
        this.dbUri = dbUri;
    }

    public MongoDatabase dbConnection(){
        MongoClientURI uri  = new MongoClientURI(this.dbUri);
        MongoClient client = new MongoClient(uri);
        return client.getDatabase(uri.getDatabase());
    }

    public void saveTeamListToMongo(List<Team> teamList, String myCollection){
        MongoDatabase db = dbConnection();
        MongoCollection<Document> collection = db.getCollection(myCollection);

        List<Document> teamDocList = new ArrayList<>();
        for(Team team : teamList){
            teamDocList.add(teamToDoc(team));
        }
        collection.insertMany(teamDocList);
    }

    public void saveRiderListToMongo(List<Rider> riderList, String myCollection){
        MongoDatabase db = dbConnection();
        MongoCollection<Document> collection = db.getCollection(myCollection);

        List<Document> riderDocList = new ArrayList<>();
        for(Rider rider : riderList){
            riderDocList.add(riderToDoc(rider));
        }
        collection.insertMany(riderDocList);
    }

    private Document teamToDoc(Team team){
        Document teamDoc = new Document();
        teamDoc.put("displayName", team.getDisplayName());
        teamDoc.put("normName", team.getNormName());
        teamDoc.put("classification", team.getClassification());
        teamDoc.put("pageUrl", team.getPageUrl());

        return teamDoc;
    }

    private Document riderToDoc(Rider rider){
        Document riderDoc = new Document();
        riderDoc.put("displayName", rider.getDisplayName());
        riderDoc.put("normName", rider.getNormName());
        riderDoc.put("nationality", rider.getNationality());
        riderDoc.put("team", rider.getTeam());
        riderDoc.put("pageUrl", rider.getPageUrl());
        riderDoc.put("age", rider.getAge());
        riderDoc.put("height", rider.getHeight());
        riderDoc.put("weight", rider.getWeight());
        riderDoc.put("grandTours", rider.getGrandTours());
        riderDoc.put("classicRaces", rider.getClassicRaces());
        riderDoc.put("proWins", rider.getProWins());

        return riderDoc;
    }
}
