package laba2;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class MyBook implements Externalizable {
    private String title;
    private String author;

    public MyBook() {}

    public MyBook(String title, String author) {
        this.title = title;
        this.author = author;
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        title = (String) in.readObject();
        author = (String) in.readObject();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(title);
        out.writeObject(author);
    }

    @Override
    public String toString() {
        return "MyBook{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}

class BookStore implements Externalizable {
    private String name;
    private List<MyBook> books;

    public BookStore() {}

    public BookStore(String name, List<MyBook> books) {
        this.name = name;
        this.books = books;
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        int count = in.readInt();
        books = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            MyBook book = new MyBook();
            book.readExternal(in);
            books.add(book);
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeInt(books.size());
        for (MyBook book : books) {
            book.writeExternal(out);
        }
    }

    @Override
    public String toString() {
        return "BookStore{" +
                "name='" + name + '\'' +
                ", books=" + books +
                '}';
    }
}

class LibraryDriver {
    public static void main(String[] args) {
        List<MyBook> books = new ArrayList<>();
        books.add(new MyBook("Евгений Онегин", "Александр Пушкин"));
        books.add(new MyBook("Преступление и наказание", "Федор Достоевский"));

        BookStore bookStore = new BookStore("Книжный магазин", books);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("bookstore.ser"))) {
            oos.writeObject(bookStore);
            System.out.println("Bookstore serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("bookstore.ser"))) {
            BookStore deserializedBookStore = (BookStore) ois.readObject();
            System.out.println("Bookstore deserialized successfully: " + deserializedBookStore);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
