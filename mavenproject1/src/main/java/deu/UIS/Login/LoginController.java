/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.UIS.Login;

import deu.UIS.AcademicManager.A_Main;
import deu.UIS.Admin.Admin_Main;
import deu.UIS.ClassManager.C_Main;
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
        //System.out.println("입력된 ID: " + enteredId + ", 입력된 비밀번호: " + enteredPassword);

        if (LoginService.authenticateUser(enteredId, enteredPassword)) {
            String role = LoginService.determineUserRole(enteredId);

            // 사용자 이름 가져오기
            String userName = LoginService.getUserNameForId(enteredId);

            // UserSession에 사용자 정보 저장
            UserSession session = UserSession.getInstance();
            session.setUserInfo(enteredId, userName);

            // 디버깅용 로그 (필요하면 삭제 가능)
            //System.out.println("로그인 성공: ID=" + enteredId + ", 이름=" + userName);
            // 역할별 페이지 이동
            showRoleSpecificPage(role, enteredId); // 수정된 부분
        } else {
            JOptionPane.showMessageDialog(loginView, "로그인 실패. 아이디와 비밀번호를 확인하세요.");
        }
    }

    private void showRoleSpecificPage(String role, String userId) {
        loginView.dispose();
        switch (role) {
            case "STUDENT" ->
                new S_Main().setVisible(true);
            case "PROFESSOR" ->
                new P_Main().setVisible(true);
            case "ADMIN_STAFF" ->
                new A_Main().setVisible(true);
            case "COURSE_MANAGER" ->
                new C_Main().setVisible(true);
            case "ADMIN" ->
                new Admin_Main().setVisible(true); // Admin 페이지로 이동
            default ->
                JOptionPane.showMessageDialog(loginView, "알 수 없는 사용자 역할");
        }
    }
}
