/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.UIS.Login;

import deu.UIS.AcademicManager.A_Main;
import deu.UIS.Admin.Admin_Main;
import deu.UIS.ClassManager.Class_Management;
import deu.UIS.Professor.P_Main;
import deu.UIS.Student.S_Main;
import javax.swing.JOptionPane;

/**
 *
 * @author YangJinWon
 */
public class LoginController {

    private final Login loginView;

    public LoginController(Login loginView) {
        this.loginView = loginView;
    }

    public void handleLogin(String enteredId, String enteredPassword) {
        if (LoginService.authenticateUser(enteredId, enteredPassword)) {
            String role = LoginService.determineUserRole(enteredId);
            showRoleSpecificPage(role);
        } else {
            JOptionPane.showMessageDialog(loginView, "로그인 실패. 아이디와 비밀번호를 확인하세요.");
        }
    }

    private void showRoleSpecificPage(String role) {
        loginView.dispose();
        switch (role) {
            case "STUDENT" ->
                new S_Main().setVisible(true);
            case "PROFESSOR" ->
                new P_Main().setVisible(true);
            case "ADMIN_STAFF" ->
                new A_Main().setVisible(true);
            case "COURSE_MANAGER" ->
                new Class_Management().setVisible(true);
            case "ADMIN" ->
                new Admin_Main().setVisible(true);
            default ->
                JOptionPane.showMessageDialog(loginView, "알 수 없는 사용자 역할");
        }
    }
}
