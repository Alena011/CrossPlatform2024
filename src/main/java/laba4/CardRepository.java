package laba4;

import java.util.ArrayList;

public class CardRepository {

    private final ArrayList<TransitCard> store;

    public CardRepository() {
        this.store = new ArrayList<>();
    }

    // Пошук пластикової картки за серійним номером
    public int findMetroCard(String serNum) {
        for (int i = 0; i < store.size(); i++) {
            if (store.get(i).getSerNum().equals(serNum)) {
                return i;
            }
        }
        return -1;
    }

    // Додавання пластикової картки до сховища
    public void addCard(TransitCard newCard) {
        store.add(newCard);
    }

    // Видалення картки за серійним номером
    public boolean removeCard(String serNum) {
        int index = findMetroCard(serNum);
        if (index != -1) {
            store.remove(index);
            return true;
        }
        return false;
    }

    // Поповнення рахунку для пластикової картки за номером
    public boolean addMoney(String serNum, double money) {
        int index = findMetroCard(serNum);
        if (index != -1) {
            TransitCard card = store.get(index);
            card.setBalance(card.getBalance() + money);
            return true;
        }
        return false;
    }

    // Оплата поїздки для пластикової картки за номером
    public boolean getMoney(String serNum, double money) {
        int index = findMetroCard(serNum);
        if (index != -1) {
            TransitCard card = store.get(index);
            if (card.getBalance() >= money) {
                card.setBalance(card.getBalance() - money);
                return true;
            }
        }
        return false;
    }

    public ArrayList<TransitCard> getStore() {
        return store;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("List of MetroCards:");
        for (TransitCard c : store) {
            buf.append("\n\n").append(c);
        }
        return buf.toString();
    }
}
