package sixth.second;

import javax.swing.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CallHandler implements InvocationHandler {

    private Object result;
    private Task ui;

    public CallHandler(Task ui) {
        this.ui = ui;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (SwingUtilities.isEventDispatchThread()) {
            return result = method.invoke(ui, args);
        }

        SwingUtilities.invokeAndWait(() -> {
            try {
                result = method.invoke(ui, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        return result;
    }
}