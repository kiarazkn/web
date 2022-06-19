package ph.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import ph.entity.Speciality;
import ph.utils.DBUtils;

public class SpecialityDAO {

	// 查询所有专业
	public List<Speciality> getAll() throws Exception {
		List<Speciality> specs = new ArrayList<Speciality>();

		Connection con = DBUtils.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select * from t_speciality");

			rs = ps.executeQuery();

			while (rs.next()) {

				Speciality spec = new Speciality();
				spec.setId(rs.getInt("id"));
				spec.setName(rs.getString("name"));

				specs.add(spec);
			}
		} catch (SQLException e) {

			e.printStackTrace();

			throw new Exception("数据库访问出现异常:" + e);

		} finally { // 释放资源
			DBUtils.release(rs, ps, con);
		}
		return specs;
	}

	// 根据医生id查询其专业信息
	public List<Speciality> getSpecialitiesByVetId(int vetId) throws Exception {
		List<Speciality> specs = new ArrayList<Speciality>();
		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select t_speciality.*"
					+ " from t_speciality join t_vet_speciality on t_speciality.id=t_vet_speciality.specId"
					+ " where t_vet_speciality.vetId=" + vetId);
			// 根据医生id查其专业

			rs = ps.executeQuery();
			while (rs.next()) {
				Speciality spec = new Speciality();
				spec.setId(rs.getInt("id"));
				spec.setName(rs.getString("name"));
				specs.add(spec);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常：" + e);
		} finally {
			DBUtils.release(rs, ps, con);
		}
		return specs;
	}

	// 添加专业
	public void save(Speciality spec) throws Exception {

		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;

		try {

			ps = con.prepareStatement("INSERT INTO t_speciality VALUES(null,?)");

			ps.setString(1, spec.getName());

			ps.executeUpdate(); // 执行insert语句

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常:" + e);
		} finally {
			DBUtils.release(ps, con);
		}
	}

	// 删除专业
	public void delete(int specId) throws Exception {
		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;

		try {
			con.setAutoCommit(false); // 开启事务

			String sql = "DELETE FROM t_vet_speciality WHERE specId=" + specId;
			ps = con.prepareStatement(sql);
			ps.executeUpdate(); // 执行delete操作

			sql = "DELETE FROM t_speciality WHERE id=" + specId;
			ps.executeUpdate(sql);

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

	// 查询专业
	public List<Speciality> search(String specName) throws Exception {

		List<Speciality> specs = new ArrayList<Speciality>();

		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = con.prepareStatement("SELECT * FROM t_speciality WHERE name LIKE ?");
			// t_user表中，role值为customer代表客户(宠物主人)
			ps.setString(1, "%" + specName + "%");
			rs = ps.executeQuery();

			while (rs.next()) {
				Speciality spec = new Speciality();

				spec.setId(rs.getInt("id"));
				spec.setName(rs.getString("name"));

				specs.add(spec);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常:" + e);
		} finally {
			DBUtils.release(rs, ps, con);
		}
		return specs;
	}

	// 专业名称是否已经存在
		public boolean isExist(String name) throws Exception {
			boolean flag = false;

			Connection con = DBUtils.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				ps = con.prepareStatement("SELECT * from t_speciality WHERE name=?");
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
}
