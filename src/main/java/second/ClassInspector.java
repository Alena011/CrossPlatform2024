package second;

import java.lang.reflect.*;

public class ClassInspector {

    // Метод для аналізу класу
    public static String inspectClass(Class<?> clazz) {
        StringBuilder result = new StringBuilder();

        // Додамо інформацію про пакет та версію
        Package pack = clazz.getPackage();
        if (pack != null) {
            result.append("Пакет: ").append(pack.getName());
            result.append(", ").append("Версія: ").append(pack.getSpecificationTitle());
            result.append(", ").append("Версія: ").append(pack.getSpecificationVersion());
            result.append("\n");
        }

        // Додамо модифікатори та назву класу
        int modifiers = clazz.getModifiers();
        result.append("Модифікатори: ").append(Modifier.toString(modifiers)).append(", ").append("Назва класу: ").append(clazz.getSimpleName()).append(" {\n");

        // Додамо базовий клас
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            result.append("Наслідує: ").append(superClass.getSimpleName()).append(" ");
        }

        // Додамо інтерфейси, які реалізує клас
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length > 0) {
            result.append("Реалізує: ");
            for (int i = 0; i < interfaces.length; i++) {
                result.append(interfaces[i].getSimpleName());
                if (i < interfaces.length - 1) {
                    result.append(", ");
                }
            }
            result.append(" ");
        }
        result.append("{\n");

        // Додамо поля класу
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            result.append("Поле: ").append(Modifier.toString(field.getModifiers())).append(" ").append(field.getType().getSimpleName()).append(" ").append(field.getName()).append(";\n");
        }

        // Додамо конструктори
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            result.append("Конструктор: ").append(Modifier.toString(constructor.getModifiers())).append(" ").append(constructor.getName()).append(";\n");
        }

        // Додамо методи
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            result.append("Метод: ").append(Modifier.toString(method.getModifiers())).append(" ").append(method.getReturnType().getSimpleName()).append(" ").append(method.getName()).append("(");
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                result.append(parameters[i].getType().getSimpleName()).append(" ").append(parameters[i].getName());
                if (i < parameters.length - 1) {
                    result.append(", ");
                }
            }
            result.append(");\n");
        }

        result.append("}");

        return result.toString();
    }

    public static void main(String[] args) {
        // Перевірка роботи методу
        String className = "java.lang.String";
        try {
            Class<?> clazz = Class.forName(className);
            System.out.println(inspectClass(clazz));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
