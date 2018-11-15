package app;

import model.Rider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RiderList {

    public List<Rider> riderList(String riderLink) {
        List<Rider> riderList = new ArrayList<>();
        Document doc = null;
        try {
            doc = Jsoup.connect(riderLink).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String h1 = doc.select("h1").first().text();
        String displayName = getDisplayName(h1);
        System.out.println("h1 = " + h1);
        Element riderInfoElem = doc.select("b:contains(Date of birth)").first().parent();
        String dob = riderInfoElem.text();

        String[] infoWords = dob.split("\\s");
//        printWordsArray(infoWords);
        return riderList;
    }

    protected String getDisplayName(String h1){
        String[] h1Split = h1.split("&nbsp;");
        return h1Split[1].trim();
    }

    private static void setRiderValues (Rider rider, String[] words){
        String age = null, nationality = null, height = null, weight = null;
        String prevWord = "";
        for(int i = 0; i <= 15; i++){
            System.out.println(i + "-" + words[i]);
            if(words[i].equals("Nationality:")){
                age = extractAgeFromBrackets(prevWord);
            }
            if(prevWord.equals("Nationality:")){
                nationality = words[i];
            }
            if(prevWord.equals("Height:")){
                height = words[i];
            }
            if(prevWord.equals("Weight:")){
                weight = words[i];
            }

            prevWord = words[i];
        }

        rider.setAge(age);
        rider.setNationality(nationality);
        rider.setHeight(height);
        rider.setWeight(weight);
    }

    private static String extractAgeFromBrackets(String rawAge){
        Matcher m = Pattern.compile("\\((.*?)\\)").matcher(rawAge);
        if(m.find())
            return m.group(1);
        else return null;
    }
}
