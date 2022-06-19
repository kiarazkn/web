package ph.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ph.entity.Visit;
import ph.utils.DBUtils;

public class VisitDAO {

	//添加病例
	public void save(Visit visit) throws Exception {
		
		Connection con = DBUtils.getConnection();
		PreparedStatement ps =null;
		
		try {
			
			ps = con.prepareStatement("insert into t_visit value(null,?,?,CURDATE(),?,?)");
			//CURDATE()函数：返回当前日期
			
			ps.setInt(1, visit.getPetId());
			ps.setInt(2, visit.getVetId());
			ps.setString(3, visit.getDescription());
			ps.setString(4, visit.getTreatment());
			
			ps.executeUpdate();//执行insert语句
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常：" + e);
		} finally {
			DBUtils.release(ps, con);
		}
	}

	//查询宠物的病例（连接查询医生的姓名）
	public List<Visit> getVisitsByPetId(int petId) throws Exception {
		
		List<Visit> visits = new ArrayList<Visit>();
		
		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			ps = con.prepareStatement("SELECT t_visit.*,t_vet.name"
					+ " FROM t_visit JOIN t_vet on t_visit.vetId=t_vet.id"
					+ " WHERE t_visit.petId=?");
			
			ps.setInt(1, petId);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				Visit visit = new Visit();
				
				visit.setId(rs.getInt("id"));
				visit.setPetId(petId);
				visit.setVetId(rs.getInt("vetId"));
				visit.setVisitdate(rs.getString("visitdate"));
				visit.setDescription(rs.getString("description"));
				visit.setTreatment(rs.getString("treatment"));
				
				visit.setVetName(rs.getString("name"));	//封装医生姓名
				
				visits.add(visit);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常：" + e);
		} finally {
			DBUtils.release(ps, con);
		}
		return visits;
	}
}
