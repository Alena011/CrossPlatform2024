package second;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Computable {
    double compute(double x);
}

class FunctionOne implements Computable {
    @Override
    public double compute(double x) {
        double a = 2.5;
        return Math.exp(-Math.abs(a) * x) * Math.sin(x);
    }
}

class FunctionTwo implements Computable {
    @Override
    public double compute(double x) {
        return x * x;
    }
}

class ProfilingHandler implements InvocationHandler {
    private final Object target;

    public ProfilingHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.nanoTime();
        Object result = method.invoke(target, args);
        long endTime = System.nanoTime();
        System.out.println(method.getName() + " took " + (endTime - startTime) + " ns");
        return result;
    }
}

class TracingHandler implements InvocationHandler {
    private final Object target;

    public TracingHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.print(method.getName() + ": ");
        Object result = method.invoke(target, args);
        if (args != null && args.length > 0) {
            System.out.println(method.getName() + "(" + args[0] + ") = " + result);
        } else {
            System.out.println(result);
        }
        return result;
    }
}

public class Main {
    public static void main(String[] args) {
        Computable functionOne = new FunctionOne();
        Computable functionTwo = new FunctionTwo();

        Computable functionOneProxy = (Computable) Proxy.newProxyInstance(
                first.Main.class.getClassLoader(),
                new Class[]{Computable.class},
                new ProfilingHandler(functionOne));

        Computable functionTwoProxy = (Computable) Proxy.newProxyInstance(
                first.Main.class.getClassLoader(),
                new Class[]{Computable.class},
                new TracingHandler(functionTwo));

        double x = 1.0;
        System.out.println("F1: " + functionOneProxy.compute(x));
        System.out.println("F2: " + functionTwoProxy.compute(x));
        System.out.println("F1: " + functionOneProxy.compute(x));
        System.out.println("F2: " + functionTwoProxy.compute(x));
    }
}
