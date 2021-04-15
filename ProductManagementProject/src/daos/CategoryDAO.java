/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import database.MyConnection;
import dtos.CategoryDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author cutay
 */
public class CategoryDAO{
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public CategoryDAO() {
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
    
    public ArrayList<CategoryDTO> getAllCategory() throws Exception{
        ArrayList<CategoryDTO> result = null;
        CategoryDTO dto;
        String categoryID, categoryName, description;
        String sql = "Select categoryID, categoryName, description From TblCategories";
        
        try {
            Connection con = MyConnection.getMyConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            result = new ArrayList<>();
            
            while(rs.next()){
                categoryID = rs.getString("categoryID");
                categoryName = rs.getString("categoryName");
                description = rs.getString("description");
                dto = new CategoryDTO(categoryID, categoryName, description);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public String CheckSameID(String categoryID) throws Exception{
        String result = null;
        String sql = "Select categoryID From TblCategories Where categoryID = ?";
        try {
            con = MyConnection.getMyConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, categoryID);
            rs = ps.executeQuery();
            
            while(rs.next()){
                categoryID = rs.getString("categoryID");
                result = categoryID;
            }
        } 
        catch (SQLException e){
            e.printStackTrace();
        }
        finally{
            closeConnection();
        }
        return result;
    }
    
    public ArrayList<String> ChoiceCategory(ArrayList<CategoryDTO> data) throws Exception{
        ArrayList<String> list = new ArrayList<>();
        for (CategoryDTO string : data) {
            list.add(string.getCategoryID()+ "-" + string.getCategoryName());
        }
        return list;
    }
     
    public int insertCategory (CategoryDTO cate) throws Exception{
        String sql = "Insert TblCategories Values(?,?,?)";
        try{
            con = MyConnection.getMyConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cate.getCategoryID());
            ps.setString(2, cate.getCategoryName());
            ps.setString(3, cate.getDescription());
            return ps.executeUpdate();
        }
        finally{
            closeConnection();
        }
    }
    
    public int updateCategory (CategoryDTO cate) throws Exception{
        String sql = "Update TblCategories Set categoryName = ?, description = ? Where categoryID = ?";
        try{
            con = MyConnection.getMyConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cate.getCategoryName());
            ps.setString(2, cate.getDescription());
            ps.setString(3, cate.getCategoryID());
            return ps.executeUpdate();
        }
        finally{
            closeConnection();
        }
    }
    
    public String checkSameIDWithProduct(String code) throws Exception{
        String result = null;
        String productName;
        String sql = "Select productName From TblProducts join TblCategories on TblCategories.categoryID = TblProducts.categoryid Where TblCategories.categoryID = ?";
        try {
            con = MyConnection.getMyConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, code);
            rs = ps.executeQuery();
            
            while(rs.next()){
                productName = rs.getString("productName");
                result = productName;
            }
        } 
        finally{
            closeConnection();
        }
        return result;
    }
    
    public int deleteCategory(String code) throws Exception{
        String sql = "Delete From TblCategories Where categoryID = ?";
        try{
            con = MyConnection.getMyConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, code);
            return ps.executeUpdate();
        }
        finally{
            closeConnection();
        }
    }
}
