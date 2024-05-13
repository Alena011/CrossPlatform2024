package fourth;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserRegistry implements Serializable {

    private static final DateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

    private static final DateFormat dateParser = dateFormatter;

    private final String name;

    private final String surName;

    private final String sex;

    private Date birthday;

    public UserRegistry(String name, String surName, String sex, String birthday) {
        this.name = name;
        this.surName = surName;
        this.sex = sex;
        try {
            this.birthday = dateParser.parse(birthday);
        } catch (ParseException ex) {
            System.out.println("Error: " + ex);
        }
    }

    @Override
    public String toString() {
        return name + " " + surName + " " + sex +
                " " + dateFormatter.format(birthday);
    }
}
