/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.marketbasketanalysis.dto;

import com.marketbasketanalysis.dao.Category;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Duncan
 */
public class CategoryDTO {
     private DataSource ds;
    private Connection con;
    private Context ctx;
    
    public CategoryDTO(){
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/basketanalysis");
            con=(Connection) ds.getConnection();
        } catch (NamingException ex) {
            Logger.getLogger(CategoryDTO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public boolean addCategory(Category category) {
        boolean isAddCategorySuccess=false;
        try {
            
            
            
            String insertCategoryToTable="insert into category(name,description) values(?,?)";
            
            PreparedStatement ps=con.prepareStatement(insertCategoryToTable);
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            
            isAddCategorySuccess=true;
            
           
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
         return isAddCategorySuccess;
    }
    
    public boolean deleteCategory(Category category){
        boolean isDeleteCategorySuccess=false;
        String deleteCategoryQuery="Delete from category where categoryId=?";
        try {
            PreparedStatement ps=con.prepareStatement(deleteCategoryQuery);
            ps.setInt(1, category.getCategoryId());
            isDeleteCategorySuccess=ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return isDeleteCategorySuccess;
    }
    
    public boolean updateCategory(Category category){
        boolean isUpdateCategorySuccess=false;
        try {
            
            String updateCategoryQuery="update category set "
                    + "name=?,"
                   
                    + "description=?"
                    +"where category_id=?";
            PreparedStatement ps=con.prepareStatement(updateCategoryQuery);
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setInt(3, category.getCategoryId());
            
           
            isUpdateCategorySuccess=ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
          return isUpdateCategorySuccess;
    }
}
