package bsu.labs.ArithmeticsApp.writers;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

@Component("XmlWriter")
public class ExpressionWriterXml implements ExpressionWriter{
    @Override
    public void writeResult(List<String> data, String fileName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element rootElement = document.createElement("expressionRequest");
            document.appendChild(rootElement);

            for (var expressionString : data) {
                if(!expressionString.isEmpty()){
                    Element expressionElement = document.createElement("expressionString");
                    expressionElement.appendChild(document.createTextNode(expressionString));
                    rootElement.appendChild(expressionElement);
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("src/main/resources/" + fileName));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}