/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.UIS.Admin.Class_Manager;

import deu.UIS.Admin.*;

public class ClassManager {
    private String name;
    private String classNumber;
    private String birthDate;
    private String phone;
    private String password;

    public ClassManager(String name, String classNumber, String birthDate, String phone, String password) {
        this.name = name;
        this.classNumber = classNumber;
        this.birthDate = birthDate;
        this.phone = phone;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
