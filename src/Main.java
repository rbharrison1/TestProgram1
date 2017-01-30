import java.io.*;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        //String filePath = "C:\\Users\\rharrison15\\Desktop\\TestFile.txt";
        String filePath = "C:\\Users\\ryneb\\Desktop\\TestFile.txt";
        String replace = filePath.replace("\\", "\\\\");
        BufferedReader br = null;
        FileReader fr = null;
        List<Standing> list = null;


        fr = new FileReader(replace);
        br = new BufferedReader(fr);

        String sCurrentLine;
        Standing team = new Standing();
        System.out.println("2016 NL Central Standings");
        System.out.println("\t\tW\tL\t%\t\tGB");

        while ((sCurrentLine = br.readLine()) != null){
            String[] data = sCurrentLine.split("\\|");
            team.position = Integer.parseInt(data[0]);
            team.name = data[1];
            team.wins = Integer.parseInt(data[2]);
            team.losses = Integer.parseInt(data[3]);
            team.pct = data[4];
            team.gb = data[5];
                
            System.out.println(team.position + ") " + team.name + "\t" + team.wins + "\t" + team.losses + "\t"
                + team.pct + "\t" + team.gb);
        }

        fr.close();
        br.close();

    }

    public static class Standing {
        public int position;
        public String name;
        public int wins;
        public int losses;
        public String pct;
        public String gb;
    }
}


