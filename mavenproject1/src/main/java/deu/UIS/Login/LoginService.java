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
        return false; // 알 수 없는 역할
    }

    public static String getUserNameForId(String userId) {
        String filePath = determineFilePath(userId); // ID에 따른 파일 경로 결정
        if (filePath == null) {
            System.err.println("잘못된 ID 형식: " + userId);
            return null; // 올바르지 않은 ID 형식
        }

        String namePrefix = "이름: ";
        String idPrefix = getIdPrefix(userId);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isIdMatched = false;
            String name = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // ID 매칭 확인
                if (line.startsWith(idPrefix) && line.contains(userId)) {
                    isIdMatched = true; // 현재 ID와 매칭
                } else if (line.startsWith(idPrefix)) {
                    isIdMatched = false; // 다른 ID가 시작되면 매칭 해제
                }

                // 이름 추출
                if (line.startsWith(namePrefix) && isIdMatched) {
                    name = line.substring(namePrefix.length()).trim();
                    return name; // 매칭된 ID와 이름 반환
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
        return "UNKNOWN"; // 알 수 없는 역할
    }
}
