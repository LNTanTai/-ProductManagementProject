/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import database.MyConnection;
import java.sql.Connection;
import dtos.ProductDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author cutay
 */
public class ProductDAO{
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public ProductDAO() {
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
    
    public ArrayList<ProductDTO> getAllProduct() throws Exception{
        ArrayList<ProductDTO> result = null;
        String productID, productName, unit, categoryid;
        float price;
        int quantity;
        ProductDTO dto;
        String sql = "Select productID, productName, unit, price, quantity, categoryid From TblProducts";
        try {
            con = MyConnection.getMyConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            result = new ArrayList<>();
            
            while(rs.next()){
                productID = rs.getString("productID");
                productName = rs.getString("productName");
                unit = rs.getString("unit");
                price = rs.getFloat("price");
                quantity = rs.getInt("quantity");
                categoryid = rs.getString("categoryid");
                dto = new ProductDTO(productID, productName, unit, categoryid, quantity, price);
                result.add(dto);
            }
        } 
        finally{
            closeConnection();
        }
        return result;
    }
    
    public String CheckSameID(String productID) throws Exception{
        String result = null;
        String sql = "Select productID From TblProducts Where productID = ?";
        try {
            con = MyConnection.getMyConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, productID);
            rs = ps.executeQuery();
            
            while(rs.next()){
                productID = rs.getString("productID");
                result = productID;
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
    
    public int insertProduct (ProductDTO pro) throws Exception{
        String sql = "Insert TblProducts Values(?,?,?,?,?,?)";
        try{
            con = MyConnection.getMyConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getProductID());
            ps.setString(2, pro.getProductName());
            ps.setString(3, pro.getUnit());
            ps.setFloat(4, pro.getPrice());
            ps.setInt(5, pro.getQuantity());
            ps.setString(6, pro.getCategoryid());
            return ps.executeUpdate();
        }
        finally{
            closeConnection();
        }
    }
    
    public int updateProduct (ProductDTO pro) throws Exception{
        String sql = "Update TblProducts Set categoryid = ?, productName = ?, unit = ?, price = ?, quantity = ? Where productID = ?";
        try{
            con = MyConnection.getMyConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getCategoryid());
            ps.setString(2, pro.getProductName());
            ps.setString(3, pro.getUnit());
            ps.setFloat(4, pro.getPrice());
            ps.setInt(5, pro.getQuantity());
            ps.setString(6, pro.getProductID());
            return ps.executeUpdate();
        }
        finally{
            closeConnection();
        }
    }
    
    public int deleteProduct(String code) throws Exception{
        String sql = "Delete From TblProducts Where productID = ?";
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
