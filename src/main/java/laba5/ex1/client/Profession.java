package laba5.ex1.client;


import laba5.ex1.interfaces.Performer;

import java.io.Serializable;
import java.math.BigInteger;

public class Profession implements Performer, Serializable {
    private static final long serialVersionUID = -1L;
    private int n;


    public Profession(int n) {
        this.n = n;
    }

    @Override
    public Object perform() {
        BigInteger res = BigInteger.ONE;
        return res;
    }
}
