/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import database.MyConnection;
import dtos.LoginDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


/**
 *
 * @author cutay
 */
public class LoginDAO{
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public LoginDAO() {
    }
    
    private void closeConnection() throws Exception{
        
        if (rs != null) {
            rs.close();
        }
        
        if (ps != null) {
            ps.close();
        }
        
        if (con != null) {
            con.close();
        }
    }
    
    public String getFullname (String userName) throws Exception {
        String fullname = null;
        String sql = "Select fullName From TblUsers Where userID = ?";
        try{
            con = MyConnection.getMyConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, userName);
            rs = ps.executeQuery();
            if (rs.next()) {
                fullname = rs.getString("fullName");
            }
        }
        finally{
            closeConnection();
        }
        return fullname;
    }
    
    public ArrayList<LoginDTO> getAllLogin() throws Exception{
        ArrayList<LoginDTO> result = null;
        LoginDTO dto;
        String userID, fullname, password;
        String sql = "Select userID, password, fullName From TblUsers Where status = 'True'";
        
        try {
            Connection con = MyConnection.getMyConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            result = new ArrayList<>();
            
            while(rs.next()){
                userID = rs.getString("userID");
                password = rs.getString("password");
                fullname = rs.getString("fullName");
                dto = new LoginDTO(userID, fullname, password);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
