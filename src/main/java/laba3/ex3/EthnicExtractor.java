package laba3.ex3;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Set;

public class EthnicExtractor {
    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(false);
            SAXParser parser = factory.newSAXParser();
            EthnicHandler handler = new EthnicHandler();
            parser.parse("src/main/java/laba3/Children_Names_NY.xml", handler);
            Set<String> ethnicities = handler.getEthnicities();
            System.out.println("Назви етнічних груп:");
            for (String ethnicity : ethnicities) {
                System.out.println(ethnicity);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}