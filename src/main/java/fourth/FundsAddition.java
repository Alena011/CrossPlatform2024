package fourth;

public class FundsAddition extends CardActivity {

    private final String serNum;

    private final double money;

    public FundsAddition(String serNum, double money) {
        this.serNum = serNum;
        this.money = money;
    }

    public String getSerNum() {
        return serNum;
    }

    public double getMoney() {
        return money;
    }
}
