package com.example.user.controller;


import com.example.user.model.User;
import org.springframework.stereotype.Controller;

import java.sql.*;
import java.util.ArrayList;

//This class connects to database
//We are using H2 database

@Controller
public class UserDBController {

    private static UserDBController instance;
    private Connection conn;
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test";
    static final String USER = "sa";
    static final String PASS = "";

    public static UserDBController getInstance() throws SQLException, ClassNotFoundException {
        return instance==null?instance=new UserDBController():instance;
    }

    private UserDBController() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER,PASS);
        Statement stmt = null;

        try{
            stmt = conn.createStatement();
            String sql =  "CREATE TABLE   User " +
                    "(id INTEGER not NULL, " +
                    " first VARCHAR(255), " +
                    " last VARCHAR(255), " +
                    " age INTEGER, " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                conn.close();
                if (stmt != null) {
                    stmt.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList<User> getUsers(){
        Statement st = null;
        ArrayList<User> allUsers = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            st = conn.createStatement();

            String sql = "SELECT * FROM User";
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                int id  = rs.getInt("id");
                int age = rs.getInt("age");
                String first = rs.getString("first");
                String last = rs.getString("last");
                allUsers.add(new User(id,age,first,last));
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                conn.close();
                if (st != null) {
                    st.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return allUsers;
    }

    public User getUser(Integer userID){
        Statement st = null;
        User user = null;
        try{
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            st = conn.createStatement();

            String sql = "SELECT * FROM User where id='"+userID+"'";
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                int id  = rs.getInt("id");
                int age = rs.getInt("age");
                String first = rs.getString("first");
                String last = rs.getString("last");
                user = new User(id,age,first,last);
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                conn.close();
                if (st != null) {
                    st.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return user;
    }

    public User updateUser(User user){
        Integer userID = user.getId();
        User userFromDB = getUser(userID);
        if(userFromDB == null || user == null){
            return null;
        }
        Statement st = null;
        try{
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            st = conn.createStatement();

            String sql = "Update User SET age='"+user.getAge()+"', first='"+user.getFirstName()+"', last='"+user.getLastName()+"' where id='"+userID+"'";
            int result = st.executeUpdate(sql);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                conn.close();
                if (st != null) {
                    st.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return getUser(userID);
    }

    public User createUser(User user){
        if(user == null || user.getId()==null){
            return null;
        }
        User userFromDB = getUser(user.getId());
        if(userFromDB !=null){
            //We can update user here
            //return updateUser(user.getId(),user);
            return null;
        }

        Statement st = null;
        try{
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            st = conn.createStatement();
            String sql = "Insert into User values ( '"+user.getId()+"', '"+user.getFirstName()+"', '"+user.getLastName()+"', '"+user.getAge()+"' )";
            int result = st.executeUpdate(sql);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                conn.close();
                if (st != null) {
                    st.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return getUser(user.getId());
    }

    public boolean removeUser(Integer userID){

        if(userID == null){
            return false;
        }

        User userFromDB = getUser(userID);
        if(userFromDB ==null){
            return false;
        }

        Statement st = null;
        try{
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            st = conn.createStatement();
            String sql = "Delete from User where id='"+userID+"'";
            int result = st.executeUpdate(sql);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            try {
                conn.close();
                if (st != null) {
                    st.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return true;
    }
}