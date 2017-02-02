
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.GZIPInputStream;

public class Main {

    static final String ACCESS_TOKEN = "***************************";

    static final String USER_AGENT = "User-agent";

    static final String USER_AGENT_CONTACT = "***********************"; //email

    static final String USER_AGENT_NAME = "nba-standings/%s (%s)";

    static final String VERSION = "$(version)";

    static final String AUTHORIZATION = "Authorization";

    static final String BEARER_AUTH_TOKEN = "Bearer %s";

    static final String ACCEPT_ENCODING = "Accept-encoding";

    static final String GZIP = "gzip";

    // For brevity, the url with api method, format, and parameters
    static final String REQUEST_URL = "https://erikberg.com/nba/standings.json";

    public static void main(String[] args) throws IOException {

        String accessToken = String.format(BEARER_AUTH_TOKEN, ACCESS_TOKEN);
        String userAgent = String.format(USER_AGENT_NAME,
                VERSION,
                USER_AGENT_CONTACT);
/*
        try {
            URL url = new URL(REQUEST_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Set Authorization header
            connection.setRequestProperty(AUTHORIZATION, accessToken);
            // Set User agent header
            connection.setRequestProperty(USER_AGENT, userAgent);
            // Tell server we can handle gzip content
            connection.setRequestProperty(ACCEPT_ENCODING, GZIP);

            // Check the HTTP status code for "200 OK"
            int statusCode = connection.getResponseCode();
            String encoding = connection.getContentEncoding();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                System.err.printf("Server returned HTTP status: %s. %s%n%n",
                        statusCode, connection.getResponseMessage());
                System.exit(1);
            }

            InputStream in = connection.getInputStream();
            // Ensure there is data
            if (in == null) {
                System.err.println("Response is empty.");
                System.exit(1);
            }

            // Decompress response if it is compressed
            if (GZIP.equals(encoding)) {
                in = new GZIPInputStream(in);
            }

            printResult(in);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

         */

        String body = "";
        HttpURLConnection con = (HttpURLConnection)new URL("https://api.stattleship.com/basketball/nba/games?on=yesterday").openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept","application/vnd.stattleship.com; version=1");
        con.setRequestProperty("Content-Type","application/json");
        con.setRequestProperty("Authorization","Token token=1f502063a6723c09c5b3d76809b3ed2b");
        con.connect();

        InputStream is = con.getInputStream();

        BufferedReader br;

        br = new BufferedReader(new InputStreamReader(is));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readValue(br, JsonNode.class);

        int length;

        JsonNode games = node.get("games");
        String date = games.findValue("on").toString();
        length = date.length();
        date = date.substring(4,length - 1);
        System.out.println(date);
        
        body += "<h3>Here's all that happened in the NBA yesterday, " + date + ".</h3>";

        JsonNode awayTeams = node.get("away_teams");
        List<String> away = new ArrayList<>();
        String name = "";
        for (JsonNode awayTeam : awayTeams) {
            name = awayTeam.get("name").toString();
            name = name.replaceAll("\"", "");
            away.add(name);
        }

        JsonNode homeTeams = node.get("home_teams");
        List<String> home = new ArrayList<>();
        for (JsonNode homeTeam : homeTeams) {
            name = homeTeam.get("name").toString();
            name = name.replaceAll("\"", "");
            home.add(name);
        }

        List<String> awayScores = new ArrayList<>();
        List<String> homeScores = new ArrayList<>();
        String awayScore;
        String homeScore;
        for (JsonNode game : games) {
            awayScore = game.get("away_team_score").toString();
            homeScore = game.get("home_team_score").toString();
            awayScores.add(awayScore);
            homeScores.add(homeScore);
        }

        body += "<div>";
        int i = 1;
        for (String x : away) {
            if ((i-1) % 4 == 0 && i != 2)
            {
                body += "<div>";
            }

            body += "<table style=\"border: 2px groove black;padding: 5px;display: inline-table;width: 120px;table-layout: fixed\"><tr style=\"border: 2px groove black;padding: 5px\"><td style=\"border: 2px groove black;padding: 5px;width: 100px\">" + away.get(i - 1) + "</td><td style=\"border: 2px groove black;padding: 5px;width: 20px;text-align: right\">" + awayScores.get(i - 1) + "</td></tr>";
            body += "<tr style=\"border: 2px groove black;padding: 5px\"><td style=\"border: 2px groove black;padding: 5px;width: 100px\">" + home.get(i - 1) + "</td><td style=\"border: 2px groove black;padding: 5px;width: 20px;text-align: right\">" + homeScores.get(i - 1) + "</td></tr></table>";

            if(i % 4 == 0 && i != 1)
            {
                body += "</div>";
            }

            i++;
        }
        
        br.close();
        con.disconnect();


        //end of first connection


        System.out.println();
        body += "<br> </br>";


        //start of second connection



        try {
            URL url = new URL(REQUEST_URL);
            HttpURLConnection con2 = (HttpURLConnection) url.openConnection();
            // Set Authorization header
            con2.setRequestProperty(AUTHORIZATION, accessToken);
            // Set User agent header
            con2.setRequestProperty(USER_AGENT, userAgent);
            // Tell server we can handle gzip content
            con2.setRequestProperty(ACCEPT_ENCODING, GZIP);

            // Check the HTTP status code for "200 OK"
            int statusCode = con2.getResponseCode();
            String encoding = con2.getContentEncoding();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                System.err.printf("Server returned HTTP status: %s. %s%n%n",
                        statusCode, con2.getResponseMessage());
                System.exit(1);
            }

            InputStream is2 = con2.getInputStream();
            // Ensure there is data
            if (is2 == null) {
                System.err.println("Response is empty.");
                System.exit(1);
            }

            // Decompress response if it is compressed
            if (GZIP.equals(encoding)) {
                is2 = new GZIPInputStream(is2);
            }

            //HttpsURLConnection con2 = (HttpsURLConnection) new URL("https://erikberg.com/nba/standings.json").openConnection();
            //con2.setRequestMethod("GET");
            //con2.setRequestProperty("Authorization","Bearer 4219b74c-c244-47cf-bb02-eb358c749980");
            //con2.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            //con2.connect();

            //InputStream is2 = con2.getInputStream();
            //FileReader fr = new FileReader("NBA_Standings.json");

            br = new BufferedReader(new InputStreamReader(is2));
            //br = new BufferedReader(fr);

            ObjectMapper mapper2 = new ObjectMapper();
            Standings standings = mapper2.readValue(br, Standings.class);
            Standing team;

            body += "<table style=\"border: 2px groove black;padding: 5px;display: inline-table;width: 591px;table-layout: fixed\">";

            System.out.println("NBA Eastern Conference Standings");
            System.out.println("\t\t\t\t\tW\tL\t%\t\tGB");
            body += "<tr><td style=\"border: 2px groove black;padding: 5px;text-align: center;width: 295.5px\">NBA Eastern Conference Standings</td>";


            System.out.println();
            System.out.println("NBA Western Conference Standings");
            System.out.println("\t\t\t\t\tW\tL\t%\t\tGB");
            body += "<td style=\"border: 2px groove black;padding: 5px;text-align: center;width: 295.5px\">NBA Western Conference Standings</td></tr></table><div>";

            String teamName;

            body += "<table style=\"border: 2px groove black;padding: 5px;display: inline-table;width: 225px;table-layout: fixed\"><tr style=\"border: 2px groove black;padding: 5px\"><th style=\"border: 2px groove black;padding: 5px;width: 25px\"></th><th style=\"border: 2px groove black;padding: 5px;width: 100px\"></th><th style=\"border: 2px groove black;padding: 5px;width: 25px\">W</th><th style=\"border: 2px groove black;padding: 5px;width: 25px\">L</th><th style=\"border: 2px groove black;padding: 5px;width: 25px\">%</th><th style=\"border: 2px groove black;padding: 5px;width: 25px\">GB</th></tr>";
            for (int x = 0; x < 15; x++) {
                team = standings.standing.get(x);

                teamName = team.first_name;

                System.out.println(String.format("%1$2s", team.rank) + ") " + String.format("%1$13s", teamName) + "\t" + team.won + "\t" + team.lost + "\t" + team.win_percentage + "\t" + team.games_back);
                body += "<tr style=\"border: 2px groove black;padding: 5px\"><td style=\"border: 2px groove black;padding: 5px;width: 25px;text-align: center\">" + String.format("%1$2s", team.rank) + "</td><td style=\"border: 2px groove black;padding: 5px;width: 100px\">" + String.format("%1$13s", teamName) + "</td><td style=\"border: 2px groove black;padding: 5px;width: 25px;text-align: center\">" + team.won + "</td><td style=\"border: 2px groove black;padding: 5px;width: 25px;text-align: center\">" + team.lost + "</td><td style=\"border: 2px groove black;padding: 5px;width: 25px;text-align: center\">" + team.win_percentage + "</td><td style=\"border: 2px groove black;padding: 5px;width: 25px;text-align: center\">" + team.games_back + "</td></tr>";
            }
            body += "</table>";


            body += "<table style=\"border: 2px groove black;padding: 5px;display: inline-table;width: 225px;table-layout: fixed\"><tr style=\"border: 2px groove black;padding: 5px\"><th style=\"border: 2px groove black;padding: 5px;width: 25px\"></th><th style=\"border: 2px groove black;padding: 5px;width: 100px\"></th><th style=\"border: 2px groove black;padding: 5px;width: 25px\">W</th><th style=\"border: 2px groove black;padding: 5px;width: 25px\">L</th><th style=\"border: 2px groove black;padding: 5px;width: 25px\">%</th><th style=\"border: 2px groove black;padding: 5px;width: 25px\">GB</th></tr>";
            for (int x = 15; x < 30; x++) {
                team = standings.standing.get(x);

                if (team.first_name.equals("Los Angeles"))
                {
                    teamName = "L.A. " + team.last_name;
                }
                else
                {
                    teamName = team.first_name;
                }

                System.out.println(String.format("%1$2s", team.rank) + ") " + String.format("%1$13s", teamName) + "\t" + team.won + "\t" + team.lost + "\t" + team.win_percentage + "\t" + team.games_back);
                body += "<tr style=\"border: 2px groove black;padding: 5px\"><td style=\"border: 2px groove black;padding: 5px;width: 25px;text-align: center\">" + String.format("%1$2s", team.rank) + "</td><td style=\"border: 2px groove black;padding: 5px;width: 100px\">" + String.format("%1$13s", teamName) + "</td><td style=\"border: 2px groove black;padding: 5px;width: 25px;text-align: center\">" + team.won + "</td><td style=\"border: 2px groove black;padding: 5px;width: 25px;text-align: center\">" + team.lost + "</td><td style=\"border: 2px groove black;padding: 5px;width: 25px;text-align: center\">" + team.win_percentage + "</td><td style=\"border: 2px groove black;padding: 5px;width: 25px;text-align: center\">" + team.games_back + "</td></tr>";
            }
            body += "</table></div></div>";

            br.close();
            con2.disconnect();
        }
        catch (IOException e)
        {
            System.out.println("Error generating standings.");
            body += "Error generating standings.";
        }

        String username = "*********************"; //from
        String password = "**************";  //insert password here
        String to = "*****************"; //to
        String subject = "Yesterday's NBA Wrap-up";
        String email_body = body;
        doSendMail(username,password,to,subject,email_body);

    }

    public static void doSendMail(final String username, final String password, String to, String subject, String email_body) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(email_body, "text/html");
            Transport.send(message);
            System.out.println("message sent");
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e.toString());
        }
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


