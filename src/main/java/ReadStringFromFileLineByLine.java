import java.io.*;

public class ReadStringFromFileLineByLine {

    public static void readLineByLine() {
        try {
            File file = new File("MyFile.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }
            fileReader.close();
            System.out.println("Contents of file:");
            //System.out.println(stringBuffer.toString().trim().replaceAll("(?m)(?s)^Beslutsunderlag.*Datum Kreditregel ID Beskrivning Handläggare Kommentar Resultat$", ""));
            //.replaceAll("Beslutsunderlag(\\s|\\S)*('Datum Kreditregel ID Beskrivning Handläggare Kommentar Resultat')", ""));
            //TODO write to file
            BufferedWriter bwr = new BufferedWriter(new FileWriter(new File("MyFile2.txt")));
            bwr.write((stringBuffer.toString().trim().replaceAll("(?m)(?s)^Beslutsunderlag.*Datum Kreditregel ID Beskrivning Handläggare Kommentar Resultat$", "")));
            //flush the stream
            bwr.flush();
            //close the stream
            bwr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
