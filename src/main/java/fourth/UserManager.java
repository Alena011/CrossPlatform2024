package fourth;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class UserManager extends Thread {

    private ObjectInputStream is = null;

    private ObjectOutputStream os = null;

    private boolean work;

    private final CardRepository bnk;

    private final Socket s;

    public UserManager(CardRepository bnk, Socket s) {
        this.bnk = bnk;
        this.s = s;
        this.work = true;
        try {
            this.is = new ObjectInputStream(s.getInputStream());
            this.os = new ObjectOutputStream(s.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    @Override
    public void run() {
        synchronized (bnk) {
            System.out.println("Client Handler Started for: " + s);
            while (work) {
                Object obj;
                try {
                    obj = is.readObject();
                    processOperation(obj);
                } catch (IOException e) {
                    System.out.println("Error: " + e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Error: " + e);
                }
            }
            try {
                System.out.println("Client Handler Stopped for: " + s);
                s.close();
            } catch (IOException ex) {
                System.out.println("Error: " + ex);
            }
        }
    }

    private void processOperation(Object obj) throws IOException, ClassNotFoundException {
        if (obj instanceof ShutdownProcedure) {
            finish();
        } else if (obj instanceof MetroCardAddition) {
            addCard(obj);
        } else if (obj instanceof FundsAddition) {
            addMoney(obj);
        } else if (obj instanceof PaymentProcessing) {
            payMoney(obj);
        } else if (obj instanceof CardRemoval) {
            removeCard(obj);
        } else if (obj instanceof BalanceDisplay) {
            showBalance(obj);
        } else if (obj instanceof InformationDisplay) {
            showInfo(obj);
        } else {
            error();
        }
    }

    private void finish() throws IOException {
        work = false;
        os.writeObject("Finish Work " + s);
        os.flush();
    }

    private void addCard(Object obj) throws IOException, ClassNotFoundException {
        bnk.addCard(((MetroCardAddition) obj).getCrd());
        os.writeObject("Card Added");
        os.flush();
    }

    private void addMoney(Object obj) throws IOException, ClassNotFoundException {
        FundsAddition op = (FundsAddition) obj;
        boolean res = bnk.addMoney(op.getSerNum(), op.getMoney());
        if (res) {
            os.writeObject("Balance Added");
        } else {
            os.writeObject("Cannot Balance Added");
        }
        os.flush();
    }

    private void payMoney(Object obj) throws IOException, ClassNotFoundException {
        PaymentProcessing op = (PaymentProcessing) obj;
        boolean res = bnk.getMoney(op.getMetroCard().getSerNum(), op.getAmount());
        if (res) {
            os.writeObject("Money Paid");
        } else {
            os.writeObject("Cannot Pay Money");
        }
        os.flush();
    }

    private void removeCard(Object obj) throws IOException, ClassNotFoundException {
        CardRemoval op = (CardRemoval) obj;
        boolean res = bnk.removeCard(op.getSerNum());
        if (res) {
            os.writeObject("Metro Card Successfully Removed: " + op.getSerNum());
        } else {
            os.writeObject("Cannot Remove Card: " + op.getSerNum());
        }
        os.flush();
    }

    private void showBalance(Object obj) throws IOException, ClassNotFoundException {
        BalanceDisplay op = (BalanceDisplay) obj;
        int ind = bnk.findMetroCard(op.getSerNum());
        if (ind >= 0) {
            os.writeObject("Card : " + op.getSerNum() + " balance: " + bnk.getStore().get(ind).getBalance());
        } else {
            os.writeObject("Cannot Show Balance for Card: " + op.getSerNum());
        }
        os.flush();
    }

    private void showInfo(Object obj) throws IOException {
        InformationDisplay op = (InformationDisplay) obj;
        int ind = bnk.findMetroCard(op.getSerNum());
        if (ind >= 0) {
            os.writeObject("Card Info: " + bnk.getStore().get(ind).toString());
        } else {
            os.writeObject("Cannot Show Info for Card: " + op.getSerNum());
        }
        os.flush();
    }

    private void error() throws IOException {
        os.writeObject("Bad Operation");
        os.flush();
    }

    public CardRepository getBnk() {
        return bnk;
    }
}
