package ph.dao;

import ph.entity.Pet;
import ph.entity.User;
import ph.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    //根据客户姓名关键字查询客户
    public List<User> searchCustomer(String customerName) throws Exception {
    	
    	List<User> customers = new ArrayList<User>();
    	
    	Connection con = DBUtils.getConnection();
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	
    	try {
    		
			ps = con.prepareStatement("SELECT * FROM t_user WHERE name LIKE ? AND role='customer'");
			//t_user表中，role值为customer代表客户(宠物主人)
			ps.setString(1, "%" + customerName + "%");
			rs = ps.executeQuery();
			
			while (rs.next()) {
				User user = new User();
				
				user.setId(rs.getInt("id"));
				user.setRole(rs.getString("role"));
				user.setName(rs.getString("name"));
				user.setPwd(rs.getString("pwd"));
				user.setTel(rs.getString("tel"));
				user.setAddress(rs.getString("address"));
				
				customers.add(user);
			}
    	} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常:" + e);
		}finally {
			DBUtils.release(rs, ps, con);
		}
    	return customers;
    }
    
    //根据客户id查询客户
    public User getById(int id) throws Exception {
    	
    	User customer = null;
    	
    	Connection con = DBUtils.getConnection();
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	
    	try {
			ps = con.prepareStatement("SELECT * FROM t_user WHERE id=?");
			
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				customer = new User();
				customer.setId(rs.getInt("id"));
				customer.setRole(rs.getString("role"));
				customer.setName(rs.getString("name"));
				customer.setPwd(rs.getString("pwd"));
				customer.setTel(rs.getString("tel"));
				customer.setAddress(rs.getString("address"));
			}
    	} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常:" + e);
		}finally {
			DBUtils.release(rs, ps, con);
		}
    	return customer;
    }

    //添加新客户
    public void save(User customer) throws Exception {
    	
    	Connection con = DBUtils.getConnection();
    	PreparedStatement ps = null;
    	
    	try {
    		
    		ps = con.prepareStatement("INSERT INTO t_user VALUES(null,?,?,?,?,?)");
    		//id由数据库自动生成
    		
    		ps.setString(1, customer.getRole());
    		ps.setString(2, customer.getName());
    		ps.setString(3, customer.getPwd());
    		ps.setString(4, customer.getTel());
    		ps.setString(5, customer.getAddress());
    		
    		ps.executeUpdate();  //执行insert语句
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常:" + e);
		} finally {
			DBUtils.release(ps, con);
		}
    }

    //更新客户信息
    public void modify(User customer) throws Exception{
    	Connection con = DBUtils.getConnection();
    	PreparedStatement ps = null;
    	
    	try {
			ps=con.prepareStatement("update t_user set tel=?,address=? where id=?");
			
			ps.setString(1, customer.getTel());
			ps.setString(2, customer.getAddress());
			ps.setInt(3, customer.getId());
			
			ps.executeUpdate();//执行update语句
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常："+e);
		}finally {
			DBUtils.release(ps, con);
		}
    }

    //删除客户（级联删除其宠物）
    public void delete(int cid) throws Exception{
    	Connection con = DBUtils.getConnection();
    	PreparedStatement ps = null;
    	
    	try {
			con.setAutoCommit(false);//开启事务
			
			//查客户的所有宠物，删除其宠物
			List<Pet> pets = new PetDAO().getPetsByOwnerId(cid);
			
			if (pets.size()>0) {//有宠物时，先删宠物
				for(Pet pet:pets) {
					String sql="delete from t_visit where petId="+pet.getId();
					ps=con.prepareStatement(sql);
					ps.executeUpdate();//删宠物病例
					
					sql="delete from t_pet where id="+pet.getId();
					ps.executeUpdate(sql);//删宠物
				}
				ps.close();
			}
			ps=con.prepareStatement("delete from t_user where id="+cid);
			ps.executeUpdate();//删客户
			
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (con!=null) {
				con.rollback();//遇异常，回滚事务
			}
			throw new Exception("数据库访问出现异常："+e);
		}finally {
			DBUtils.release(ps, con);
		}
    }
}
