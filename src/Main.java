
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        String filePath = "NBA_Standings.json";
        BufferedReader br;

        br = new BufferedReader(new FileReader(filePath));
        ObjectMapper mapper = new ObjectMapper();
        Standings standings = mapper.readValue(br, Standings.class);
        Standing team;

        String[] date = standings.standings_date.split("T");
        System.out.println("Date: " + date[0]);
        System.out.println("NBA Eastern Conference Standings");
        System.out.println("\t\t\t\t\tW\tL\t%\t\tGB");

        for (int x = 0; x < 15; x++)
        {
            team = standings.standing.get(x);
            System.out.println(String.format("%1$2s", team.rank) + ") " + String.format("%1$13s", team.last_name) + "\t" + team.won + "\t" + team.lost + "\t" + team.win_percentage + "\t" + team.games_back);
        }

        System.out.println();
        System.out.println("NBA Western Conference Standings");
        System.out.println("\t\t\t\t\tW\tL\t%\t\tGB");

        for (int x = 15; x < 30; x++)
        {
            team = standings.standing.get(x);
            System.out.println(String.format("%1$2s", team.rank) + ") " + String.format("%1$13s", team.last_name) + "\t" + team.won + "\t" + team.lost + "\t" + team.win_percentage + "\t" + team.games_back);
        }

        br.close();

    }

    public static class Standings {
        private String standings_date;
        private List<Standing> standing;

        public String getStandings_date() {
            return standings_date;
        }

        public void setStandings_date(String standings_date) {
            this.standings_date = standings_date;
        }

        public List<Standing> getStanding() {
            return standing;
        }

        public void setStanding(List<Standing> standing) {
            this.standing = standing;
        }
    }

    public static class Standing {
        private String rank;
        private String won;
        private String lost;
        private String streak;
        private String ordinal_rank;
        private String first_name;
        private String last_name;
        private String team_id;
        private String games_back;
        private String points_for;
        private String points_against;
        private String home_won;
        private String home_lost;
        private String away_won;
        private String away_lost;
        private String conference_won;
        private String conference_lost;
        private String last_five;
        private String last_ten;
        private String conference;
        private String division;
        private String playoff_seed;
        private String games_played;
        private String points_scored_per_game;
        private String points_allowed_per_game;
        private String win_percentage;
        private String point_differential;
        private String point_differential_per_game;
        private String streak_type;
        private String streak_total;

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getWon() {
            return won;
        }

        public void setWon(String won) {
            this.won = won;
        }

        public String getLost() {
            return lost;
        }

        public void setLost(String lost) {
            this.lost = lost;
        }

        public String getStreak() {
            return streak;
        }

        public void setStreak(String streak) {
            this.streak = streak;
        }

        public String getOrdinal_rank() {
            return ordinal_rank;
        }

        public void setOrdinal_rank(String ordinal_rank) {
            this.ordinal_rank = ordinal_rank;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getTeam_id() {
            return team_id;
        }

        public void setTeam_id(String team_id) {
            this.team_id = team_id;
        }

        public String getGames_back() {
            return games_back;
        }

        public void setGames_back(String games_back) {
            this.games_back = games_back;
        }

        public String getPoints_for() {
            return points_for;
        }

        public void setPoints_for(String points_for) {
            this.points_for = points_for;
        }

        public String getPoints_against() {
            return points_against;
        }

        public void setPoints_against(String points_against) {
            this.points_against = points_against;
        }

        public String getHome_won() {
            return home_won;
        }

        public void setHome_won(String home_won) {
            this.home_won = home_won;
        }

        public String getHome_lost() {
            return home_lost;
        }

        public void setHome_lost(String home_lost) {
            this.home_lost = home_lost;
        }

        public String getAway_won() {
            return away_won;
        }

        public void setAway_won(String away_won) {
            this.away_won = away_won;
        }

        public String getAway_lost() {
            return away_lost;
        }

        public void setAway_lost(String away_lost) {
            this.away_lost = away_lost;
        }

        public String getConference_won() {
            return conference_won;
        }

        public void setConference_won(String conference_won) {
            this.conference_won = conference_won;
        }

        public String getConference_lost() {
            return conference_lost;
        }

        public void setConference_lost(String conference_lost) {
            this.conference_lost = conference_lost;
        }

        public String getLast_five() {
            return last_five;
        }

        public void setLast_five(String last_five) {
            this.last_five = last_five;
        }

        public String getLast_ten() {
            return last_ten;
        }

        public void setLast_ten(String last_ten) {
            this.last_ten = last_ten;
        }

        public String getConference() {
            return conference;
        }

        public void setConference(String conference) {
            this.conference = conference;
        }

        public String getDivision() {
            return division;
        }

        public void setDivision(String division) {
            this.division = division;
        }

        public String getGames_played() {
            return games_played;
        }

        public void setGames_played(String games_played) {
            this.games_played = games_played;
        }

        public String getPoints_scored_per_game() {
            return points_scored_per_game;
        }

        public void setPoints_scored_per_game(String points_scored_per_game) {
            this.points_scored_per_game = points_scored_per_game;
        }

        public String getPoints_allowed_per_game() {
            return points_allowed_per_game;
        }

        public void setPoints_allowed_per_game(String points_allowed_per_game) {
            this.points_allowed_per_game = points_allowed_per_game;
        }

        public String getWin_percentage() {
            return win_percentage;
        }

        public void setWin_percentage(String win_percentage) {
            this.win_percentage = win_percentage;
        }

        public String getPoint_differential() {
            return point_differential;
        }

        public void setPoint_differential(String point_differential) {
            this.point_differential = point_differential;
        }

        public String getPoint_differential_per_game() {
            return point_differential_per_game;
        }

        public void setPoint_differential_per_game(String point_differential_per_game) {
            this.point_differential_per_game = point_differential_per_game;
        }

        public String getStreak_type() {
            return streak_type;
        }

        public void setStreak_type(String streak_type) {
            this.streak_type = streak_type;
        }

        public String getStreak_total() {
            return streak_total;
        }

        public void setStreak_total(String streak_total) {
            this.streak_total = streak_total;
        }

        public String getPlayoff_seed() {
            return playoff_seed;
        }

        public void setPlayoff_seed(String playoff_seed) {
            this.playoff_seed = playoff_seed;
        }
    }
}


