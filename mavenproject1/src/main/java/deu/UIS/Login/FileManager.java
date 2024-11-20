/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.UIS.Login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author YangJinWon
 */
public class FileManager {

    public static boolean verifyUserCredentials(String filePath, String enteredId, String enteredPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 2) {
                    String fileId = parts[0].substring(4).trim(); // "ID: " 제거
                    String filePassword = parts[1].substring(10).trim(); // "Password: " 제거

                    if (enteredId.equals(fileId) && enteredPassword.equals(filePassword)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 읽기 오류", e);
        }
        return false;
    }
    //학생 인증
    public static boolean verifyStudentCredentials(String filePath, String enteredId, String enteredPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line, studentId = "", birthLast = "";

            while ((line = reader.readLine()) != null) {
                line = line.trim(); // 앞뒤 공백 제거

                if (line.startsWith("학번: ")) {
                    studentId = line.substring(4).trim();
                } else if (line.startsWith("생년월일: ")) {
                    String[] birthParts = line.substring(6).split("-");
                    if (birthParts.length == 2) {
                        birthLast = birthParts[1].trim();
                    }
                } else if (line.isEmpty()) {
                    if (enteredId.equals(studentId) && enteredPassword.equals(birthLast)) {
                        return true;
                    
                    }
                    // 데이터 초기화
                    studentId = "";
                    birthLast = "";
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 읽기 오류", e);
        }
        return false;
    }
    
    //수업담당자 인증
    public static boolean verifyCourseManagerCredentials(String filePath, String enteredId, String enteredPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line, managerId = "", departmentCode = "";

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("담당자ID: ")) {
                    managerId = line.substring(8).trim();
                } else if (line.startsWith("부서코드: ")) {
                    departmentCode = line.substring(6).trim();
                } else if (line.isEmpty()) {
                    if (enteredId.equals(managerId) && enteredPassword.equals(departmentCode)) {
                        return true;
                    }
                    managerId = "";
                    departmentCode = "";
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 읽기 오류", e);
        }
        return false;
    }

    //교수
    public static boolean verifyProfessorCredentials(String filePath, String enteredId, String enteredPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line, professorNumber = "", birthLast = "";

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("교수번호: ")) {
                    professorNumber = line.substring(6).trim();
                } else if (line.startsWith("생년월일: ")) {
                    String[] birthParts = line.substring(6).split("-");
                    if (birthParts.length == 2) {
                        birthLast = birthParts[1].trim();
                    }
                } else if (line.isEmpty()) {
                    if (enteredId.equals(professorNumber) && enteredPassword.equals(birthLast)) {
                        return true;
                    }
                    professorNumber = "";
                    birthLast = "";
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 읽기 오류", e);
        }
        return false;
    }
//학사 담당자 인증
    public static boolean verifyAcademicCredentials(String filePath, String enteredId, String enteredPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line, professorNumber = "", birthLast = "";

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("교수번호: ")) {
                    professorNumber = line.substring(6).trim();
                } else if (line.startsWith("생년월일: ")) {
                    String[] birthParts = line.substring(6).split("-");
                    if (birthParts.length == 2) {
                        birthLast = birthParts[1].trim();
                    }
                } else if (line.isEmpty()) {
                    if (enteredId.equals(professorNumber) && enteredPassword.equals(birthLast)) {
                        return true;
                    }
                    professorNumber = "";
                    birthLast = "";
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 읽기 오류", e);
        }
        return false;
    }
    public static void cleanUpFile(String filePath) {
        StringBuilder cleanedContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    cleanedContent.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 읽기 오류", e);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(cleanedContent.toString());
        } catch (IOException e) {
            throw new RuntimeException("파일 쓰기 오류", e);
        }
    }

}
