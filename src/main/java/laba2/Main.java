package laba2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Клас, що представляє книгу
class Book implements Serializable {
    private String title;
    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    // Геттери та сеттери
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // Перевизначений метод toString()
    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}

// Клас, що представляє читача
class Reader implements Serializable {
    private String name;

    public Reader(String name) {
        this.name = name;
    }

    // Геттери та сеттери
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Перевизначений метод toString()
    @Override
    public String toString() {
        return "Reader{" +
                "name='" + name + '\'' +
                '}';
    }
}

// Клас, що представляє бібліотеку
class Library implements Serializable {
    private List<Book> books;
    private List<Reader> readers;

    public Library() {
        this.books = new ArrayList<>();
        this.readers = new ArrayList<>();
    }

    // Метод для додавання книги до бібліотеки
    public void addBook(Book book) {
        books.add(book);
    }

    // Метод для додавання читача до бібліотеки
    public void addReader(Reader reader) {
        readers.add(reader);
    }

    // Метод для виведення інформації про бібліотеку
    public void display() {
        System.out.println("Books:");
        for (Book book : books) {
            System.out.println(book);
        }
        System.out.println("\nReaders:");
        for (Reader reader : readers) {
            System.out.println(reader);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Створення бібліотеки та додавання книг та читачів
        Library library = new Library();
        library.addBook(new Book("Евгений Онегин", "Александр Пушкин"));
        library.addBook(new Book("Преступление и наказание", "Федор Достоевский"));
        library.addReader(new Reader("John Doe"));
        library.addReader(new Reader("Jane Doe"));

        // Виведення інформації про бібліотеку
        System.out.println("Library contents before serialization:");
        library.display();

        // Серіалізація об'єкту бібліотеки у файл
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("library.ser"))) {
            oos.writeObject(library);
            System.out.println("\nLibrary serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Десеріалізація об'єкту бібліотеки з файлу
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("library.ser"))) {
            Library deserializedLibrary = (Library) ois.readObject();
            System.out.println("\nLibrary deserialized successfully.");

            // Виведення інформації про десеріалізовану бібліотеку
            System.out.println("\nDeserialized library contents:");
            deserializedLibrary.display();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
