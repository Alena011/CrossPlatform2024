package laba8.server;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class RegMemberSaveToXml {

    public static void marshalDataToXML(String fileName, RegMember regMember) throws JAXBException {
        Marshaller marshaller = JAXBContext.newInstance(RegMember.class).createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(regMember, new File(fileName));

    }

    public static RegMember unmarshalOutXMLs(String filePath) throws JAXBException {
        Unmarshaller unmarshaller = JAXBContext.newInstance(RegMember.class).createUnmarshaller();
        Object element = unmarshaller.unmarshal(new File(filePath));
        if (element instanceof RegMember) {
            return (RegMember) element;
        } else throw new JAXBException("is not RegisteredParticipant, check your filePath!!");
    }
}
