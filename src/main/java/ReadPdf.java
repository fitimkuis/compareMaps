import static com.google.common.collect.MapDifference.ValueDifference;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.common.collect.MapDifference.*;
//import com.kms.katalon.core.util.KeywordUtil;

public class ReadPdf {

    private static final Logger log =
            LoggerFactory.getLogger(MapDiffUtil.class);

    //KeywordUtil logger = new KeywordUtil();

    public static void readPdfFile(String pdfFilePath) throws IOException {

        //String pdfFilePath = System.getProperty("user.dir") + "\\pdfFiles\\Kreditbeslutsfil_500786.pdf";

        List<String> content = new ArrayList<>();

        String text = "";
        PDDocument document = PDDocument.load(new File(pdfFilePath));
        if (!document.isEncrypted()) {
            PDFTextStripper stripper = new PDFTextStripper();
            text = stripper.getText(document);
            //System.out.println("Text:" + text);
            content.add(text);
        }
        document.close();

        //System.out.println("DEBUG**********"+content);
        writeTxtFile(content);

        String[] lines = text.split("(\r\n|\r|\n)", -1);


        //System.out.println(lines);

        //for testing purpose
        Map<String, String> rules = new HashMap<>();

        //String pattern = '^.*Person ([\\S\\s])([\\S\\s])([\\S\\s]+)';
        //String pattern = '([CR\\d]+) * Description ([\\S\\s]) Person ([\\S\\s])([\\S\\s]+)';
        String pattern = "([\\S\\s]+) ([\\S\\s]+) ([CR\\d]+) - ([\\d]+) System ([\\S\\s]+)";
        String searchString = "Avslag";
        String rule = "";
        String outcome = "";
        Map<String, String> rulesOutcomes = new HashMap<>();

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);
// Now create matcher object.
        for (String line : lines) {
            Matcher m = r.matcher(line);
            if (m.find()) {
                System.out.println("Found value: " + m.group(0));
                System.out.println("Found value: " + m.group(1)); //rule
                //rule1 = m1.group(1).replaceAll("\\s","");
                System.out.println("Found value: " + m.group(2));
                System.out.println("Found value: " + m.group(3)); //rule x
                rule = m.group(3).replaceAll("\\s", "");
                System.out.println("Found value: " + m.group(4)); //outcome
                //outcome1 = m1.group(4).replaceAll("\\s","")
                System.out.println("Found value: " + m.group(5)); //outcome x
                outcome = m.group(5).replaceAll("\\s", "");
                rulesOutcomes.put(rule, outcome);
                if (m.group(5).replaceAll("\\s", "").equals(searchString)) {
                    log.info("value is wrong should be Godkänt");
                }
            } else {
                //System.out.println("NO MATCH");
            }
        }

        //define map where are expected key and value pair
        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("CR007", "Godkänt");
        expectedValues.put("CR008", "Avslag");
        expectedValues.put("CR051", "Godkänt");

        //TODO remove unwanted lines from the start of file and write to new txt file
        ReadStringFromFileLineByLine.readLineByLine();


        //compare maps
        //Assert.assertTrue("Maps should be unequal", MapDiffUtil.validateEqual(rulesOutcomes, expectedValues, "map1", "map2"));
        //Assert.assertFalse("Maps should be unequal", MapDiffUtil.validateEqual(rulesOutcomes, expectedValues, "map1", "map2"));
    }

    public static void writeTxtFile(List<String> list) throws IOException {
        try {
            FileWriter writer = new FileWriter("MyFile.txt", true);
            for (String s : list) {
                writer.write(s);
                writer.write("\r\n");   // write new line
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static String readLineByLineJava8(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public static void main(String[] args) throws IOException {
        readPdfFile("src/test/Kreditbeslutsfil_500786.pdf");
        //TODO parse txt file read all to string
        String strFile = readLineByLineJava8( "MyFile2.txt" );

        //add here unwanted lines
        String remove1 =  "Skandia106 55 StockholmTelefon: 08 788 10 00skandia.se3";
        String remove2 = "Skandia106 55 StockholmTelefon: 08 788 10 00skandia.se4";
        String remove3 = "/5InternSidaKlassificering";
        String remove4 = "3.2 KreditbeslutDatum Handläggare Kommentar Resultat";
        String remove5 = "13:12 System AvslagSkandia106 55 StockholmTelefon: 08 788 10 00skandia.se5";
        String remove6 = "adress - 199003122455";

        String[] splitted = strFile.split("20-03-10");

        FileWriter writer = new FileWriter("MyFile3.txt", true);
        for (String s: splitted){
            //System.out.println(s.replace("\n", "").replace("\r", "").replace(remove1, "").replace(remove2,"").replace(remove3,"").replace(remove4,"").replace(remove5,"").replace(remove6,""));
            writer.write(s.replace("\n", "").replace("\r", "").replace(remove1, "").replace(remove2,"").replace(remove3,"").replace(remove4,"").replace(remove5,"").replace(remove6,""));
            writer.write("\r\n");   // write new line
        }
        writer.close();

        //TODO read txt file MyFile3.txt to regex
    }
}
/**
 * Map comparison with detailed log messages
 */
/*
class MapDiffUtil {

    //private static KeywordUtil logg = new KeywordUtil();

    private static final Logger log =
            LoggerFactory.getLogger(MapDiffUtil.class);

    public static <K, V> boolean validateEqual(
            Map<K, V> map1, Map<K, V> map2,
            String map1Name, String map2Name) {

        final MapDifference<K, V> diff = Maps.difference(map1, map2);

        if (diff.areEqual()) {
            String error = "Maps "+map1Name+" and "+map2Name+" contain exactly the same name/value pairs";
            //logg.markWarning(error);
            log.info("Maps '{}' and '{}' contain exactly the same name/value pairs", map1Name, map2Name);
            return true;

        } else {
            logKeys(diff.entriesOnlyOnLeft(), map1Name, map2Name);
            logKeys(diff.entriesOnlyOnRight(), map2Name, map1Name);
            logEntries(diff.entriesDiffering(), map1Name, map2Name);
            return false;
        }
    }

    private static <K, V> void logKeys(
            Map<K, V> mapSubset, String n1, String n2) {
        if (not(mapSubset.isEmpty())) {
            //logg.markWarning("Keys found in "+n1+" but not in "+n2+" : "+mapSubset.keySet());
            log.error("Keys found in {} but not in {}: {}",n1, n2, mapSubset.keySet());
        }
    }

    private static <K, V> void logEntries(
            Map<K, ValueDifference<V>> differing,
            String n1, String n2) {
        if (not(differing.isEmpty())) {
            //logg.markFailed("Differing values found {key="+n1+"-value,"+n2+"-value}: "+differing);
            log.error("Differing values found {key={}-value,{}-value}: {}",	n1, n2, differing);
        }
    }

    private static boolean not(boolean b) {
        return !b;
    }
}
*/
/*
class ReadStringFromFileLineByLine {

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
            System.out.println(
                    stringBuffer
                            .toString()
                            .trim()
                            .replaceAll("(?m)(?s)^Beslutsunderlag.*Datum Kreditregel ID Beskrivning Handläggare Kommentar Resultat$", ""));
            //.replaceAll("Beslutsunderlag(\\s|\\S)*('Datum Kreditregel ID Beskrivning Handläggare Kommentar Resultat')", ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/