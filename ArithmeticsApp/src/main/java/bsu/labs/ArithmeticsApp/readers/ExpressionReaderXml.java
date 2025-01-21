package bsu.labs.ArithmeticsApp.readers;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component("XmlReader")
public class ExpressionReaderXml implements ExpressionReader{
    @Override
    public List<String> read(String filename) {
        List<String> expressionStringList = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            File file = new File("src/main/resources/" + filename);
            if (!file.exists()) {
                return null;
            }
            Document document = builder.parse(file);

            Element root = document.getDocumentElement();
            NodeList expressionStringNodes = root.getElementsByTagName("expressionString");

            for (int i = 0; i < expressionStringNodes.getLength(); i++) {
                Element expressionStringElement = (Element) expressionStringNodes.item(i);
                String expressionString = expressionStringElement.getTextContent();
                if (!expressionString.isEmpty()){
                    expressionStringList.add(expressionString);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expressionStringList;
    }
}