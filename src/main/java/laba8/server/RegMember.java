package laba8.server;

import laba8.pi.Member;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@XmlRootElement(name = "RegisteredMembers")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "members"
})
public class RegMember implements Iterable<Member>, Serializable {

    @XmlElement(name = "Member")
    private List<Member> members;

    @XmlTransient
    private List<RegMemberChangeListener> listeners = new ArrayList<>();

    @XmlTransient
    private RegMemberChangeEvent event = new RegMemberChangeEvent(this);

    public RegMember() {
        members = new ArrayList<>();
        fireDataSheetChange();
    }

    public RegMember(List<Member> members) {
        this.members = members;
        fireDataSheetChange();
    }


    public void addDRegisteredParticipantChangeListener(RegMemberChangeListener l) {
        listeners.add(l);
    }

    public void removeRegisteredParticipantChangeListener(RegMemberChangeListener l) {
        listeners.remove(l);
    }

    protected void fireDataSheetChange() {
        for (RegMemberChangeListener listener : listeners) listener.dataChange(event);
    }

    public void add(Member member) {
        members.add(member);
        fireDataSheetChange();
    }

    public void remove(Member member) {
        members.remove(member);
        fireDataSheetChange();
    }

    public Member get(int index) {
        return members.get(index);
    }

    public List<Member> getParticipants() {
        return members;
    }

    public void setParticipants(List<Member> members) {
        this.members = members;
        fireDataSheetChange();
    }

    @Override
    public Iterator<Member> iterator() {
        return members.iterator();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        members.forEach(e -> stringBuilder
                .append("======================")
                .append(System.lineSeparator())
                .append(e)
        );
        return stringBuilder.toString();
    }

    public int size(){
        return members.size();
    }
}
