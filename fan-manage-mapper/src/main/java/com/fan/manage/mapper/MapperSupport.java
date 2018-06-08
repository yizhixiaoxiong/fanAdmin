package com.fan.manage.mapper;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

/**
 * DAO
 * @author hanxiaofan
 *
 */
@SuppressWarnings("rawtypes")
@Repository("mapperSupport")
public class MapperSupport implements BaseMapper {
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 保存对象
	 * @param str  标识符
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object save(String str, Object obj) throws Exception {
		return sqlSessionTemplate.insert(str, obj);
	}
	
	/**
	 * 批量保存对象
	 * @param str
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public Object batchSave(String str,List objs) throws Exception{
		return sqlSessionTemplate.insert(str, objs);
	}
	
	/**
	 * 修改对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object update(String str, Object obj) throws Exception {
		return sqlSessionTemplate.update(str, obj);
	}
	
	/**
	 * 批量更新
	 * @param str
	 * @param objs
	 * @throws Exception
	 */
	
	public void batchUpdate(String str, List objs )throws Exception{
		SqlSessionFactory sessionFactory = sqlSessionTemplate.getSqlSessionFactory();
		//批量执行器,ExcecutorType.BATCH ,创建一个批处理的sqlSession，false事务最后是统一提交的，可能导致内存溢出
		//ture,是自动提交，无法控制条数
		SqlSession sqlSession = sessionFactory.openSession(ExecutorType.BATCH,false);
		try {
			//非null判断
			if(objs!=null){
				for (int i = 0,size = objs.size(); i < size; i++) {
					//单条更新
					sqlSession.update(str, objs.get(i));
					}
				//起到一种预插入的作用(执行了这行代码之后,要插入的数据会锁定数据库的一行记录,
				//并把数据库默认返回的主键赋值给插入的对象,这样就可以把该对象的主键赋值给其他需要的对象中去了
				sqlSession.flushStatements();
				sqlSession.commit();
				sqlSession.clearCache();
			}
		} finally {
			sqlSession.close();
		}
	}
	/**
	 * 删除对象 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object delete(String str, Object obj) throws Exception {
		return sqlSessionTemplate.delete(str, obj);
	}
	/**
	 * 查找对象
	 * @param str 标识符
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object findForObject(String str, Object obj) throws Exception {
		return sqlSessionTemplate.selectOne(str, obj);
	}
	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object findForList(String str, Object obj) throws Exception {
		return sqlSessionTemplate.selectList(str, obj);
	}
	
	/**
	 * 查找对象
	 * @param sql
	 * @param obj
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object findForMap(String sql, Object obj, String key, String value)
			throws Exception {
		return sqlSessionTemplate.selectMap(sql, obj, key);
	}
	
	
}
