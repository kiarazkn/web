package ph.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;
import com.mysql.jdbc.Statement;

import ph.entity.Speciality;
import ph.entity.Vet;
import ph.utils.DBUtils;

public class VetDAO {
	
	//根据医生姓名，专业名称查询医生信息
	public List<Vet> search(String vetName, String specName) throws Exception {
		
		List<Vet> vets = new ArrayList<Vet>();
		
		Connection con = DBUtils.getConnection();
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		try {
			
			//第一步：根据医生姓名、专业名称模糊查询t_vet表找出对应的医生，主要封装医生的基本信息：id，name
		
			ps = (PreparedStatement) con.prepareStatement("SELECT DISTINCT t_vet.*" + 
					"  FROM t_vet JOIN t_vet_speciality ON t_vet.id=t_vet_speciality.vetId" + 
					"  JOIN t_speciality ON t_vet_speciality.specId=t_speciality.id" + 
					"  WHERE t_vet.name LIKE  ?  AND t_speciality.name LIKE  ?");
			
			ps.setString(1, "%"+vetName+"%");
			ps.setString(2, "%"+specName+"%");
			
			rs=ps.executeQuery();
			
			while(rs.next()){
				Vet v=new Vet();
				v.setId(rs.getInt("id"));
				v.setName(rs.getString("name"));
				vets.add(v);
			}
			
			//第二步：再针对之前查到的所有的医生，查每个医生的所有专业信息，封装到该医生对象的specs属性上
			for(Vet v : vets){
			
				ps=(PreparedStatement) con.prepareStatement("SELECT t_speciality.*" +
						" FROM t_vet JOIN t_vet_speciality ON t_vet.id=t_vet_speciality.vetId" +
						" JOIN t_speciality ON t_vet_speciality.specId=t_speciality.id" +
						" WHERE t_vet.id=?");
			
				ps.setInt(1, v.getId()); //1,2,3
				
				rs=ps.executeQuery();
				
				while(rs.next()){
					Speciality spec=new Speciality();
					spec.setId(rs.getInt("id"));
					spec.setName(rs.getString("name"));
					v.getSpecs().add(spec);		//将查到的医生专业信息添加到医生对象的specs属性上
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常:" + e);
		} finally {
			DBUtils.release(rs,ps,con);
		}
		
		return vets;
	}
	
	/*
	 * 添加医生：1.医生表添加记录   2.医生专业关系表添加记录
	 * 
	 * 上述2操作要么都执行，要么都不执行，用JDBC事物处理来实现：
	 * 		开启事物：con.setAutoCommit(false)
	 * 		多条SQL语句(更新操作)的执行
	 * 		提交事物：con.rollback()
	 */
	
		
	public void save(Vet vet) throws Exception {
		
		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			con.setAutoCommit(false);	//关闭自动提交，开启事务
			
			String sql = "insert into t_vet VALUES(null,?)";
			// t_vet的主键id是由数据库自动生成的，故而这里id处设为null
			
			ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			//第二个参数表示允许返回自动生成的主键值
			
			ps.setString(1, vet.getName());
			
			ps.executeUpdate();  //执行t_vet表的添加操作
			
			rs = ps.getGeneratedKeys();
			//获得之前insert语句自动生成的医生id
			
			if (rs.next()) {
				vet.setId(rs.getInt(1));
				//将生成的医生id封装到医生对象vet上，之后t_vet_speciality添加医生与专业的关系时需要的
			}
			
			sql = "insert into t_vet_speciality VALUES";
			//往t_vet_speciality表添加记录，拼接SQL语句，注意一个医生可以具备多个专业
			//VALUES后面的SQL语句格式： (vetId,specId),(vetId,specId),(vetId,specId)...
			
			boolean first = true;	//first:表示是否是VALUES后的第一条记录
			
			for (Speciality spec : vet.getSpecs()) {
				//遍历vet的所有专业，拼接SQL语句串
				
				if (first) { //是第一条
					
					sql += "(" + vet.getId() + "," + spec.getId() + ")";
					first = false; 	//第一条记录拼接完成，first立刻修改为false
					
				} else { //不是第一条
					sql += ",(" + vet.getId() + "," + spec.getId() + ")";
					//左侧多了一个，分隔符
				}
			}
			
			ps.executeUpdate(sql); //执行t_vet_speciality添加操作
			
			con.commit();
			//提交整个事务执行：往t_vet表添加和往t_vet_speciality添加的操作都提交执行
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			if (con != null)
				con.rollback();		//遇异常，回滚事务，即添加操作都撤销
			
			throw new Exception("数据库访问出现异常:" + e);
			
		} finally {
			DBUtils.release(rs, ps, con);
		}
	}

	//查询所有医生
	public List<Vet> getAll() throws Exception{
		
		List<Vet> vets = new ArrayList<Vet>();
		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps=con.prepareStatement("select * from t_vet");
			rs=ps.executeQuery();
			
			while (rs.next()) {
				Vet v=new Vet();
				v.setId(rs.getInt("id"));
				v.setName(rs.getString("name"));
				
				vets.add(v);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常：" + e);
		} finally {
			DBUtils.release(rs, ps, con);
		}
		return vets;
	}

	//修改医生专长信息：更新t_vet_speciality表的数据
	public void modify(Vet vet) throws Exception{
		
		Connection con = DBUtils.getConnection();
		
		PreparedStatement ps = null;
		
		try {
			con.setAutoCommit(false);//关闭自动提交，事务开始

			/*
			 * 更新t_vet_speciality表的记录：先删除医生id对应的原纪录，再插入新的记录
			*/
			
			String sql = "delete from t_vet_speciality where vetId=?";
			
			ps=con.prepareStatement(sql);
			ps.setInt(1, vet.getId());
			ps.executeUpdate();//删除原医生-专长对应关系记录
			ps.close();
			
			sql = "insert into t_vet_speciality values";//添加医生的新专长信息
			
			boolean first = true;
			for(Speciality spec : vet.getSpecs()) {//拼接insert语句
				if (first) {
					sql += "("+vet.getId()+","+spec.getId()+")";
					first = false;
				}else {
					sql += ",("+vet.getId()+","+spec.getId()+")";
				}
			}
			
			ps=con.prepareStatement(sql);
			ps.executeUpdate();//执行添加
			
			con.commit();
			//提交事务执行
		} catch (Exception e) {
			e.printStackTrace();
			if (con != null)
				con.rollback();//遇异常，回滚事务，即操作都撤销
			throw new Exception("数据库访问出现异常："+e);
		}finally {
			DBUtils.release(ps, con);
		}
	}

}
