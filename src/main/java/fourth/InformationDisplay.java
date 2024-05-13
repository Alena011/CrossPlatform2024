package fourth;

public class InformationDisplay extends CardActivity {

    private final String serNum;

    public InformationDisplay(String serNum) {
        this.serNum = serNum;
    }

    public String getSerNum() {
        return serNum;
    }
}
