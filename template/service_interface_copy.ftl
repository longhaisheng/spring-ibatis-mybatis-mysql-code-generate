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
	 * @param isAllField 是否更新所有字段
	 * @return 更新行数
	 * @throws ServiceException
	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
	 */
	int update(${domain.className} ${param},boolean isAllField) throws ServiceException;

//	/**
//	 * 根据ID更新 ${data_object_comment}全部字段
//	 * @param ${param}
//	 * @return 更新行数
//	 * @throws ServiceException
//	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
//	 */
//	int updateById(${domain.className} ${param}) throws ServiceException;

	/**
	 * 根据ID删除 ${data_object_comment}
	 * @param id
	 * @return 删除行数
	 * @throws ServiceException
	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
	 */
	int deleteById(Long id) throws ServiceException;

//	/**
//	 * 动态更新 ${data_object_comment}部分字段
//	 * @param ${param}
//	 * @return 更新行数
//	 * @throws ServiceException
//	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
//	 */
//	int updateDynamic(${domain.className} ${param}) throws ServiceException;

	/**
	 * 根据ID查询 一个 ${data_object_comment}
	 * @param id
	 * @return ${domain.className}
	 * @throws ServiceException
	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
	 */
	${domain.className} selectById(Long id) throws ServiceException;

	/**
	 * 根据  ${data_object_comment} 动态返回记录数
	 * @param ${param}
	 * @return 记录数
	 * @throws ServiceException
	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
	 */
	Long selectCountDynamic(${domain.className} ${param}) throws ServiceException;

	/**
	 * 动态返回 ${data_object_comment} 列表
	 * @param ${param}
	 * @return List<${domain.className}>
	 * @throws ServiceException
	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
	 */
	List<${domain.className}> selectDynamic(${domain.className} ${param}) throws ServiceException;

	/**
	 * 动态返回 ${data_object_comment} 分页列表
	 * @param ${param}
	 * @return Page<${domain.className}>
	 * @throws ServiceException
	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
	 */
	Page<${domain.className}> queryPageListBy${domain.className}(${domain.className} ${param});

	/**
	 * 动态返回 ${data_object_comment} 分页列表
	 * @param ${param}
	 * @param startPage 起始页
	 * @param pageSize 每页记录数
	 * @return Page<${domain.className}>
	 * @throws ServiceException
	 * @author longhaisheng ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
	 */
	Page<${domain.className}> queryPageListBy${domain.className}AndStartPageSize(${domain.className} ${param},int startPage,int pageSize);

}
