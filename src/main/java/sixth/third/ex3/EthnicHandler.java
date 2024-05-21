package sixth.third.ex3;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashSet;
import java.util.Set;

public class EthnicHandler extends DefaultHandler {
    private Set<String> ethnicities = new HashSet<>();
    private boolean isInEthnicity = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("ethcty")) {
            isInEthnicity = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("ethcty")) {
            isInEthnicity = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (isInEthnicity) {
            String ethnicity = new String(ch, start, length).trim();
            if (!ethnicity.isEmpty()) {
                ethnicities.add(ethnicity);
            }
        }
    }

    public Set<String> getEthnicities() {
        return ethnicities;
    }
}