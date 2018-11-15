package app;

import model.Team;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeamsList {

    public List<Team> teamsList(String teamsPageUrl, String teamClassQuery, int siblingNum, Boolean saveImage) {
        Document doc = null;
        try {
            doc = Jsoup.connect(teamsPageUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements teamElements = doc.select("div:containsOwn(" + teamClassQuery + ")").first().siblingElements().get(siblingNum).children();

        List<Team> teams = new ArrayList<>();
        teamElements.stream()
//                .limit(3)
                .forEach(teamEl -> {
            Team team = new Team();
            ExtractTeamData extractTeamData = new ExtractTeamData(team);
            extractTeamData.teamInit(teamEl, teamClassQuery, saveImage);
            teams.add(team);
        });

        return teams;
    }
}
