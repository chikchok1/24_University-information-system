/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.UIS.Login;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

/**
 *
 * @author YangJinWon
 */
public class LoginService {

    // 역할별 파일 경로 정의
    private static final String STUDENT_FILE_PATH = Paths.get(System.getProperty("user.home"), "data", "student_info.txt").toString();
    private static final String PROFESSOR_FILE_PATH = Paths.get(System.getProperty("user.home"), "data", "professor_info.txt").toString();
    private static final String ACADEMIC_FILE_PATH = Paths.get(System.getProperty("user.home"), "data", "Academic_info.txt").toString();
    private static final String COURSE_MANAGER_FILE_PATH = Paths.get(System.getProperty("user.home"), "data", "Class_Manager.txt").toString();
    private static final String ADMIN_FILE_PATH = Paths.get(System.getProperty("user.home"), "data", "user_data.txt").toString(); // Admin용 파일 경로

    /**
     * 사용자 인증 메서드: ID와 비밀번호가 각 역할의 파일에 존재하는지 확인.
     */
    public static boolean authenticateUser(String enteredId, String enteredPassword) {
        if (enteredId.startsWith("S")) { // 학생
            return FileManager.verifyUserCredentials(STUDENT_FILE_PATH, "학번: ", "비밀번호: ", enteredId, enteredPassword);
        }
        if (enteredId.startsWith("P")) { // 교수
            return FileManager.verifyUserCredentials(PROFESSOR_FILE_PATH, "교수번호: ", "비밀번호: ", enteredId, enteredPassword);
        }
        if (enteredId.startsWith("H")) { // 학사담당자
            return FileManager.verifyUserCredentials(ACADEMIC_FILE_PATH, "학사담당자번호: ", "비밀번호: ", enteredId, enteredPassword);
        }
        if (enteredId.startsWith("G")) { // 수업담당자
            return FileManager.verifyUserCredentials(COURSE_MANAGER_FILE_PATH, "수업담당자번호: ", "비밀번호: ", enteredId, enteredPassword);
        }
        if (enteredId.equals("Admin")) { // Admin
            return verifyAdminCredentials(enteredPassword);
        }
        return false; // 알 수 없는 역할
    }

    private static boolean verifyAdminCredentials(String enteredPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ADMIN_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // ID와 Password를 한 줄에서 처리
                if (line.equals("ID: Admin, Password: " + enteredPassword)) {
                    return true; // Admin 인증 성공
                }
            }
        } catch (IOException e) {
            System.err.println("파일 읽기 오류: " + e.getMessage());
        }
        return false; // Admin 인증 실패
    }

    public static String getUserNameForId(String userId) {

        if (userId.equals("Admin")) {
            return "Admin"; // Admin의 이름은 고정
        }

        String filePath = determineFilePath(userId); // ID에 따른 파일 경로 결정
        if (filePath == null) {
            System.err.println("잘못된 ID 형식: " + userId);
            return null; // 올바르지 않은 ID 형식
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String currentName = null;
            boolean isMatchingBlock = false; // 현재 블록이 ID와 일치하는지 여부

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // ID와 매칭 확인 (수업담당자, 학사담당자, 학생, 교수 모두 처리)
                if (line.contains(userId)) {
                    isMatchingBlock = true; // ID와 일치하는 블록 시작
                } else if (line.isEmpty()) {
                    isMatchingBlock = false; // 빈 줄로 블록 종료
                }

                // 이름 찾기
                if (line.startsWith("이름: ") && isMatchingBlock) {
                    currentName = line.substring("이름: ".length()).trim();
                }

                // ID와 이름 모두 찾으면 반환
                if (isMatchingBlock && currentName != null) {
                    return currentName;
                }
            }
        } catch (IOException e) {
            System.err.println("파일 읽기 오류: " + e.getMessage());
        }

        System.err.println("이름을 찾지 못함: ID=" + userId);
        return null; // 이름을 찾지 못한 경우
    }

// ID 접두사에 따른 파일 내 ID 키워드 반환
    private static String getIdPrefix(String userId) {
        if (userId.startsWith("S")) {
            return "학번: ";
        }
        if (userId.startsWith("P")) {
            return "교수번호: ";
        }
        if (userId.startsWith("H")) {
            return "학사담당자번호: ";
        }
        if (userId.startsWith("G")) {
            return "수업담당자번호: ";
        }
        return "";
    }

// ID에 따라 파일 경로 반환
    private static String determineFilePath(String userId) {
        if (userId.startsWith("S")) {
            return STUDENT_FILE_PATH;
        }
        if (userId.startsWith("P")) {
            return PROFESSOR_FILE_PATH;
        }
        if (userId.startsWith("H")) {
            return ACADEMIC_FILE_PATH;
        }
        if (userId.startsWith("G")) {
            return COURSE_MANAGER_FILE_PATH;
        }
        return null;
    }

    /**
     * 사용자 역할 판별 메서드: ID 접두사로 역할 결정.
     */
    public static String determineUserRole(String userId) {
        if (userId.startsWith("S")) {
            return "STUDENT"; // 학생
        }
        if (userId.startsWith("P")) {
            return "PROFESSOR"; // 교수
        }
        if (userId.startsWith("H")) {
            return "ADMIN_STAFF"; // 학사담당자
        }
        if (userId.startsWith("G")) {
            return "COURSE_MANAGER"; // 수업담당자
        }
        if (userId.equals("Admin")) {
            return "ADMIN"; // Admin 역할
        }
        return "UNKNOWN"; // 알 수 없는 역할
    }
}
