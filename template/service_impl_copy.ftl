package ${packgeDO.serviceImplPackageName};

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
<#if bean_is_auto_wire?string=="1">
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
</#if>
<#assign len = domain.className?index_of("DO")>
<#assign DAOName = "${domain.className?substring(0,len)}DAO">
<#assign lower_dao_name = "${domain.className?substring(0,len)?uncap_first}DAO">
<#assign param="${domain.className?uncap_first}">

import ${packgeDO.daoPackageName}.${domain.className?substring(0,len)}DAO;
import ${domain.packageName}.${domain.className};
import ${packgeDO.servicePackageName}.${domain.className?substring(0,len)}Service;
import ${packgeDO.exceptionPackageName}.DAOException;
import ${packgeDO.exceptionPackageName}.ServiceException;
import ${packgeDO.utilPackageName}.Page;

<#if bean_is_auto_wire?string=="1">
@Service(value="${domain.className?substring(0,len)?uncap_first}Service")
</#if>
public class ${domain.className?substring(0,len)}ServiceImpl  implements ${domain.className?substring(0,len)}Service{

	private Log logger = LogFactory.getLog(this.getClass());

	<#if bean_is_auto_wire?string=="1">@Autowired</#if>
	private ${DAOName} ${lower_dao_name};

	<#if bean_is_auto_wire?string=="0">
	public void set${DAOName}(${DAOName} ${lower_dao_name}) {
		this.${lower_dao_name} = ${lower_dao_name};
	}

	</#if>
	@Override
	public Long insert(${domain.className} ${param}) throws ServiceException {
		try {
			return ${lower_dao_name}.insert(${param});
		}catch(DAOException e){
			logger.error(e);
            throw new ServiceException(e);
		}
	}

//	@Override
//	public int updateById(${domain.className} ${param}) throws ServiceException {
//		try {
//			return (Integer) ${lower_dao_name}.updateById(${param});
//		}catch(DAOException e){
//			logger.error(e);
//            throw new ServiceException(e);
//		}
//	}

	@Override
	public int update(${domain.className} ${param},boolean isAllField) throws ServiceException {
		try {
			if(isAllField){
				return (Integer) ${lower_dao_name}.update(${param});
			}else{
				return (Integer) ${lower_dao_name}.updateDynamic(${param});
			}
		}catch(DAOException e){
			logger.error(e);
            throw new ServiceException(e);
		}
	}

	@Override
	public int deleteById(Long id) throws ServiceException {
		try {
			return (Integer) ${lower_dao_name}.deleteById(id);
		}catch(DAOException e){
			logger.error(e);
            throw new ServiceException(e);
		}
	}

//	@Override
//	public int updateDynamic(${domain.className} ${param}) throws ServiceException {
//		try {
//			return (Integer) ${lower_dao_name}.updateDynamic(${param});
//		}catch(DAOException e){
//			logger.error(e);
//            throw new ServiceException(e);
//		}
//	}

	@Override
	public ${domain.className} selectById(Long id) throws ServiceException {
		try {
			return ${lower_dao_name}.selectById(id);
		}catch(DAOException e){
			logger.error(e);
            throw new ServiceException(e);
		}
	}

	@Override
	public Long selectCountDynamic(${domain.className} ${param}) throws ServiceException {
		try {
			return ${lower_dao_name}.selectCountDynamic(${param});
		}catch(DAOException e){
			logger.error(e);
            throw new ServiceException(e);
		}
	}

	@Override
	public List<${domain.className}> selectDynamic(${domain.className} ${param}) throws ServiceException {
		try {
			return ${lower_dao_name}.selectDynamic(${param});
		}catch(DAOException e){
			logger.error(e);
            throw new ServiceException(e);
		}
	}

	public Page<${domain.className}> queryPageListBy${domain.className}(${domain.className} ${param}) {
		if (${param} != null) {
			Long totalCount = this.selectCountDynamic(${param});
			List<${domain.className}> resultList = this.selectDynamic(${param});

			Page<${domain.className}> page = new Page<${domain.className}>();
			page.setPageNo(${param}.getStartPage());
			page.setPageSize(${param}.getPageSize());
			page.setTotalCount(totalCount.intValue());
			page.setList(resultList);
			return page;
		}
		return new Page<${domain.className}>();
	}

	public Page<${domain.className}> queryPageListBy${domain.className}AndStartPageSize(${domain.className} ${param},int startPage,int pageSize){
		if (${param} != null && startPage>0 && pageSize>0) {
			${param}.setStartPage(startPage);
			${param}.setPageSize(pageSize);
			return this.queryPageListBy${domain.className}(${param});
		}
		return new Page<${domain.className}>();
	}

}
