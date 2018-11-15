package app;

import config.ReadConfigurations;
import model.Team;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utlis.SaveImage;

import java.io.File;

public class ExtractTeamData {
    private Team team;

    public ExtractTeamData(Team team) {
        this.team = team;
    }

    public void teamInit(Element teamEl, String classification, Boolean saveImage){
//        System.out.println(teamEl);
        String pageUrl = teamEl.attr("abs:href");
        String displayName = teamEl.attr("title");
        String normName = pageUrl.substring(pageUrl.lastIndexOf("team/") + 5, pageUrl.lastIndexOf("-2018"));
        Elements img = teamEl.select("img");
        this.saveTeamImage(saveImage, img, normName);

        String classMinusTeams = classification.substring(0, classification.indexOf(" Teams"));
//        Team team = new Team(name);
        this.team.setDisplayName(displayName);
        this.team.setNormName(normName);
        this.team.setClassification(classMinusTeams);
        this.team.setPageUrl(pageUrl);
        System.out.println(team.toString());
//        return team;
    }

    private void saveTeamImage(boolean saveImage, Elements imageElements, String normName){
        if(saveImage && imageElements != null) {
            String imgSrc = imageElements.attr("abs:src");
            File teamImagesDir = new File(ReadConfigurations.getProperty("team_images_path"));
            SaveImage.imageToFile(imgSrc, teamImagesDir.getAbsolutePath(), normName);
        }
    }
}
