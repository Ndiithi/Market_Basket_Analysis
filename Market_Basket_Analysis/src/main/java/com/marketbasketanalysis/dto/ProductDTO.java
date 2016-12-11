/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marketbasketanalysis.dto;

import com.marketbasketanalysis.dao.Category;
import com.marketbasketanalysis.dao.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Duncan
 */
public class ProductDTO {

    private DataSource ds;
    private Connection con;
    private Context ctx;

    public ProductDTO() {
        try {
            ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("basketanalysistest");

        } catch (NamingException ex) {
            Logger.getLogger(ProductDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean addProduct(Product product) {
        boolean isAddProductSuccess = false;
        try {

            con = (Connection) ds.getConnection();
            String insertProductToTable = "insert into product(name,price,category,desc) values(?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(insertProductToTable);
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getCategory().getCategoryId());
            ps.setString(4, product.getDescripton());
            isAddProductSuccess = true;

        } catch (SQLException ex) {
            Logger.getLogger(ProductDTO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductDTO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return isAddProductSuccess;
    }

    public boolean deleteProductByObect(Product product) {
        boolean isDeleteProductSuccess;
        int id = product.getProductId();
        isDeleteProductSuccess = deleteProduct(id);
        return isDeleteProductSuccess;
    }

    public boolean deleteProductById(int id) {
        boolean isDeleteProductSuccess;
        isDeleteProductSuccess = deleteProduct(id);
        return isDeleteProductSuccess;

    }

    private boolean deleteProduct(int id) {
        boolean isDeleteProductSuccess = false;
        String deleteProductQuery = "Delete from product where product_id=?";
        try {
            con = (Connection) ds.getConnection();
            PreparedStatement ps = con.prepareStatement(deleteProductQuery);
            ps.setInt(1, id);
            isDeleteProductSuccess = ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDTO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductDTO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return isDeleteProductSuccess;
    }

    public boolean updateProduct(Product product) {
        boolean isUpdateProductSuccess = false;
        try {
            con = (Connection) ds.getConnection();
            String updateProductQuery = "update product set "
                    + "name=?,"
                    + "category=?,"
                    + "description=?"
                    + "price=? where product_id=?";
            PreparedStatement ps = con.prepareStatement(updateProductQuery);
            ps.setString(1, product.getName());
            ps.setInt(2, product.getCategory().getCategoryId());
            ps.setString(3, product.getDescripton());
            ps.setDouble(4, product.getPrice());

            isUpdateProductSuccess = ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDTO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductDTO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return isUpdateProductSuccess;
    }

    public List<Product> getAllProducts() {
        String selectAllProducts = "Select * from product";
        List<Product> productList = null;
        try {
            con = (Connection) ds.getConnection();
            productList = new ArrayList();
            PreparedStatement ps = con.prepareStatement(selectAllProducts);
            ResultSet rs = ps.executeQuery();
            Product prod;
            while (rs.next()) {
                prod = new Product();
                Category cat = new Category();
                prod.setDescripton(rs.getString("description"));
                prod.setName(rs.getString("name"));
                prod.setProductId(rs.getInt("product_id"));
                prod.setPrice(rs.getInt("price"));
                int catId = rs.getInt("category");
                System.out.println("The is is :" + catId);
                String getCatIdQuery = "select * from category where category_id='" + catId + "'";
                PreparedStatement psForCat = con.prepareStatement(getCatIdQuery);
                ResultSet rsForCat = psForCat.executeQuery();
                if (rsForCat.next()) {
                    cat.setCategoryId(rsForCat.getInt("category_id"));
                    cat.setDescription("description");
                    cat.setName("name");
                }
                prod.setCategory(cat);
                productList.add(prod);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDTO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductDTO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return productList;
    }
}
