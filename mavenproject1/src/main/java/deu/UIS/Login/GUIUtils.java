/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.UIS.Login;

/**
 *
 * @author YangJinWon
 */
public class GUIUtils {
    public static void addEnterKeyListener(javax.swing.JTextField textField, javax.swing.JButton button) {
        textField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    button.doClick();
                }
            }
        });
    }
}

