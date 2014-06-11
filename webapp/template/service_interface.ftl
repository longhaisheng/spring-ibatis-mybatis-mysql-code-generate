package ${packgeDO.servicePackageName};

import java.util.List;
import ${packgeDO.utilPackageName}.Page;
import ${packgeDO.exceptionPackageName}.ServiceException;
import ${domain.packageName}.${domain.className};

<#assign len = domain.className?index_of("DO")>
<#assign param="${domain.className?uncap_first}">
 /**
 * ${data_object_comment} Service
 * @author haisheng.long ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
 */
public interface ${domain.className?substring(0,len)}Service {

	/**
	 * 插入  ${data_object_comment}
	 * @param ${param}
	 * @return 主键
	 * @throws ServiceException
	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
	 */
	Long insert(${domain.className} ${param}) throws ServiceException;

	/**
	 * 根据${domain.className}对象更新 ${data_object_comment}
	 * @param ${param}
	 * @return 更新行数
	 * @throws ServiceException
	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
	 */
	int update(${domain.className} ${param}) throws ServiceException;

	/**
	 * 根据ID删除 ${data_object_comment}
	 * @param id
	 * @return 删除行数
	 * @throws ServiceException
	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
	 */
	int delete(Long id) throws ServiceException;

	/**
	 * 根据ID查询 一个 ${data_object_comment}
	 * @param id
	 * @return ${domain.className}
	 * @throws ServiceException
	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
	 */
	${domain.className} get(Long id) throws ServiceException;

	/**
	 * 根据  ${data_object_comment} 动态返回记录数
	 * @param ${param}
	 * @return 记录数
	 * @throws ServiceException
	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
	 */
	Long getCount(${domain.className} ${param}) throws ServiceException;

	/**
	 * 动态返回 ${data_object_comment} 列表
	 * @param ${param}
	 * @return List<${domain.className}>
	 * @throws ServiceException
	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
	 */
	List<${domain.className}> queryList(${domain.className} ${param}) throws ServiceException;

	/**
	 * 动态返回 ${data_object_comment} 分页列表
	 * @param ${param}
	 * @return Page<${domain.className}>
	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
	 */
	Page<${domain.className}> queryPageList(${domain.className} ${param});

}
