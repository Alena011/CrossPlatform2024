package laba8;

import laba8.pi.Member;
import laba8.server.RegMember;
import laba8.server.RegMemberSaveToXml;

import javax.xml.bind.JAXBException;

public class Test {

    public static void main(String[] args) throws JAXBException {
        RegMember participant = new RegMember();
        participant.addDRegisteredParticipantChangeListener(e->{
            System.out.println("Change");
        });
        participant.add(new Member("test name 1","test family 1","test org 1","test report 1","test1@email.com"));
        participant.add(new Member("test name 2","test family 2","test org 2","test report 2","test2@email.com"));
        participant.add(new Member("test name 3","test family 3","test org 3","test report 3","test3@email.com"));

        RegMemberSaveToXml.marshalDataToXML("output.xml", participant);
        System.out.println(RegMemberSaveToXml.unmarshalOutXMLs("output.xml"));
    }
}