/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.UIS.Admin.Academic_Manager;

/**
 *
 * @author YangJinWon
 */
public class AcademicInfo {
    private String name;
    private String academicNumber;
    private String birthDate;
    private String phone;
    private String password; // 추가된 필드

    public AcademicInfo(String name, String academicNumber, String birthDate, String phone) {
        this.name = name;
        this.academicNumber = academicNumber;
        this.birthDate = birthDate;
        this.phone = phone;
        this.password = generatePassword(birthDate); // 비밀번호 자동 생성
    }

    // 비밀번호 생성 로직
    private String generatePassword(String birthDate) {
        if (birthDate.contains("-") && birthDate.split("-").length > 1) {
            return birthDate.split("-")[1]; // 생년월일 뒷자리 7자리
        }
        return "0000000"; // 기본값
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getAcademicNumber() {
        return academicNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        this.password = generatePassword(birthDate); // 생년월일 변경 시 비밀번호 갱신
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
