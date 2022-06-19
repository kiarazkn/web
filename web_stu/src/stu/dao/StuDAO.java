package stu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import stu.entity.Major;
import stu.entity.Stu;
import stu.utils.DBUtils;

public class StuDAO {

	// 查询学生
	public List<Stu> search(String stuName, String majorName) throws Exception {
		List<Stu> stus = new ArrayList<Stu>();

		Connection con = DBUtils.getConnection();

		PreparedStatement ps = null;

		ResultSet rs = null;

		try {

			// 第一步：根据医生姓名、专业名称模糊查询t_vet表找出对应的医生，主要封装医生的基本信息：id，name

			ps = (PreparedStatement) con.prepareStatement(
					"SELECT DISTINCT t_stu.*" + "  FROM t_stu JOIN t_stu_major ON t_stu.id=t_stu_major.stuId"
							+ "  JOIN t_major ON t_stu_major.majorId=t_major.id"
							+ "  WHERE t_stu.name LIKE  ?  AND t_major.name LIKE  ?");

			ps.setString(1, "%" + stuName + "%");
			ps.setString(2, "%" + majorName + "%");

			rs = ps.executeQuery();

			while (rs.next()) {
				Stu s = new Stu();
				s.setId(rs.getInt("id"));
				s.setName(rs.getString("name"));
				s.setGender(rs.getString("gender"));
				s.setBirthday(rs.getString("birthday"));
				s.setTel(rs.getString("tel"));
				stus.add(s);
			}

			// 第二步：再针对之前查到的所有的医生，查每个医生的所有专业信息，封装到该医生对象的specs属性上
			for (Stu s : stus) {

				ps = (PreparedStatement) con.prepareStatement(
						"SELECT t_major.*" + " FROM t_stu JOIN t_stu_major ON t_stu.id=t_stu_major.stuId"
								+ " JOIN t_major ON t_stu_major.majorId=t_major.id" + " WHERE t_stu.id=?");

				ps.setInt(1, s.getId()); // 1,2,3

				rs = ps.executeQuery();

				while (rs.next()) {
					Major major = new Major();
					major.setId(rs.getInt("id"));
					major.setName(rs.getString("name"));
					s.getMajors().add(major); // 将查到的医生专业信息添加到医生对象的specs属性上
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常:" + e);
		} finally {
			DBUtils.release(rs, ps, con);
		}

		return stus;
	}

	public void save(Stu stu) throws Exception {
		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con.setAutoCommit(false);
			String sql = "insert into t_stu VALUES(null,?,?,?,?)";
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, stu.getName());
			ps.setString(2, stu.getGender());
			ps.setString(3, stu.getBirthday());
			ps.setString(4, stu.getTel());

			ps.executeUpdate();// 执行update语句
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				stu.setId(rs.getInt(1));
				// 将生成的医生id封装到医生对象vet上，之后t_vet_speciality添加医生与专业的关系时需要的
			}

			sql = "insert into t_stu_major VALUES";
			// 往t_vet_speciality表添加记录，拼接SQL语句，注意一个医生可以具备多个专业
			// VALUES后面的SQL语句格式： (vetId,specId),(vetId,specId),(vetId,specId)...

			boolean first = true; // first:表示是否是VALUES后的第一条记录

			for (Major major : stu.getMajors()) {
				// 遍历vet的所有专业，拼接SQL语句串

				if (first) { // 是第一条

					sql += "(" + stu.getId() + "," + major.getId() + ")";
					first = false; // 第一条记录拼接完成，first立刻修改为false

				} else { // 不是第一条
					sql += ",(" + stu.getId() + "," + major.getId() + ")";
					// 左侧多了一个，分隔符
				}
			}

			ps.executeUpdate(sql); // 执行t_vet_speciality添加操作

			con.commit();
			// 提交整个事务执行：往t_vet表添加和往t_vet_speciality添加的操作都提交执行
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常:" + e);
		} finally {
			DBUtils.release(ps, con);
		}
	}

	public void modify(Stu stu) throws Exception {
		/*
		 * Connection con = DBUtils.getConnection();
		 * 
		 * PreparedStatement ps = null;
		 * 
		 * try {
		 */
			/*
			 * con.setAutoCommit(false);//关闭自动提交，事务开始
			 * 
			 * 
			 * 更新t_vet_speciality表的记录：先删除医生id对应的原纪录，再插入新的记录
			 * 
			 * 
			 * String sql = "delete from t_stu_major where stuId=?";
			 * 
			 * ps=con.prepareStatement(sql); ps.setInt(1, stu.getId());
			 * 
			 * ps.executeUpdate();//删除原医生-专长对应关系记录 ps.close();
			 * 
			 * sql = "insert into t_stu_major values";//添加医生的新专长信息
			 * 
			 * boolean first = true; for(Major major : stu.getMajors()) {//拼接insert语句 if
			 * (first) { sql += "("+stu.getId()+","+major.getId()+")"; first = false; }else
			 * { sql += ",("+stu.getId()+","+major.getId()+")"; } }
			 * 
			 * ps=con.prepareStatement(sql); ps.executeUpdate();//执行添加
			 * 
			 * con.commit(); //提交事务执行
			 */ 
			Connection con = DBUtils.getConnection();
	    	PreparedStatement ps = null;
	    	
	    	try {
				ps=con.prepareStatement("update t_stu set name=?,gender=?,birthday=?,tel=? where id=?");
				
				ps.setString(1, stu.getName());
				ps.setString(2, stu.getGender());
				ps.setString(3, stu.getBirthday());
				ps.setString(4, stu.getTel());
				ps.setInt(5, stu.getId());
				
				ps.executeUpdate();//执行update语句
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常：" + e);
		} finally {
			DBUtils.release(ps, con);
		}
	}

	// 查询所有学生信息
	public List<Stu> getAll() throws Exception {

		List<Stu> stus = new ArrayList<Stu>();
		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select * from t_stu");
			rs = ps.executeQuery();

			while (rs.next()) {
				Stu stu = new Stu();
				stu.setId(rs.getInt("id"));
				stu.setName(rs.getString("name"));
				stu.setGender(rs.getString("gender"));
				stu.setBirthday(rs.getString("birthday"));
				stu.setTel(rs.getString("tel"));

				stus.add(stu);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常：" + e);
		} finally {
			DBUtils.release(rs, ps, con);
		}
		return stus;
	}

	// 根据学生id查询学生
	public Stu getById(int id) throws Exception {
		Stu stu = null;

		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("SELECT * FROM t_stu WHERE id=?");

			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				stu = new Stu();
				stu.setId(rs.getInt("id"));
				stu.setName(rs.getString("name"));
				stu.setGender(rs.getString("gender"));
				stu.setBirthday(rs.getString("birthday"));
				stu.setTel(rs.getString("tel"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常:" + e);
		} finally {
			DBUtils.release(rs, ps, con);
		}
		return stu;
	}

	// 删除学生
	public void delete(int stuId) throws Exception {
		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;

		try {
			con.setAutoCommit(false); // 开启事务

			String sql = "DELETE FROM t_stu WHERE id=" + stuId;
			ps = con.prepareStatement(sql);
			ps.executeUpdate(); // 执行delete操作

			con.commit(); // 提交事务
		} catch (Exception e) {
			e.printStackTrace();

			if (con != null) {
				con.rollback(); // 遇异常，回滚事务
			}
			throw new Exception("数据库访问出现异常：" + e);
		} finally {
			DBUtils.release(ps, con);
		}
	}
}
