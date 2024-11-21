/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.UIS.AcademicManager;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProfessorFileManager {
    private final String filePath;

    public ProfessorFileManager(String filePath) {
        this.filePath = filePath;
        ensureFileExists(); // 파일 존재 여부 확인
    }

    private void ensureFileExists() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<Professor> readProfessors() throws IOException {
        List<Professor> professors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line, name = "", professorNumber = "", department = "", birthDate = "", phone = "";
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("이름: ")) name = line.substring(4);
                else if (line.startsWith("교수번호: ")) professorNumber = line.substring(6);
                else if (line.startsWith("학과: ")) department = line.substring(4);
                else if (line.startsWith("생년월일: ")) birthDate = line.substring(6);
                else if (line.startsWith("휴대폰: ")) phone = line.substring(5);
                else if (line.isEmpty() && !name.isEmpty() && !professorNumber.isEmpty()) {
                    professors.add(new Professor(name, professorNumber, department, birthDate, phone));
                    name = professorNumber = department = birthDate = phone = ""; // Reset
                }
            }
        }
        return professors;
    }

    public synchronized void writeProfessors(List<Professor> professors) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Professor professor : professors) {
                writer.write("이름: " + professor.getName() + "\n");
                writer.write("교수번호: " + professor.getProfessorNumber() + "\n");
                writer.write("학과: " + professor.getDepartment() + "\n");
                writer.write("생년월일: " + professor.getBirthDate() + "\n");
                writer.write("휴대폰: " + professor.getPhone() + "\n\n");
            }
        }
    }
}
