package stu.utils;

import java.sql.*;

public class DBUtils {

    //获得数据库连接
    public static Connection getConnection() {

        Connection con = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_stu","root", "123456");

        } catch (ClassNotFoundException e) {
            System.out.println("驱动异常：" + e.getMessage());
        } catch (SQLException e) {
            System.out.println("拿连接异常：" + e.getMessage());
        }
        return con;
    }

    //仅测试用
	
	  public static void main(String[] args) {
	  
	  System.out.println(getConnection()); }
	

    //释放数据库资源，适用于添、删、改
    public static void release(Statement stmt, Connection connection){
        try{
            if(stmt!=null) stmt.close();
            if(connection!=null) connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //释放数据库资源，适用于查询
    public static void release(ResultSet rs, Statement stmt, Connection connection){
        try{
            if(rs!=null) rs.close();
            release(stmt, connection);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}

