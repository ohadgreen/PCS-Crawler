package app;

import config.ReadConfigurations;
import model.Rider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utlis.SaveImage;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ExtractRiderData {
    private Rider rider;

    public ExtractRiderData(Rider rider) {
        this.rider = rider;
    }

    public void riderInit(String riderPageUrl, String teamName, boolean saveImage){
        Document doc = null;
        try {
            doc = Jsoup.connect(riderPageUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setNormNameFromUrl(riderPageUrl);
        this.setDisplayName(doc);
        this.setGeneralValues(doc);
        this.keyStatsInit(doc);
        this.saveRiderImage(doc, teamName, saveImage);
        this.rider.setTeam(teamName);
        this.rider.setPageUrl(riderPageUrl);
    }

    protected void keyStatsInit(Document doc){
        try {
            Elements keyStatsElements = doc.select("b:contains(Key stats:)").first().siblingElements();
            if (keyStatsElements != null){
                for(Element e : keyStatsElements){
                    String spanText = e.select("span").first().text();
                    String[] catArray = spanText.split("\\s+");
                    if(catArray[0].equals("#ProWins")){
                        this.rider.setProWins(Integer.parseInt(catArray[1]));
                    }
                    if(catArray[0].equals("#GrandTours")){
                        this.rider.setGrandTours(Integer.parseInt(catArray[1]));
                    }
                    if(catArray[0].equals("#Classics")){
                        this.rider.setClassicRaces(Integer.parseInt(catArray[1]));
                    }
                }
            }
        } catch (Exception e){
            System.out.println("can't find key stats element for " + this.rider.getNormName() + " " + e);
        }
    }

    protected void saveRiderImage(Element doc, String teamName, boolean saveImage){
        File riderImagesDir = new File(ReadConfigurations.getProperty("rider_images_path"));
        Elements images = doc.select("img");
        try {
            Element riderImage = images.stream().filter(img -> img.attr("src").contains("riders/")).collect(Collectors.toList()).get(0);
            //        System.out.println(riderImage.attr("abs:src"));
            if(saveImage && riderImage != null) {
                SaveImage.imageToFile(riderImage.attr("abs:src"), riderImagesDir.getAbsolutePath() + "/" + teamName, this.rider.getNormName());
            }
        }
        catch (Exception e){
            System.out.println("can't find image for " + this.rider.getNormName() + " " + images + " " + e);
        }
    }

    protected void setNormNameFromUrl(String riderPageUrl){
        String normName = riderPageUrl.substring(riderPageUrl.lastIndexOf("rider/") + 6);
        this.rider.setNormName(normName);
    }

    protected void setDisplayName(Document doc){
        try {
            String displayNameElementText = doc.select("h1").first().text();
            String[] h1Split = displayNameElementText.split("»");
            this.rider.setDisplayName(h1Split[0].trim());
        }
        catch (Exception e){
            System.out.println("can't find display name for " + this.rider.getNormName() + " " + e );
        }
    }

    protected void setGeneralValues(Document doc){
        try {
            Element riderInfoElement = doc.select("b:contains(Date of birth)").first().parent();
            String dob = riderInfoElement.text();
            String[] infoWordsArray = dob.split("\\s");
            String age = null, nationality = null, height = null, weight = null;
            String prevWord = "";
            for (int i = 0; i <= 15; i++) {
                if (infoWordsArray[i].equals("Nationality:")) {
                    age = extractAgeFromBrackets(prevWord);
                }
                if (prevWord.equals("Nationality:")) {
                    nationality = infoWordsArray[i];
                }
                if (prevWord.equals("Height:")) {
                    height = infoWordsArray[i];
                }
                if (prevWord.equals("Weight:")) {
                    weight = infoWordsArray[i];
                }
                prevWord = infoWordsArray[i];
            }
            this.rider.setAge(age);
            this.rider.setNationality(nationality);
            this.rider.setHeight(height);
            this.rider.setWeight(weight);
        }
        catch (Exception e){
            System.out.println("can't find general info for " + this.rider.getNormName() + " " + e);
        }
    }

    protected String extractAgeFromBrackets(String rawAge){
        Matcher m = Pattern.compile("\\((.*?)\\)").matcher(rawAge);
        String age = null;
        if(m.find()){
            age = m.group(1);
        }
        return age;
    }
}
