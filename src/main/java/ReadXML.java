import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ReadXML {

    public static void main(String argv[]) {
        try {
            File fXmlFile = new File("C:\\Users\\fitim\\IdeaProjects\\compareMaps\\src\\main\\java\\stuff.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("report");

            List<String> r4 = new ArrayList<>();
            List<String> r5 = new ArrayList<>();
            List<String> r6 = new ArrayList<>();
            List<String> r7 = new ArrayList<>();

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("R4 : " + eElement.getElementsByTagName("R4").item(0).getTextContent());
                    r4.add(eElement.getElementsByTagName("R4").item(0).getTextContent());
                    System.out.println("R5 : " + eElement.getElementsByTagName("R5").item(0).getTextContent());
                    r5.add(eElement.getElementsByTagName("R5").item(0).getTextContent());
                    System.out.println("R6 : " + eElement.getElementsByTagName("R6").item(0).getTextContent());
                    r6.add(eElement.getElementsByTagName("R6").item(0).getTextContent());
                    System.out.println("R7 : " + eElement.getElementsByTagName("R7").item(0).getTextContent());
                    r7.add(eElement.getElementsByTagName("R7").item(0).getTextContent());
                }
            }
            int nodeR4 = 0;
            System.out.println(r4);
            for(String s : r4){
                nodeR4 += Integer.valueOf(s);
            }
            System.out.println("NODE4 sum: "+nodeR4);

            int nodeR5 = 0;
            System.out.println(r5);
            for(String s : r5){
                nodeR5 += Integer.valueOf(s);
            }
            System.out.println("NODE5 sum: "+nodeR5);

            int nodeR6 = 0;
            System.out.println(r6);
            for(String s : r6){
                nodeR6 += Integer.valueOf(s);
            }
            System.out.println("NODE6 sum: "+nodeR6);

            int nodeR7 = 0;
            System.out.println(r7);
            for(String s : r7){
                nodeR7 += Integer.valueOf(s);
            }
            System.out.println("NODE7 sum: "+nodeR7);

            int sum = nodeR4+nodeR5+nodeR6+nodeR7;
            System.out.println("nodes SUM: "+sum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
