/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.UIS.AcademicManager;

/**
 *
 * @author YangJinWon
 */
public class P_ValidationUtils {
    public static void validateInputFields(String name, String department, String phone, String birthDate) {
        if (name.isEmpty() || department.isEmpty() || phone.isEmpty() || birthDate.isEmpty()) {
            throw new IllegalArgumentException("모든 필드를 입력해주세요.");
        }
        if (!phone.matches("\\d{3}-\\d{3,4}-\\d{4}")) {
            throw new IllegalArgumentException("전화번호 형식이 잘못되었습니다. (예: 010-1234-5678)");
        }
        if (!birthDate.matches("\\d{6}-\\d{7}")) {
            throw new IllegalArgumentException("생년월일 형식이 잘못되었습니다. (예: 901010-1234567)");
        }
    }
}
