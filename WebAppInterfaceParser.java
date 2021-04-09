package uk.ac.le.cs.mobwebapp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class WebAppInterfaceParser {

    public static void main(String[] args) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("WebAppInterface.xml");
            doc.getDocumentElement().normalize();
            traverse_tree(doc);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

    }

    public static void traverse_tree(Document doc) {

        JSONObject rootJsonObject = new JSONObject();
        JSONArray abstractMethodsArray = new JSONArray();

        NodeList nodeList = doc.getElementsByTagName("abstract_method");
        int length = nodeList.getLength();
        for (int i = 0; i < length; i++) {
            JSONObject abstractMethodObject = new JSONObject();
            Element abstractMethod = (Element) nodeList.item(i);

            NamedNodeMap attributes = abstractMethod.getAttributes();
            String name = attributes.getNamedItem("name").getNodeValue();
            abstractMethodObject.put("name", name);

            Element accessLevelElement = (Element) abstractMethod.getElementsByTagName("access_level").item(0);
            String accessLevel = accessLevelElement.getTextContent();
            abstractMethodObject.put("access_level", accessLevel);

            JSONArray argumentsArray = new JSONArray();
            NodeList argumentsNodeList = abstractMethod.getElementsByTagName("parameter");
            for (int j = 0; j < argumentsNodeList.getLength(); j++) {
                JSONObject argumentObject = new JSONObject();
                Element parameterElement = (Element) argumentsNodeList.item(j);
                argumentObject.put("type", parameterElement.getAttribute("type"));
                argumentObject.put("variable", parameterElement.getTextContent());
                argumentsArray.add(argumentObject);
            }
            if (argumentsNodeList.getLength() > 0) {
                abstractMethodObject.put("arguments", argumentsArray);
            }

            JSONArray exceptionsArray = new JSONArray();
            NodeList exceptionsNodeList = abstractMethod.getElementsByTagName("exception");
            for (int j = 0; j < exceptionsNodeList.getLength(); j++) {
                Element exceptionElement = (Element) exceptionsNodeList.item(j);
                exceptionsArray.add(exceptionElement.getTextContent());
            }
            if (exceptionsNodeList.getLength() > 0) {
                abstractMethodObject.put("throws", exceptionsArray);
            }

            Element returnElement = (Element) abstractMethod.getElementsByTagName("return").item(0);
            String returnValue = returnElement.getTextContent();
            abstractMethodObject.put("return", returnValue);

            abstractMethodsArray.add(abstractMethodObject);
        }
        rootJsonObject.put("abstract_method", abstractMethodsArray);
        System.out.println(rootJsonObject.toString());
    }

}   
