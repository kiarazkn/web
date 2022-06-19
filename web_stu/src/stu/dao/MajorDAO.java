package stu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import stu.entity.Major;
import stu.utils.DBUtils;


public class MajorDAO {
		//查询所有专业
		public List<Major> getAll() throws Exception{
			
			List<Major> majors = new ArrayList<Major>();
			Connection con = DBUtils.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try {
				ps=con.prepareStatement("select * from t_major");
				rs=ps.executeQuery();
				
				while (rs.next()) {
					Major major=new Major();
					major.setId(rs.getInt("id"));
					major.setName(rs.getString("name"));
					
					majors.add(major);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("数据库访问出现异常：" + e);
			} finally {
				DBUtils.release(rs, ps, con);
			}
			return majors;
		}

		//查询专业
		public List<Major> search(String majorName) throws Exception{
			List<Major> majors = new ArrayList<Major>();
			
			Connection con = DBUtils.getConnection();
			
			PreparedStatement ps = null;
			
			ResultSet rs = null;
			
			try {
				//第一步：根据学生姓名、专业名称模糊查询t_major表找出对应的专业，主要封装专业的基本信息：id，name
				ps = (PreparedStatement) con.prepareStatement("SELECT DISTINCT t_major.*" + 
						"  FROM t_major WHERE t_major.name LIKE  ?");
				
				ps.setString(1, "%"+majorName+"%");
				
				rs=ps.executeQuery();
				
				while(rs.next()){
					Major major=new Major();
					major.setId(rs.getInt("id"));
					major.setName(rs.getString("name"));
					majors.add(major);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("数据库访问出现异常:" + e);
			} finally {
				DBUtils.release(rs,ps,con);
			}
			
			return majors;
		}

		//添加专业
		public void save(Major major) throws Exception {
			Connection con = DBUtils.getConnection();
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement("INSERT INTO t_major VALUES(null,?)");
				ps.setString(1, major.getName());
				ps.executeUpdate(); // 执行insert语句
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("数据库访问出现异常:" + e);
			} finally {
				DBUtils.release(ps, con);
			}
		}
		
		// 专业名称是否已经存在
		public boolean isExist(String name) throws Exception {
			boolean flag = false;

			Connection con = DBUtils.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				ps = con.prepareStatement("SELECT * from t_major WHERE name=?");
				ps.setString(1, name);
				rs = ps.executeQuery();

				if (rs.next()) {
					flag = true; // 专业名称已存在
				}

				} catch (SQLException e) {
					e.printStackTrace();
					throw new Exception("数据库访问出现异常:" + e);
				} finally {
					DBUtils.release(ps, con);
				}
				return flag;
		}

		//删除专业
		public void delete(int majorId) throws Exception{
			Connection con = DBUtils.getConnection();
			PreparedStatement ps = null;
			
			try {
				con.setAutoCommit(false);  //开启事务
				
				String sql="DELETE FROM t_stu_major WHERE majorId=" + majorId;
				//先删除宠物关联着的病例
				ps = con.prepareStatement(sql);
				ps.executeUpdate(); //执行delete操作
				
				sql="DELETE FROM t_major WHERE id=" + majorId;
				//再删除宠物
				ps.executeUpdate(sql);
				
				con.commit();  //提交事务
			} catch (Exception e) {
				e.printStackTrace();
				
				if(con!=null) {
					con.rollback();   //遇异常，回滚事务
				}
				throw new Exception("数据库访问出现异常：" + e);
			}finally {
				DBUtils.release(ps, con);
			}
		}


		//根据专业id查询专业
		public Major getById(int MajorId) throws Exception{
			Major major = null;
	    	
	    	Connection con = DBUtils.getConnection();
	    	PreparedStatement ps = null;
	    	ResultSet rs = null;
	    	
	    	try {
				ps = con.prepareStatement("SELECT * FROM t_major WHERE id=?");
				
				ps.setInt(1, MajorId);
				
				rs = ps.executeQuery();
				
				if (rs.next()) {
					major = new Major();
					major.setId(rs.getInt("id"));
					major.setName(rs.getString("name"));
					
				}
	    	} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("数据库访问出现异常:" + e);
			}finally {
				DBUtils.release(rs, ps, con);
			}
	    	return major;
		}

		public List<Major> getMajorsByStuId(int stuId) throws Exception{
			List<Major> majors = new ArrayList<Major>();
			Connection con = DBUtils.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				ps = con.prepareStatement("select t_major.*"
						+ " from t_major join t_stu_major on t_major.id=t_stu_major.majorId"
						+ " where t_stu_major.stuId=" + stuId);
				// 根据医生id查其专业

				rs = ps.executeQuery();
				while (rs.next()) {
					Major major = new Major();
					major.setId(rs.getInt("id"));
					major.setName(rs.getString("name"));
					majors.add(major);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("数据库访问出现异常：" + e);
			} finally {
				DBUtils.release(rs, ps, con);
			}
			return majors;
		}

		public void modify(Major major) throws Exception{
			Connection con = DBUtils.getConnection();
	    	PreparedStatement ps = null;
	    	
	    	try {
				ps=con.prepareStatement("update t_major set name=? where id=?");
				
				ps.setString(1, major.getName());
				ps.setInt(2, major.getId());
				
				ps.executeUpdate();//执行update语句
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("数据库访问出现异常："+e);
			}finally {
				DBUtils.release(ps, con);
			}
		}
}
