package laba5.ex1.server;


import laba5.ex1.interfaces.Finish;

import java.io.Serializable;

public class ResultImpl implements Finish, Serializable {
    Object output;
    double scoreTime;
    public ResultImpl(Object o, double c) {
        output = o;
        scoreTime = c;
    }
    public Object output() {
        return output;
    }
    public double scoreTime() {
        return scoreTime;
    }
}
