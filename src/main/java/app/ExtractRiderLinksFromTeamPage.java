package app;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ExtractRiderLinksFromTeamPage {
    public Set<String> allRiderLinks(String teamUrl){
        Set<String> riderUrlList = new HashSet<>();
        Document doc = null;
        try {
            doc = Jsoup.connect(teamUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements links = doc.select("a");

        for (Element link : links){
            String absUrl = link.attr("abs:href");
            if(absUrl.contains("rider/") && !absUrl.contains("statistics/")) {
//                System.out.println(absUrl);
                riderUrlList.add(absUrl);
            }
        }
        return riderUrlList;
    }
}
