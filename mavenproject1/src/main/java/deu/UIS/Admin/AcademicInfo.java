/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.UIS.Admin;

/**
 *
 * @author YangJinWon
 */
public class AcademicInfo {
    private String name;
    private String academicNumber;
    private String birthDate;
    private String phone;

    public AcademicInfo(String name, String academicNumber, String birthDate, String phone) {
        this.name = name;
        this.academicNumber = academicNumber;
        this.birthDate = birthDate;
        this.phone = phone;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public String getAcademicNumber() {
        return academicNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getPhone() {
        return phone;
    }
}
