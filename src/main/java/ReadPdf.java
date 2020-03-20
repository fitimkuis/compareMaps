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
//import com.kms.katalon.core.util.KeywordUtil;

public class ReadPdf {

    static List <String> rules = new ArrayList<>();

    static Map<String, String> expectedValues = new HashMap<>();

    static String[] lines;

    private static final Logger log = LoggerFactory.getLogger(MapDiffUtil.class);

    //KeywordUtil logger = new KeywordUtil();

    public static String[] readPdfFile(String pdfFilePath) throws IOException {

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

        String newLine = System.getProperty("line.separator");
        String retString = String.join(newLine,text);

        String[] arrLines = retString.split("(\r\n|\r|\n)", -1);

        List<String[]> arr = new ArrayList<>();
        for (String s: arrLines){
            arr.add(s.split("\r\n"));
        }

        List<String> arr2 = new ArrayList<>();
        for (String s: content){
            arr2.add(s.replace("\r\n"," "));
        }

        String cont = arr2.get(0);


        String cont1 = cont.replaceAll("\r\n", "  ");
        document.close();

        //testing TODO
        String pat = "2020";
        Pattern rr = Pattern.compile(pat);
        String splitString = "";
        Matcher mm = rr.matcher(cont);
        if (mm.find()) {
            System.out.println("Found value: " + mm.group(0));
            splitString = mm.group(0);
        } else {
            System.out.println("NO MATCH");
        }

        String[] dateSplitted = arr2.get(0).split(splitString);

        System.out.println(dateSplitted[1]);

        String test = "Produkt LånelöfteSkandia106 55 StockholmTelefon: 08 788 10 00skandia.se1/5InternSidaKlassificering1 KundinformationKunduppgifter huvudlåntagarePersonnummer 199003122455Förnamn Skandia ExtranamnEfternamn MockssonCivilstånd EnsamståendeC/O Adress 123456Gatuadress Lindhagensgatan 86Postnummer 11218Postort StockholmMobilnummer 0796765985E-postadress suvankar1990@gmail.comSysselsättning Fast/TillsvidareanställdArbetsgivare CapgeminiInkomst (från UC) 0Inkomst (Angiven) 50 000Valuta SEKTotalt antal barn i hushålletTotalt antal barn i hushållet, heltid2 KreditriskKreditriskPDSkandia106 55 StockholmTelefon: 08 788 10 00skandia.se2/5InternSidaKlassificeringSkuldkvotBelåningsgrad (sökt belopp inräknat)RiskklassLånelöftesbelopp kreditbeslut baserats på 350 000Överskott/underskott (KALP)3 Beslut3.1 KreditreglerDatum Kreditregel ID Beskrivning Handläggare Kommentar Resultat20-03-10 13:12 CR030\"Internal engagementcheck (only for Privateloan and Mortgageloans)\" - 199003122455System Godkänt20-03-10 13:12 CR077\"Applicant is in Fraudlist\" - 199003122455System Godkänt20-03-10 13:12 CR081Risk för bedrägeriFöretag - 199003122455System Avslag20-03-10 13:12 CR032Temporary or projectbased employmentSystem Godkänt20-03-10 13:12 CR007Skyddad personuppgift -199003122455System Godkänt20-03-10 13:12 CR008 - 199003122455 System Godkänt20-03-10 13:12 CR011'Customer has BOX-adress in big cities' -199003122455System Godkänt20-03-10 13:12 CR012Customer has FACK-System GodkäntSkandia106 55 StockholmTelefon: 08 788 10 00skandia.se3/5InternSidaKlassificeringadress - 19900312245520-03-10 13:12 CR013Customer has Posterestante-address -199003122455System Godkänt20-03-10 13:12 CR015Foreign resident andhave at least ONE late-payment -199003122455System Godkänt20-03-10 13:12 CR024Kreditupplysning saknas- 199003122455System Avslag20-03-10 13:12 CR035Debt remediation -199003122455System Godkänt20-03-10 13:12 CR036Skuldsaldo UC -199003122455System Godkänt20-03-10 13:12 CR047If customer has lost theirDrivers license -199003122455System Godkänt20-03-10 13:12 CR048If customer has lost theirPassport -199003122455System Godkänt20-03-10 13:12 CR049If customer has lost theirID document -199003122455System Godkänt20-03-10 13:12 CR050Marital status differsfrom what the customerhas entered inapplication -199003122455System Godkänt20-03-10 13:12 CR051 - 199003122455 System Godkänt20-03-10 13:12 CR071Skyddad adress -199003122455System GodkäntSkandia106 55 StockholmTelefon: 08 788 10 00skandia.se4/5InternSidaKlassificering20-03-10 13:12 CR072customer is emigrated -199003122455System Godkänt20-03-10 13:12 CR073customer is deceased -199003122455System Godkänt20-03-10 13:12 CR074UC Investigation real -199003122455System Godkänt20-03-10 13:12 CR075UC investigation spec -199003122455System Godkänt20-03-10 13:12 CR076Lost id documents -199003122455System Godkänt20-03-10 13:12 CR019 PD för högt System Godkänt20-03-10 13:12 CR028 PD saknas System Avslag20-03-10 13:12 CR041All applicants havecurrency SEKSystem Godkänt20-03-10 13:12 CR052Kontrollera inkomst -199003122455System Godkänt3.2 KreditbeslutDatum Handläggare Kommentar Resultat20-03-10 13:12 System AvslagSkandia106 55 StockholmTelefon: 08 788 10 00skandia.se5/5InternSidaKlassificering]\n";
        //test = test.replaceAll("/'\'","");
        String[] t = test.split("2020");

        String[] splitted = dateSplitted[1].split(splitString);

        return dateSplitted;
        ///TODO


        /*
        //System.out.println("DEBUG**********"+content);
        //TODO write to txt file
        writeTxtFile(content);

        lines = text.split("(\r\n|\r|\n)", -1);


        //System.out.println(lines);

        //for testing purpose
        //Map<String, String> rules = new HashMap<>();

        //String pattern = '^.*Person ([\\S\\s])([\\S\\s])([\\S\\s]+)';
        //String pattern = '([CR\\d]+) * Description ([\\S\\s]) Person ([\\S\\s])([\\S\\s]+)';
        String pattern = "([\\S\\s]+) ([\\S\\s]+) ([CR\\d]+) - ([\\d]+) System ([\\S\\s]+)";
        //String pattern = "(?m)^(\\d+:\\d+)\\s(\\w+\\d+).* System (\\w.*)";
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
                //System.out.println("Found value: " + m.group(0));
                //System.out.println("Found value: " + m.group(1)); //rule
                //rule1 = m1.group(1).replaceAll("\\s","");
                //System.out.println("Found value: " + m.group(2));
                //System.out.println("Found value: " + m.group(3)); //rule x
                rule = m.group(3).replaceAll("\\s", "");
                rules.add(rule);
                //System.out.println("Found value: " + m.group(4)); //outcome
                //outcome1 = m1.group(4).replaceAll("\\s","")
                //System.out.println("Found value: " + m.group(5)); //outcome x
                outcome = m.group(5).replaceAll("\\s", "");
                rulesOutcomes.put(rule, outcome);
                if (m.group(5).replaceAll("\\s", "").equals(searchString)) {
                    log.info("value is wrong should be Godkänt");
                }
            } else {
                //System.out.println("NO MATCH");
            }
        }
        */


        //expectedValues.put("CR007", "Godkänt");
        //expectedValues.put("CR008", "Avslag");
        //expectedValues.put("CR051", "Godkänt");


        //compare maps
        //Assert.assertTrue("Maps should be unequal", MapDiffUtil.validateEqual(rulesOutcomes, expectedValues, "map1", "map2"));
        //Assert.assertFalse("Maps should be unequal", MapDiffUtil.validateEqual(rulesOutcomes, expectedValues, "map1", "map2"));
    }

