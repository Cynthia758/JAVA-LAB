import java.io.*;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    int id;
    String name;
    transient String password;

    Student(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Password: " + password;
    }
}
public class SaveLoadDemo {
    public static void main(String[] args) {
        Student[] students = {
            new Student(101, "Aarav", "pass123"),
            new Student(102, "Diya", "pass456"),
            new Student(103, "Riya", "pass789")
        };

        try {
            ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("students.dat")
            );

            out.writeObject(students);
            out.close();

            ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("students.dat")
            );

            Student[] loadedStudents = (Student[]) in.readObject();
            in.close();

            System.out.println("Objects loaded successfully:");

            for (Student student : loadedStudents) {
                System.out.println(student);
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
