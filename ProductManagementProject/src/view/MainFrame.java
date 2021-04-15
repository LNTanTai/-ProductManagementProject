/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author cutay
 */
public class MainFrame {
    public static void main(String[] args) {
        new LoginTable().setVisible(true);
    }
    
    public static boolean isLoggedIn = false;
}