    public static void writeTxtFile(List<String> list) throws IOException {
        try {
            FileWriter writer = new FileWriter("temp.txt", false);
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
            stream.forEach(s -> contentBuilder.append(s).append(" ").append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    private static String readTxtFile(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public static String trimTrailingBlanks( String str)
    {
        int len = 0;
        if( str == null)
            return null;
        if (!str.equals(" ")) {
            len = str.length();
            for (; len > 0; len--) {
                if (!Character.isWhitespace(str.charAt(len - 1)))
                    break;
            }
        }
        return str.substring( 0, len);
    }

    public static void main(String[] args) throws IOException {


        String number = "1.0";
        Double doubleNumber = Double.parseDouble(number);
        System.out.println(doubleNumber);
        int integer = doubleNumber.intValue();

        //TODO read pdf file
        String[] splittedDate = readPdfFile("src/test/Kreditbeslutsfil_500786.pdf");
        Map<String, String> rulesOutcomes = new HashMap<>();

        List<String> ruleList = new ArrayList<>();
        List<String> outList = new ArrayList<>();

        ///////////////////////////////////////////////////////////////////
        //TODO working one regex
        Map<String, String> testValues = new HashMap<>();
        String  rulePattern = "(?m)^(\\d+:\\d+)\\s(\\w+\\d+).* System (\\w.*)";
        // Create a Pattern object
        Pattern r = Pattern.compile(rulePattern);
        Matcher m;
        // Now create matcher object.
        for (String line : splittedDate) {
            m = r.matcher(line);
            if (m.find()) {
                //System.out.println("Found value: " + m.group(0));
                //System.out.println("Found value: " + m.group(1));
                //System.out.println("Found value: " + m.group(2));
                //System.out.println("Found value: " + m.group(3));
                testValues.put(m.group(2),m.group(3));
                rulesOutcomes.put(m.group(2),m.group(3));
            }
        }
        System.out.println(testValues);

        //TODO testing

        //TODO remove unwanted lines from the start of file and write to new txt file
        String path = ReadStringFromFileLineByLine.readLineByLineRemoveUnwantedLines("temp.txt");

        //TODO parse txt file read all lines to string
        String strFile = readLineByLineJava8( path );

        //add here which unwanted lines (strings) need to remove
        String remove1 =  " Skandia 106 55 Stockholm Telefon: 08 788 10 00 skandia.se 3/5Intern SidaKlassificering";
        String remove2 = " Skandia 106 55 Stockholm Telefon: 08 788 10 00 skandia.se 4/5Intern SidaKlassificering";
        String remove3 = "/5InternSidaKlassificering";
        String remove4 = " 3.2 Kreditbeslut Datum Handläggare Kommentar Resultat";
        String remove5 = "13:12 System Avslag Skandia 106 55 Stockholm Telefon: 08 788 10 00 skandia.se 5/5Intern SidaKlassificering";
        String remove6 = "adress - 199003122455";

        //split file with date
        rulePattern = "([\\d-\\d]+)";
        r = Pattern.compile(rulePattern);
        String splitDate = "";
        //for (String line : lines) {
        m = r.matcher(strFile);
        if (m.find()) {
            //System.out.println("Found value: " + m.group(0));
            splitDate = m.group(0);
        }

        String[] splitted = strFile.split(splitDate+" ");

        FileWriter writer = new FileWriter("temp.txt", false);
        for (String s: splitted){
            //System.out.println(s.replace("\n", "").replace("\r", "").replace(remove1, "").replace(remove2,"").replace(remove3,"").replace(remove4,"").replace(remove5,"").replace(remove6,""));
            writer.write(s.replace("\n", "").replace("\r", "").replace(remove1, "").replace(remove2,"").replace(remove3,"").replace(remove4,"").replace(remove5,"").replace(remove6,""));
            writer.write("\n");   // write new line
        }
        writer.close();

        //TODO read txt file MyFile3.txt to regex
        String ret = readTxtFile("temp.txt");
        //System.out.println(ret);

        String[] lines = ret.split("(\r\n|\r|\n)", -1);

        //String[] spaceMoved = {};
        List <String> spaceMoved = new ArrayList<>();

        for (String s : lines){
            String res = trimTrailingBlanks(s);
            if (!res.equals("")) {
                spaceMoved.add(res);
            }
        }

        /*System.out.println("DEBUG space moved ");
        for(String s : spaceMoved){
            System.out.println(s);
        }*/

        //Map<String, String> rulesOutcomes = new HashMap<>();

        //List<String> ruleList = new ArrayList<>();
        //List<String> outList = new ArrayList<>();

 ///////////////////////////////////////////////////////////////////
        //TODO working one regex
        //Map<String, String> testValues = new HashMap<>();
        rulePattern = "(?m)^(\\d+:\\d+)\\s(\\w+\\d+).* System (\\w.*)";
        // Create a Pattern object
        r = Pattern.compile(rulePattern);
        // Now create matcher object.
        for (String line : spaceMoved) {
            m = r.matcher(line);
            if (m.find()) {
                //System.out.println("Found value: " + m.group(0));
                //System.out.println("Found value: " + m.group(1));
                //System.out.println("Found value: " + m.group(2));
                //System.out.println("Found value: " + m.group(3));
                testValues.put(m.group(2),m.group(3));
                rulesOutcomes.put(m.group(2),m.group(3));
            }
        }
        System.out.println(testValues);

        for (Map.Entry<String,String> entry : rulesOutcomes.entrySet()){
            //System.out.println("Key = " + entry.getKey() +", Value = " + entry.getValue());
            ruleList.add(entry.getKey());
        }
////////////////////////////////////////////////////////////////////

        /*rulePattern = "([\\d:\\d]+)\\s([CR\\d+]+)";
        // Create a Pattern object
        r = Pattern.compile(rulePattern);
        // Now create matcher object.
        for (String line : spaceMoved) {
            m = r.matcher(line);
            if (m.find()) {
                System.out.println("Found value: " + m.group(0));
                System.out.println("Found value: " + m.group(1)); //rule
                //rule1 = m1.group(1).replaceAll("\\s","");
                System.out.println("Found value: " + m.group(2));
                ruleList.add(m.group(2));
            }
        }*/

        /*rulePattern = "Godkänt|Avslag";
        // Create a Pattern object
        r = Pattern.compile(rulePattern);
        // Now create matcher object.
        for (String line : spaceMoved) {
            m = r.matcher(line);
            if (m.find()) {
                System.out.println("Found value: " + m.group(0));
                outList.add(m.group(0));
            }
        }*/

        //for (int i = 0; i < outList.size(); i++){
        //    rulesOutcomes.put(ruleList.get(i), outList.get(i));
        //}

        //expected values are done by rulelist "Godkänt" if i modulo 2 == 0 else "Avslag"
        int i = 0;
        for (String ru : ruleList){
            if (i % 2 == 0) {
                expectedValues.put(ru, "Godkänt");
            }
            else{
                expectedValues.put(ru, "Avslag");
            }
            i++;
        }

        //compare maps
        //Assert.assertTrue("Maps should be unequal", MapDiffUtil.validateEqual(rulesOutcomes, expectedValues, "map1", "map2"));
        Assert.assertFalse("Maps should be unequal", MapDiffUtil.validateEqual(rulesOutcomes, expectedValues, "map1", "map2"));
    }
}
