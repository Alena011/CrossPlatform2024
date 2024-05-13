package fourth;

public class PaymentProcessing extends CardActivity {

    private final TransitCard transitCard;

    private final double amount;

    public PaymentProcessing(TransitCard transitCard, double amount) {
        this.transitCard = transitCard;
        this.amount = amount;
    }

    public TransitCard getMetroCard() {
        return transitCard;
    }

    public double getAmount() {
        return amount;
    }
}
