/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.UIS.Login;

/**
 *
 * @author YangJinWon
 */
public class LoginService {

    private static final String USER_FILE_PATH = System.getProperty("user.home") + "/user_data.txt";
    private static final String PROFESSOR_FILE_PATH = System.getProperty("user.home") + "/data/professor_info.txt";
    private static final String ACADEMIC_FILE_PATH = System.getProperty("user.home") + "/data/Academic_info.txt";
    private static final String STUDENT_FILE_PATH = System.getProperty("user.home") + "/data/student_info.txt";
    private static final String COURSE_MANAGER_FILE_PATH = System.getProperty("user.home") + "/data/Class_Manager.txt";
    
   public static boolean authenticateUser(String enteredId, String enteredPassword) {
        // 1. 일반 사용자 인증
        if (FileManager.verifyUserCredentials(USER_FILE_PATH, enteredId, enteredPassword)) {
            return true;
        }

        // 2. 교수 인증
        if (FileManager.verifyProfessorCredentials(PROFESSOR_FILE_PATH, enteredId, enteredPassword)) {
            return true;
        }

        // 3. 학사 담당자 인증
        if (FileManager.verifyAcademicCredentials(ACADEMIC_FILE_PATH, enteredId, enteredPassword)) {
            return true;
        }

        // 4. 학생 인증
        if (FileManager.verifyStudentCredentials(STUDENT_FILE_PATH, enteredId, enteredPassword)) {
            return true;
        }

        // 5. 수업 담당자 인증
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
