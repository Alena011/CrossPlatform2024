package sixth.third.ex1;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.HashSet;
import java.util.Set;

public class ChildrenNamesHandler extends DefaultHandler {
    private StringBuilder data;
    private Set<String> tags;

    public ChildrenNamesHandler() {
        this.data = new StringBuilder();
        this.tags = new HashSet<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        tags.add(qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(new String(ch, start, length).trim());
    }

    public void printTags() {
        System.out.println("List of tags in the XML document:");
        for (String tag : tags) {
            System.out.println(tag);
        }
    }

    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            ChildrenNamesHandler handler = new ChildrenNamesHandler();
            saxParser.parse("src/main/java/sixth/third/Children_Names_NY.xml", handler);
            handler.printTags();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
