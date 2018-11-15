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
import java.util.stream.Stream;

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
        Elements keyStats = doc.select("b:contains(Key stats:)").first().siblingElements();
        keyStatsInit(keyStats);

        Element displayNameElement = doc.select("h1").first();
        this.setDisplayName(displayNameElement);

        String normName = this.setNormName(riderPageUrl);
        this.saveRiderImage(doc, normName, teamName, saveImage);

        Element riderInfoElement = doc.select("b:contains(Date of birth)").first().parent();
        this.setRiderValues(riderInfoElement);
        this.setNormName(riderPageUrl);

        this.rider.setNormName(normName);
        this.rider.setTeam(teamName);
        this.rider.setPageUrl(riderPageUrl);
    }

    protected void keyStatsInit(Elements keyStatsElements){
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

    protected void saveRiderImage(Element doc, String normName, String teamName, boolean saveImage){
        File riderImagesDir = new File(ReadConfigurations.getProperty("rider_images_path"));
        Elements images = doc.select("img");
        try {
            Element riderImage = images.stream().filter(img -> img.attr("src").contains("riders/")).collect(Collectors.toList()).get(0);
            //        System.out.println(riderImage.attr("abs:src"));
            if(saveImage && riderImage != null) {
                SaveImage.imageToFile(riderImage.attr("abs:src"), riderImagesDir.getAbsolutePath() + "/" + teamName, normName);
            }
        }
        catch (Exception e){
            System.out.println("can't find image for " + normName + " " + images);
        }
    }

    protected String setNormName(String riderPageUrl){
        return riderPageUrl.substring(riderPageUrl.lastIndexOf("rider/") + 6);
    }

    protected void setDisplayName(Element displayNameElement){
        String h1 = displayNameElement.text();
        String[] h1Split = h1.split("»");
        this.rider.setDisplayName(h1Split[0].trim());
    }

    protected void setRiderValues (Element riderInfoElement){
        String dob = riderInfoElement.text();
        String[] infoWordsArray = dob.split("\\s");
        String age = null, nationality = null, height = null, weight = null;
        String prevWord = "";
        for(int i = 0; i <= 15; i++){
//            System.out.println(i + "-" + infoWordsArray[i]);
            if(infoWordsArray[i].equals("Nationality:")){
                age = extractAgeFromBrackets(prevWord);
            }
            if(prevWord.equals("Nationality:")){
                nationality = infoWordsArray[i];
            }
            if(prevWord.equals("Height:")){
                height = infoWordsArray[i];
            }
            if(prevWord.equals("Weight:")){
                weight = infoWordsArray[i];
            }

            prevWord = infoWordsArray[i];
        }
        this.rider.setAge(age);
        this.rider.setNationality(nationality);
        this.rider.setHeight(height);
        this.rider.setWeight(weight);
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
