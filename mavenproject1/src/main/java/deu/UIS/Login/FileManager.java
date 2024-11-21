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

    // 학생 인증
    public static boolean verifyStudentCredentials(String filePath, String enteredId, String enteredPassword) {
        return verifyCredentials(filePath, "학번: ", "비밀번호: ", enteredId, enteredPassword);
    }

    // 수업담당자 인증
    public static boolean verifyCourseManagerCredentials(String filePath, String enteredId, String enteredPassword) {
        return verifyCredentials(filePath, "수업담당자번호: ", "비밀번호: ", enteredId, enteredPassword);
    }

    // 교수 인증
    public static boolean verifyProfessorCredentials(String filePath, String enteredId, String enteredPassword) {
        return verifyCredentials(filePath, "교수번호: ", "비밀번호: ", enteredId, enteredPassword);
    }

    // 학사 담당자 인증
    public static boolean verifyAcademicCredentials(String filePath, String enteredId, String enteredPassword) {
        return verifyCredentials(filePath, "학사담당자번호: ", "비밀번호: ", enteredId, enteredPassword);
    }

    // 공통 인증 메서드
    private static boolean verifyCredentials(String filePath, String idPrefix, String passwordPrefix, String enteredId, String enteredPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line, id = "", password = "";

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith(idPrefix)) {
                    id = line.substring(idPrefix.length()).trim();
                } else if (line.startsWith(passwordPrefix)) {
                    password = line.substring(passwordPrefix.length()).trim();
                } else if (line.isEmpty()) {
                    if (enteredId.equals(id) && enteredPassword.equals(password)) {
                        return true;
                    }
                    id = "";
                    password = "";
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
