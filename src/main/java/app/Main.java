package app;

import config.ReadConfigurations;
import model.Rider;
import model.Team;
import save_to_mongo.SaveDataDoc;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private String TEAMS_URL = null;
    private static String MONGO_URI = null;

    public static void main(String[] args) throws InterruptedException {
        Main appMain = new Main();
        appMain.loadConfigs();
        if(args[0] != null) {
            String mongoUriProp = "mongo_uri_" + args[0];
            MONGO_URI = ReadConfigurations.getProperty(mongoUriProp);
        }
        appMain.runApp();
    }

    private void runApp() throws InterruptedException {
        TEAMS_URL = ReadConfigurations.getProperty("teams_page_url");
        List<Team> teams = extractAllTeamsData();
        List<Rider> riders = new ArrayList<>();
        ExtractRiderLinksFromTeamPage extractRiderLinksFromTeamPage = new ExtractRiderLinksFromTeamPage();

        for(Team team : teams){
            String teamName = team.getNormName();
            System.out.println("******* " + teamName);
            Set<String> teamRiderLinksSet = extractRiderLinksFromTeamPage.allRiderLinks(team.getPageUrl());
            Thread.sleep(3000);

            for(String riderLink : teamRiderLinksSet){
                Rider rider = new Rider();
                ExtractRiderData extractRiderData = new ExtractRiderData(rider);
                extractRiderData.riderInit(riderLink, teamName, true);
                riders.add(rider);
                System.out.println(" --- " + rider.getNormName());
                Thread.sleep(3000);
            }
        }

        System.out.println("Total teams: " + teams.size());
        System.out.println("Total riders: " + riders.size());
        SaveDataDoc saveData = new SaveDataDoc(MONGO_URI);
        saveData.saveTeamListToMongo(teams, ReadConfigurations.getProperty("teams_db_collection"));
        saveData.saveRiderListToMongo(riders, ReadConfigurations.getProperty("riders_db_collection"));
    }

    private void loadConfigs(){
        ReadConfigurations readConfigurations = new ReadConfigurations();
        readConfigurations.loadConfigs();
    }

    private List<Team> extractAllTeamsData(){
        TeamsList teamsList = new TeamsList();
        List<Team> worldTourTeamList = teamsList.teamsList(TEAMS_URL, "WorldTour Teams", 3, false);
        List<Team> proContinentalTeamList = teamsList.teamsList(TEAMS_URL, "ProContinental Teams", 10, false);

        List<Team> allTeams = Stream.concat(
                worldTourTeamList.stream(), proContinentalTeamList.stream()
        ).collect(Collectors.toList());

        return allTeams;
    }
}

