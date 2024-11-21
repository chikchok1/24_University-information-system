/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.UIS.AcademicManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentFileManager {
    private final String filePath;

    public StudentFileManager(String filePath) {
        this.filePath = filePath;
    }

    public List<Student> readStudents() throws IOException {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String department = "", studentNumber = "", name = "", grade = "", birthDate = "", phone = "";

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("학과: ")) {
                    department = line.substring(4);
                } else if (line.startsWith("학번: ")) {
                    studentNumber = line.substring(4);
                } else if (line.startsWith("이름: ")) {
                    name = line.substring(4);
                } else if (line.startsWith("학년: ")) {
                    grade = line.substring(4);
                } else if (line.startsWith("생년월일: ")) {
                    birthDate = line.substring(6);
                } else if (line.startsWith("휴대폰: ")) {
                    phone = line.substring(5);
                } else if (line.isEmpty()) {
                    if (!department.isEmpty() && !studentNumber.isEmpty() && !name.isEmpty()
                            && !grade.isEmpty() && !birthDate.isEmpty() && !phone.isEmpty()) {
                        students.add(new Student(name, studentNumber, department, grade, birthDate, phone));
                    }
                    department = studentNumber = name = grade = birthDate = phone = "";
                }
            }
        }
        return students;
    }

    public void writeStudents(List<Student> students) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Student student : students) {
                writer.write("이름: " + student.getName() + "\n");
                writer.write("학번: " + student.getStudentNumber() + "\n");
                writer.write("학과: " + student.getDepartment() + "\n");
                writer.write("학년: " + student.getGrade() + "\n");
                writer.write("생년월일: " + student.getBirthDate() + "\n");
                writer.write("휴대폰: " + student.getPhone() + "\n\n");
            }
        }
    }
}

