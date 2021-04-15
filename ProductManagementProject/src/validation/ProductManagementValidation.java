/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validation;

import javax.swing.JOptionPane;

/**
 *
 * @author cutay
 */
public class ProductManagementValidation {
    
    public boolean categoryIDValidation(String categoryID){
        if (categoryID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill in blank please");
            return false;
        }
                
        if (!categoryID.matches("CA\\d{3}")) {
            JOptionPane.showMessageDialog(null, "please type correct id: CA***");
            return false;
        }
        return true;
    }
    
    public boolean categoryNameValidation(String categoryName) {
        if (categoryName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill in blank please");
            return false;
        }
        if (!categoryName.matches("[a-zA-Z\\s]{1,50}")) {
            JOptionPane.showMessageDialog(null, "please type correct name");
            return false;
        }
        return true;
    }
    
    public boolean descriptionValidation(String description){
        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill in blank please");
            return false;
        }
        else if (!description.matches("[a-zA-Z\\s]{1,200}")) {
            JOptionPane.showMessageDialog(null, "please type correct Description");
            return false;
        }
        return true;
    }
    
    public boolean productIDValidation(String productID){
        if (productID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill in blank please");
            return false;
        }
        if (!productID.matches("PD\\d{3}")) {
            JOptionPane.showMessageDialog(null, "please type correct id: PD*** ");
            return false;
        }
        return true;
    }
    
    public boolean productNameValidation(String productName){
        if (productName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill in blank please");
            return false;
        }
        if (!productName.matches("[a-zA-Z0-9\\s]{1,50}")) {
            JOptionPane.showMessageDialog(null, "please type correct name");
            return false;
        }
        return true;
    }
    
    public boolean unitValidation(String unit){
        if (unit.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill in blank please");
            return false;
        }
        if (!unit.matches("[a-zA-Z\\s]{1,50}")) {
            JOptionPane.showMessageDialog(null, "please type correct Unit");
            return false;
        }
        return true;
    }
    
    public boolean quantityValidation(String quantity){
        if (quantity.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill in blank please");
            return false;
        }
        if (!quantity.matches("\\d{1,50}")) {
            JOptionPane.showMessageDialog(null, "please type correct Quantity");
            return false;
        }
        return true;
    }
    
    public boolean priceValidation(String price){
        if (price.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill in blank please");
            return false;
        }
        if (price.matches("\\d{1,50}")) {
            return true;
        }
        if (!price.matches("\\d{1,20}.\\d{1,50}")) {
            JOptionPane.showMessageDialog(null, "please type correct Price");
            return false;
        }
        return true;
    }
    
    public boolean usernameAndPasswordValidation(String username, String password){
        if (username.isEmpty() && password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "please type User and Password");
            return false;
        }
        return true;
    }
    
    public boolean usernameValidation(String username){
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill in blank please");
            return false;
        }
        
        if (!username.matches("[a-zA-Z0-9\\s]{1,10}")) {
            JOptionPane.showMessageDialog(null, "please type correct username");
            return false;
        }
        return true;
    }
    
    public boolean passwordValidation(String password){
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill in blank please");
            return false;
        }
        
        if (!password.matches("[a-zA-Z0-9\\s]{1,50}")) {
            JOptionPane.showMessageDialog(null, "please type correct password");
            return false;
        }
        return true;
    }
}
