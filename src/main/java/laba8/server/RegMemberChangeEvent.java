package laba8.server;

import java.util.EventObject;

public class RegMemberChangeEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public RegMemberChangeEvent(Object source) {
        super(source);
    }
}
