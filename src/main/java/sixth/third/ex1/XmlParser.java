package sixth.third.ex1;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.Scanner;

public class XmlParser extends DefaultHandler {
    private StringBuilder currentElement = new StringBuilder();
    private boolean printing = false;
    private String desiredTagName;

    public XmlParser(String desiredTagName) {
        this.desiredTagName = desiredTagName;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement.setLength(0);
        if (desiredTagName.equals(qName)) {
            printing = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        currentElement.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (printing) {
            System.out.println("Element: " + qName + ", Content: " + currentElement.toString().trim());
        }
        if (desiredTagName.equals(qName)) {
            printing = false;
        }
    }

    public static void main(String[] args) {
        try {
            // Шлях до XML-файлу
            String xmlFilePath = "src/main/java/sixth/third/Children_Names_NY.xml";

            // Створення фабрики парсерів
            SAXParserFactory factory = SAXParserFactory.newInstance();

            // Створення парсера
            SAXParser parser = factory.newSAXParser();

            // Запитуємо користувача про назву тегу
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введіть назву тегу: ");
            String desiredTagName = scanner.nextLine();

            // Створення обробника подій з введеною користувачем назвою тегу
            XmlParser handler = new XmlParser(desiredTagName);

            // Парсинг XML-файлу
            parser.parse(new File(xmlFilePath), handler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
