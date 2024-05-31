package laba5.ex2.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class ActivePerson implements Serializable, Iterable<Person>{
    private final ArrayList<Person> people;

    public ActivePerson() {
        this.people = new ArrayList<>();
    }

    public void add(Person person){
        people.add(person);
    }

    public boolean contains(Person person){
        return people.contains(person);
    }

    public int size(){
        return people.size();
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        people.forEach(u -> stringBuilder.append(u.toString()).append(System.lineSeparator()));
        return stringBuilder.toString();
    }

    @Override
    public Iterator<Person> iterator() {
        return people.iterator();
    }
}