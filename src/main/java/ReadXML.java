import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.*;
import org.apache.commons.lang3.math.NumberUtils;

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
            int tagCounter = 0;
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document document = docBuilder.parse("C:\\Users\\fitim\\IdeaProjects\\compareMaps\\src\\main\\java\\stuff.xml");


            //NodeList nodes = doc.getElementsByTagName("reports");
            //System.out.println("\nHere you go => Total # of Elements: " + nodes.getLength());

            List<String> tags = new ArrayList<>();
            NodeList nodeList = document.getElementsByTagName("*");
            //get count of R tag names in a node
            int countReport = 0;
            for(int i = 0; i < nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    if (node.getNodeName().equals("report")){
                        countReport++;
                    }
                    if (node.getNodeName().contains("R")){
                        tags.add(node.getNodeName());
                        tagCounter++;
                    }
                    if(countReport > 1){
                        break;
                    }
                    //System.out.println(node.getNodeName());
                }

            }
            //get all R tag names to list
            /*for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    if (node.getNodeName().contains("R")){
                        tags.add(node.getNodeName());
                        tagCounter++;
                    }
                    //System.out.println(node.getNodeName());
                }
            }*/

            //dynamic list for tag values
            HashMap<String,ArrayList<String>> maps1 = new HashMap<String, ArrayList<String>>();
            String tag = tags.get(0);
            String latest = tags.get(tags.size() - 1);
            latest = latest.replace("R","");
            int latestTag = Integer.parseInt(latest);
            tag = tag.replace("R","");
            int startTag = Integer.parseInt(tag);
            for (int i = 0; i <= tagCounter-1; i++) {
                maps1.put("r" + startTag, new ArrayList<>());
                startTag++;
            }

            startTag = Integer.parseInt(tag);
            //get R values from xml
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                //System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    for (int i = 0; i <= tagCounter-1; i++) {
                        maps1.get("r" + startTag).add(eElement.getElementsByTagName("R" + startTag).item(0).getTextContent());
                        startTag++;
                        if (startTag >= latestTag + 1) {
                            startTag = Integer.parseInt(tag);
                        }
                    }
                }
            }

            startTag = Integer.parseInt(tag);
            //TODO print out and calculate
            HashMap<Integer,ArrayList<Integer>> maps2 = new HashMap<Integer, ArrayList<Integer>>();
            for (int i = 0; i <= tagCounter-1; i++){
                maps2.put(startTag, new ArrayList<>());
                startTag++;
            }
            //add maps1 values to maps2 as integer
            startTag = Integer.parseInt(tag);
            //String val = maps1.get("r"+startTag).get(0);
            for (int i = 0; i < nList.getLength(); i++){

                for (int j = 0; j <= tagCounter-1; j++) {

                    boolean digits = NumberUtils.isDigits(maps1.get("r" + startTag).get(i));

                    if (maps1.get("r"+startTag).get(i).equals("") || digits == false){
                        maps2.get(startTag).add(0);
                        //maps2.get(startTag).add(Integer.parseInt(maps1.get("r" + startTag).get(0)));
                    }
                    else{
                        maps2.get(startTag).add(Integer.parseInt(maps1.get("r" + startTag).get(i)));
                    }
                    startTag++;
                    if (startTag >= latestTag + 1) {
                        startTag = Integer.parseInt(tag);
                    }
                }
            }


            //System.out.println("node R value: "+maps1.get("R4").get(4));

            startTag = Integer.parseInt(tag);
            System.out.println("Node values");
            int sumCounter = 1;
            int nodesSum = 0;
            int getCounter = 0;
            int tempSum = 0;
            for (int i = 0; i < nList.getLength(); i++) {
                for (int j = 0; j <= tagCounter-1; j++) {
                    if (maps2.get(startTag).get(i) != 0) {
                        nodesSum += maps2.get(startTag).get(i);
                        tempSum += maps2.get(startTag).get(i);
                    }
                    else{
                        nodesSum += 0;
                        tempSum += 0;
                        System.out.println("node R"+startTag+ " value: "+maps1.get("r"+startTag).get(getCounter));

                    }
                    //System.out.println("node R"+startTag+ " sum: "+nodesSum);
                    System.out.println("node R"+startTag+ " value: "+maps2.get(startTag).get(getCounter));
                    //System.out.println("node R"+startTag+ " value: "+maps1.get(startTag).get(0));
                    if (sumCounter % tagCounter == 0){
                        System.out.println("nodes sum: "+nodesSum);
                        getCounter++;
                        nodesSum = 0;
                    }
                    startTag++;
                    if (startTag >= latestTag + 1) {
                        startTag = Integer.parseInt(tag);
                    }
                    sumCounter++;
                }
            }

            System.out.println("R Node sum values: "+tempSum);


/*
            int tagStart = 0;

            //dynamic way
            HashMap<String,ArrayList<String>> maps = new HashMap<String, ArrayList<String>>();
            int start = 4;
            for (int i = 0; i <= cntNlist; i++) {
                maps.put("r" + start, new ArrayList<>());
                start++;
            }

            List<String> r4 = new ArrayList<>();
            List<String> r5 = new ArrayList<>();
            List<String> r6 = new ArrayList<>();
            List<String> r7 = new ArrayList<>();

            start = 4;

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    System.out.println("R4 : " + eElement.getElementsByTagName(tags.get(tagStart)).item(0).getTextContent());
                    r4.add(eElement.getElementsByTagName("R4").item(0).getTextContent());
                    maps.get("r"+start).add(eElement.getElementsByTagName("R"+start).item(0).getTextContent());
                    start++;
                    tagStart++;
                    System.out.println("R5 : " + eElement.getElementsByTagName(tags.get(tagStart)).item(0).getTextContent());
                    maps.get("r"+start).add(eElement.getElementsByTagName("R"+start).item(0).getTextContent());
                    start++;
                    tagStart++;
                    r5.add(eElement.getElementsByTagName("R5").item(0).getTextContent());
                    System.out.println("R6 : " + eElement.getElementsByTagName(tags.get(tagStart)).item(0).getTextContent());
                    maps.get("r"+start).add(eElement.getElementsByTagName("R"+start).item(0).getTextContent());
                    start++;
                    tagStart++;
                    r6.add(eElement.getElementsByTagName("R6").item(0).getTextContent());
                    System.out.println("R7 : " + eElement.getElementsByTagName(tags.get(tagStart)).item(0).getTextContent());
                    r7.add(eElement.getElementsByTagName("R7").item(0).getTextContent());
                    maps.get("r"+start).add(eElement.getElementsByTagName("R"+start).item(0).getTextContent());
                    start = 4;
                    tagStart = 0;
                }
            }

            //System.out.println(maps1.get("R4"));

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
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
