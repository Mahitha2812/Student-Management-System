import java.io.*;
import java.util.*;

class Student implements Serializable {
    private int id;
    private String name;
    private int age;
    private String course;
    private double marks;

    public Student(int id, String name, int age, String course, double marks) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
        this.marks = marks;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getCourse() { return course; }
    public double getMarks() { return marks; }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setCourse(String course) { this.course = course; }
    public void setMarks(double marks) { this.marks = marks; }

    public void display() {
        System.out.println("ID: " + id +
                " | Name: " + name +
                " | Age: " + age +
                " | Course: " + course +
                " | Marks: " + marks);
    }
}

public class StudentManagementSystem {

    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    private static final String FILE_NAME = "students.dat";

    public static void main(String[] args) {
        loadFromFile();

        while (true) {
            System.out.println("\n===== SMART STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Sort by Marks");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1: addStudent(); break;
                case 2: viewStudents(); break;
                case 3: searchStudent(); break;
                case 4: updateStudent(); break;
                case 5: deleteStudent(); break;
                case 6: sortByMarks(); break;
                case 7:
                    saveToFile();
                    System.out.println("Data saved successfully. Exiting...");
                    System.exit(0);
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void addStudent() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Student s : students) {
            if (s.getId() == id) {
                System.out.println("ID already exists!");
                return;
            }
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Age: ");
        int age = sc.nextInt();

        sc.nextLine();
        System.out.print("Enter Course: ");
        String course = sc.nextLine();

        System.out.print("Enter Marks: ");
        double marks = sc.nextDouble();

        students.add(new Student(id, name, age, course, marks));
        System.out.println("Student added successfully!");
    }

    private static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }
        for (Student s : students) {
            s.display();
        }
    }

    private static void searchStudent() {
        System.out.print("Enter ID to search: ");
        int id = sc.nextInt();

        for (Student s : students) {
            if (s.getId() == id) {
                s.display();
                return;
            }
        }
        System.out.println("Student not found!");
    }

    private static void updateStudent() {
        System.out.print("Enter ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Student s : students) {
            if (s.getId() == id) {
                System.out.print("New Name: ");
                s.setName(sc.nextLine());

                System.out.print("New Age: ");
                s.setAge(sc.nextInt());

                sc.nextLine();
                System.out.print("New Course: ");
                s.setCourse(sc.nextLine());

                System.out.print("New Marks: ");
                s.setMarks(sc.nextDouble());

                System.out.println("Student updated successfully!");
                return;
            }
        }
        System.out.println("Student not found!");
    }

    private static void deleteStudent() {
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();

        students.removeIf(s -> s.getId() == id);
        System.out.println("Student deleted (if existed).");
    }

    private static void sortByMarks() {
        students.sort((s1, s2) -> Double.compare(s2.getMarks(), s1.getMarks()));
        System.out.println("Students sorted by marks (High to Low).");
        viewStudents();
    }

    private static void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    private static void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (ArrayList<Student>) ois.readObject();
        } catch (Exception e) {
            students = new ArrayList<>();
        }
    }
}