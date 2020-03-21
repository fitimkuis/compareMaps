import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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

            int cntNlist = nList.getLength();

            //dynamic way
            HashMap<String,ArrayList<String>> maps=new HashMap<String, ArrayList<String>>();
            int start = 4;
            for (int i = 0; i <= cntNlist; i++) {
                maps.put("r" + start, new ArrayList<>());
                start++;
            }

            start = 4;

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
                    maps.get("r"+start).add(eElement.getElementsByTagName("R"+start).item(0).getTextContent());
                    start++;
                    System.out.println("R5 : " + eElement.getElementsByTagName("R5").item(0).getTextContent());
                    maps.get("r"+start).add(eElement.getElementsByTagName("R"+start).item(0).getTextContent());
                    start++;
                    r5.add(eElement.getElementsByTagName("R5").item(0).getTextContent());
                    System.out.println("R6 : " + eElement.getElementsByTagName("R6").item(0).getTextContent());
                    maps.get("r"+start).add(eElement.getElementsByTagName("R"+start).item(0).getTextContent());
                    start++;
                    r6.add(eElement.getElementsByTagName("R6").item(0).getTextContent());
                    System.out.println("R7 : " + eElement.getElementsByTagName("R7").item(0).getTextContent());
                    r7.add(eElement.getElementsByTagName("R7").item(0).getTextContent());
                    maps.get("r"+start).add(eElement.getElementsByTagName("R"+start).item(0).getTextContent());
                    start = 4;
                }
            }

            int node4 = 0;
            int node5 = 0;
            int node6 = 0;
            int node7 = 0;
            int nodeCounter4 = 0;
            int nodeCounter5 = 0;
            int nodeCounter6 = 0;
            int nodeCounter7 = 0;
            int nodeCnt = 4;
            int nodeSum = 0;
            for (int i = 0; i < cntNlist; i++){

                node4 += Integer.valueOf(maps.get("r"+nodeCnt).get(i));
                nodeCounter4 = maps.get("r"+nodeCnt).size();
                nodeCnt++;
                node5 += Integer.valueOf(maps.get("r"+nodeCnt).get(i));
                nodeCounter5 = maps.get("r"+nodeCnt).size();
                nodeCnt++;
                node6 += Integer.valueOf(maps.get("r"+nodeCnt).get(i));
                nodeCounter6 = maps.get("r"+nodeCnt).size();
                nodeCnt++;
                node7 += Integer.valueOf(maps.get("r"+nodeCnt).get(i));
                nodeCounter7 = maps.get("r"+nodeCnt).size();
                nodeCnt = 4;
                nodeSum = node4 + node5 + node6+ node7;
            }

            System.out.println("Node4 sum: " + node4);
            System.out.println("Node5 sum: " + node5);
            System.out.println("Node6 sum: " + node6);
            System.out.println("Node7 sum: " + node7);
            int nodes = nodeCounter4 + nodeCounter5 + nodeCounter6 + nodeCounter7;
            System.out.println("number of R nodes :"+nodes+" nodes SUM: "+nodeSum);


            int counter = 0;
            int nodeR4 = 0;
            System.out.println(r4);
            for(String s : r4){
                nodeR4 += Integer.valueOf(s);
                counter++;
            }
            System.out.println("NODE4 sum: "+nodeR4);

            int nodeR5 = 0;
            System.out.println(r5);
            for(String s : r5){
                nodeR5 += Integer.valueOf(s);
                counter++;
            }
            System.out.println("NODE5 sum: "+nodeR5);

            int nodeR6 = 0;
            System.out.println(r6);
            for(String s : r6){
                nodeR6 += Integer.valueOf(s);
                counter++;
            }
            System.out.println("NODE6 sum: "+nodeR6);

            int nodeR7 = 0;
            System.out.println(r7);
            for(String s : r7){
                nodeR7 += Integer.valueOf(s);
                counter++;
            }
            System.out.println("NODE7 sum: "+nodeR7);

            int sum = nodeR4+nodeR5+nodeR6+nodeR7;
            System.out.println("number of R nodes :"+counter+" nodes SUM: "+sum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
