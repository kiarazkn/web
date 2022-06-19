package stu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import stu.utils.DBUtils;
import stu.entity.User;

public class UserDAO {

    //根据登录用户名查找用户
    public User getUserByName(String name) { 
        /*
        	连DB
        	SQL语句：根据name查User记录
        	执行得结果
        	关闭DB资源
        */
        User user=null;
        Connection connection = DBUtils.getConnection();
        String sql="SELECT * FROM t_user where name=?";  //1,2,....
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = connection.prepareStatement(sql);
            //给？赋值
            pstmt.setString(1,name);
            rs = pstmt.executeQuery();
            if (rs.next()) {  //查到了

                user = new User();
                user.setId(rs.getInt("id"));
                user.setRole(rs.getString("role"));
                user.setName(name);
                user.setPwd(rs.getString("pwd"));

            }

        }catch (SQLException e) {
            System.out.println("根据用户名查找账户异常：" + e.getMessage());
            e.printStackTrace();

        }finally {
            DBUtils.release(rs,pstmt,connection);
        }

        return user;
    }

}
