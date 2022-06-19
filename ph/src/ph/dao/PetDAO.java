package ph.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;

import ph.entity.Pet;
import ph.utils.DBUtils;

public class PetDAO {
	
	//根据客户id查询客户的宠物
	public List<Pet> getPetsByOwnerId(int ownerId) throws Exception{
		
		List<Pet> pets=new ArrayList<Pet>();
		
		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement("SELECT * FROM t_pet WHERE ownerId=?");
			ps.setInt(1, ownerId);
			rs=ps.executeQuery();
			
			while (rs.next()) {
				Pet pet=new Pet();
				pet.setId(rs.getInt("id"));
				pet.setName(rs.getString("name"));
				pet.setBirthdate(rs.getString("birthdate"));
				pet.setOwnerId(ownerId);
				pet.setPhoto(rs.getString("photo"));
				
				pets.add(pet);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常:" + e);
		}finally {
			DBUtils.release(rs, ps, con);
		}
		return pets;
	}

	//根据id删除宠物及其关联的病例
	public void delete(int petId) throws Exception{
		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		
		try {
			con.setAutoCommit(false);  //开启事务
			
			String sql="DELETE FROM t_visit WHERE petId=" + petId;
			//先删除宠物关联着的病例
			ps = con.prepareStatement(sql);
			ps.executeUpdate(); //执行delete操作
			
			sql="DELETE FROM t_pet WHERE id=" + petId;
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

	//添加新宠物
	public void save(Pet pet) throws Exception{
		
		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		
		try {
			
			ps = con.prepareStatement("INSERT INTO t_pet VALUES(null,?,?,?,?)");
			
			ps.setString(1, pet.getName());
			ps.setString(2, pet.getBirthdate());
			ps.setString(3, pet.getPhoto());
			ps.setInt(4, pet.getOwnerId());
			
			ps.executeUpdate(); //执行insert语句
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常:" + e);
		} finally {
			DBUtils.release(ps, con);
		}
	}
}
