import java.io.*;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        String filePath = "C:\\Users\\rharrison15\\Desktop\\TestFile.txt";
        String replace = filePath.replace("\\", "\\\\");
        BufferedReader br = null;
        FileReader fr = null;
        List<Standing> list = null;

        try {
            fr = new FileReader(replace);

            br = new BufferedReader(fr);

            String sCurrentLine;
            Standing team = null;

            while ((sCurrentLine = br.readLine()) != null){
                String[] data = sCurrentLine.split("\\|");

                team.position = Integer.parseInt(data[0]);

                
                System.out.println(sCurrentLine);
            }
        }
        catch (IOException e)
        {

        }
        finally {
            fr.close();
            br.close();
        }
    }

    public class Standing {
        public int position;
        public String team;
        public int wins;
        public int losses;
        public String pct;
        public String gb;
    }
}


