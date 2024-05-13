package fourth;

import java.io.Serializable;

public class TransitCard implements Serializable{

    private String serNum;

    private UserRegistry usr;

    private String college;

    private double balance;

    public String getSerNum() {
        return serNum;
    }

    public void setSerNum(String serNum) {
        this.serNum = serNum;
    }

    public void setUsr(UserRegistry usr) {
        this.usr = usr;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "No: " + serNum + "\nUser: " + usr +
                "\nCollege: " + college +
                "\nBalance: " + balance;
    }
}
