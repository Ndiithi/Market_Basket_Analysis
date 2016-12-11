/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.marketbasketanalysis.dto;


import com.marketbasketanalysis.dao.User;
import com.mysql.jdbc.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
/**
 *
 * @author Duncan
 */
public class UserDTO {
    private DataSource ds;
    private Connection con;
    private Context ctx;
    public UserDTO(){
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/basketanalysis");
            con=(Connection) ds.getConnection();
        } catch (NamingException ex) {
            Logger.getLogger(UserDTO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public boolean addUser(User user) {
        boolean isAddUserSuccess=false;
        try {
            
            
            
            String insertUserToTable="insert into user(first_name,second_name,email,) values(?,?,?,?)";
            
            PreparedStatement ps=con.prepareStatement(insertUserToTable);
            ps.setString(1, user.getFirst_name());
            ps.setString(2, user.getSecond_name());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getRole().name());
            isAddUserSuccess=true;
            
           
        } catch (SQLException ex) {
            Logger.getLogger(UserDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
         return isAddUserSuccess;
    }
    
    public boolean deleteUser(User user){
        boolean isDeleteUserSuccess=false;
        String deleteUserQuery="Delete from user where userId=?";
        try {
            PreparedStatement ps=con.prepareStatement(deleteUserQuery);
            ps.setInt(1, user.getUserId());
            isDeleteUserSuccess=ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return isDeleteUserSuccess;
    }
    
    public boolean updateUser(User user){
        boolean isUpdateUserSuccess=false;
        try {
            
            String updateUserQuery="update user set "
                    + "first_name=?,"
                    + "second_name=?,"
                    + "email=?"
                    + "role=? where user_id=?";
            PreparedStatement ps=con.prepareStatement(updateUserQuery);
            ps.setString(1, user.getFirst_name());
            ps.setString(2, user.getSecond_name());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getRole().name());
            isUpdateUserSuccess=ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
          return isUpdateUserSuccess;
    }
}
