/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.UIS.Login;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author YangJinWon
 */
public class FileManager {
   public static boolean verifyUserCredentials(String filePath, String idPrefix, String passwordPrefix, String enteredId, String enteredPassword) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        String id = null, password = null;

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (line.startsWith(idPrefix)) {
                id = line.substring(idPrefix.length()).trim();
            } else if (line.startsWith(passwordPrefix)) {
                password = line.substring(passwordPrefix.length()).trim();
            }

            if (line.isEmpty()) {
                System.out.println("파일에서 읽은 ID: " + id + ", 비밀번호: " + password);
                if (enteredId.equals(id) && enteredPassword.equals(password)) {
                    return true;
                }
                id = null;
                password = null;
            }
        }

        // 마지막 데이터 확인
        if (enteredId.equals(id) && enteredPassword.equals(password)) {
            return true;
        }
    } catch (IOException e) {
        System.err.println("파일 읽기 오류: " + e.getMessage());
    }

    return false;
}
}