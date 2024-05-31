package laba3.ex4;

public class Child {
    private String name;
    private String gender;
    private int count;
    private int rank;

    public Child(String name, String gender, int count, int rank) {
        this.name = name;
        this.gender = gender;
        this.count = count;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getCount() {
        return count;
    }

    public int getRank() {
        return rank;
    }
}