/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.UIS.Login;

/**
 *
 * @author YangJinWon
 */
public class UserSession {
    private static UserSession instance; // 싱글톤 인스턴스
    private String userId; // 사용자 ID
    private String userName; // 사용자 이름

    private UserSession() {} // private 생성자

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setUserInfo(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void clearSession() {
        userId = null;
        userName = null;
    }
}
