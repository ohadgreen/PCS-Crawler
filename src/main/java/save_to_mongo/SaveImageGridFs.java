package save_to_mongo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import config.ReadConfigurations;

import java.io.File;
import java.io.IOException;

public class SaveImageGridFs {
    private static final String IMAGES_COLLECTION = "images-java";

    public void saveImageIntoMongoDB(String image) {
        SaveDataDoc saveData = new SaveDataDoc(ReadConfigurations.getProperty("mongo_uri_dev"));
        saveData.dbConnection();
        MongoClientURI uri  = new MongoClientURI(ReadConfigurations.getProperty("mongo_uri_dev"));
        MongoClient client = new MongoClient(uri);
        DB db = client.getDB(uri.getDatabase());

        String imageFileName = image + ".png";
        File imageFile = new File(ReadConfigurations.getProperty("team_images_path") + imageFileName);
        GridFS gfsPhoto = new GridFS(db, IMAGES_COLLECTION);
        GridFSInputFile gfsFile = null;
        try {
            gfsFile = gfsPhoto.createFile(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        gfsFile.setFilename(image);
        gfsFile.save();
    }
}
