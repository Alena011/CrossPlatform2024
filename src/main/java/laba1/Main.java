package laba1;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Scanner;

class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double distance() {
        return Math.sqrt(x * x + y * y);
    }

    public void setRandomData() {
        x = Math.random() * 10;
        y = Math.random() * 10;
    }

    @Override
    public String toString() {
        return "first.Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Створення об'єкту...");
        Point point = new Point(10, 10);
        System.out.println("Стан об'єкту:");
        printObjectState(point);

        System.out.println("Виклик методу...");
        printAccessibleMethods(point);

        System.out.println("Введіть порядковий номер методу [1, " + accessibleMethodsCount(point) + "]:");
        int choice = scanner.nextInt();
        callMethod(point, choice);
    }

    private static void printObjectState(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                System.out.println(field.getType().getName() + " " + field.getName() + " = " + field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void printAccessibleMethods(Object obj) {
        Method[] methods = obj.getClass().getDeclaredMethods();
        int count = 1;
        System.out.println("Список доступних методів:");
        for (Method method : methods) {
            if (method.getParameterCount() == 0 && method.getReturnType() != void.class) {
                System.out.println(count + "). " + method.toString());
                count++;
            }
        }
    }

    private static int accessibleMethodsCount(Object obj) {
        Method[] methods = obj.getClass().getDeclaredMethods();
        int count = 0;
        for (Method method : methods) {
            if (method.getParameterCount() == 0 && method.getReturnType() != void.class) {
                count++;
            }
        }
        return count;
    }

    private static void callMethod(Object obj, int choice) {
        Method[] methods = obj.getClass().getDeclaredMethods();
        int count = 0;
        for (Method method : methods) {
            if (method.getParameterCount() == 0 && method.getReturnType() != void.class) {
                count++;
                if (count == choice) {
                    try {
                        Object result = method.invoke(obj);
                        System.out.println("Результат виклику методу: " + result.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }
}
