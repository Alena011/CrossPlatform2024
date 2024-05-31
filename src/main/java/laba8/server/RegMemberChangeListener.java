package laba8.server;

import java.util.EventListener;

public interface RegMemberChangeListener extends EventListener {
    void  dataChange(RegMemberChangeEvent e);
}
