/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.UIS.Login;
import java.io.IOException;
import java.nio.file.Paths;
/**
 *
 * @author YangJinWon
 */
public class LoginService {

    private static final String USER_FILE_PATH = Paths.get(System.getProperty("user.home"), "data", "user_data.txt").toString();
    private static final String PROFESSOR_FILE_PATH = Paths.get(System.getProperty("user.home"), "data", "professor_info.txt").toString();
    private static final String ACADEMIC_FILE_PATH = Paths.get(System.getProperty("user.home"), "data", "Academic_info.txt").toString();
    private static final String STUDENT_FILE_PATH = Paths.get(System.getProperty("user.home"), "data", "student_info.txt").toString();
    private static final String COURSE_MANAGER_FILE_PATH = Paths.get(System.getProperty("user.home"), "data", "Class_Manager.txt").toString();

    static {
        // Ensure the directory exists
        try {
            java.nio.file.Files.createDirectories(Paths.get(System.getProperty("user.home"), "data"));
        } catch (IOException e) {
            throw new RuntimeException("데이터 디렉토리 생성 중 오류 발생", e);
        }
    }

    public static boolean authenticateUser(String enteredId, String enteredPassword) {
        // 기존 인증 로직 유지
        if (FileManager.verifyUserCredentials(USER_FILE_PATH, enteredId, enteredPassword)) {
            return true;
        }
        if (FileManager.verifyProfessorCredentials(PROFESSOR_FILE_PATH, enteredId, enteredPassword)) {
            return true;
        }
        if (FileManager.verifyAcademicCredentials(ACADEMIC_FILE_PATH, enteredId, enteredPassword)) {
            return true;
        }
        if (FileManager.verifyStudentCredentials(STUDENT_FILE_PATH, enteredId, enteredPassword)) {
            return true;
        }
        if (FileManager.verifyCourseManagerCredentials(COURSE_MANAGER_FILE_PATH, enteredId, enteredPassword)) {
            return true;
        }
        return false;
    }

    public static String determineUserRole(String userId) {
        if (userId.startsWith("S")) return "STUDENT";
        if (userId.startsWith("P")) return "PROFESSOR";
        if (userId.startsWith("H")) return "ADMIN_STAFF";
        if (userId.startsWith("G")) return "COURSE_MANAGER";
        if (userId.startsWith("A")) return "ADMIN";
        return "UNKNOWN";
    }
}

