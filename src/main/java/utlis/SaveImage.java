package utlis;

import app.Main;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveImage {

    public static boolean imageToFile(String imgUrl, String filePath, String fileName) {
        try {
            URL url = new URL(imgUrl);
            URLConnection urlConn = url.openConnection();
            urlConn.addRequestProperty("User-Agent", "Mozilla/4.76");

            File imageFile = new File(filePath);
            if(!imageFile.exists()){
                try{
                    imageFile.mkdirs();
                }catch (SecurityException se) {
                    System.out.println(se.getMessage());
                }
            }
            String outputFile = filePath + "/" + fileName + ".png";
            InputStream is = urlConn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, 4 * 1024);
            OutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFile));

            byte[] buffer = new byte[4096];
            int n = -1;
            while ( (n = bis.read(buffer)) != -1){
                bos.write(buffer, 0, n);
            }
            bos.close();
            return true;

        } catch (IOException e) {
            System.out.println("error saving image");
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
}
