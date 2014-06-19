package ${packgeDO.daoPackageName};

import java.util.List;
import ${domain.packageName}.${domain.className};
import ${packgeDO.exceptionPackageName}.DAOException;

<#assign len = domain.className?index_of("DO")>
<#assign param="${domain.className?uncap_first}">

 /**
 * ${data_object_comment} DAO
 * @author haisheng.Long ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
 */
public interface ${domain.className?substring(0,len)}DAO {

	/**
	 * 插入  ${data_object_comment}
	 * @param ${param}
	 * @return 主键
	 * @throws DAOException
	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
	 */
	Long insert(${domain.className} ${param}) throws DAOException;

	/**
	 * 根据ID更新 ${data_object_comment}全部属性
	 * @param ${param}
	 * @return 更新行数
	 * @throws DAOException
	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
	 */
	Integer update(${domain.className} ${param}) throws DAOException;

	/**
	 * 根据ID删除 ${data_object_comment}
	 * @param id
	 * @return 删除行数
	 * @throws DAOException
	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
	 */
	Integer deleteById(Long id) throws DAOException;

	/**
	 * 动态更新 ${data_object_comment}部分属性，包括全部
	 * @param ${param}
	 * @return 更新行数
	 * @throws DAOException
	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
	 */
	Integer updateDynamic(${domain.className} ${param}) throws DAOException;

	/**
	 * 根据ID查询 一个 ${data_object_comment}
	 * @param id
	 * @return ${domain.className}
	 * @throws DAOException
	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
	 */
	${domain.className} selectById(Long id) throws DAOException;

	/**
	 * 根据  ${data_object_comment} 动态返回记录数
	 * @param ${param}
	 * @return 记录条数
	 * @throws DAOException
	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
	 */
	Long selectCountDynamic(${domain.className} ${param}) throws DAOException;

	/**
	 * 根据  ${data_object_comment} 动态返回 ${data_object_comment} 列表
	 * @param ${param}
	 * @return List<${domain.className}>
	 * @throws DAOException
	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
	 */
	List<${domain.className}> selectDynamic(${domain.className} ${param}) throws DAOException;

	/**
	 * 根据  ${data_object_comment} 动态返回 ${data_object_comment} Limit 列表
	 * @param ${param} start,pageSize属性必须指定
	 * @return List<${domain.className}>
	 * @throws DAOException
	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
	 */
	List<${domain.className}> selectDynamicPageQuery(${domain.className} ${param}) throws DAOException;
}
