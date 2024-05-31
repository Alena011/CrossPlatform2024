package laba8.server;

import laba8.pi.CMethods;
import laba8.pi.Member;

import java.util.List;

public class CMethodsImpl implements CMethods {
    private RegMember participants;

    public CMethodsImpl() {
        participants = new RegMember();
    }

    @Override
    public synchronized void register(Member o) {
        participants.add(o);
    }

    @Override
    public synchronized List<Member> getAllClient() {
        return participants.getParticipants();
    }

    public RegMember getParticipants() {
        return participants;
    }

    public void setParticipants(RegMember participants) {
        this.participants.setParticipants(participants.getParticipants());
    }
}
