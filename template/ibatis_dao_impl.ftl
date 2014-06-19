package ${packgeDO.daoPackageName}.ibatis;
<#assign len = domain.className?index_of("DO")>
<#if use_statement_name_spaces?string=="1">
	<#assign ibatis_class_dao="${domain.packageName}.${domain.className?substring(0,len)}.Ibatis${domain.className?substring(0,len)}DAO">
<#else>
	<#assign ibatis_class_dao="Ibatis${domain.className?substring(0,len)}DAO">
</#if>
<#assign param="${domain.className?uncap_first}">

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
<#if bean_is_auto_wire?string=="1">
import org.springframework.stereotype.Component;
</#if>
import ${packgeDO.daoPackageName}.${domain.className?substring(0,len)}DAO;
import ${domain.packageName}.${domain.className};
import ${packgeDO.exceptionPackageName}.DAOException;

<#if bean_is_auto_wire?string=="1">
@Component(value="${domain.className?substring(0,len)?uncap_first}DAO")
</#if>
public class Ibatis${domain.className?substring(0,len)}DAO extends SqlMapClientDaoSupport implements ${domain.className?substring(0,len)}DAO{

	@Override
	public Long insert(${domain.className} ${param}) throws DAOException {
		return (Long) getSqlMapClientTemplate().insert("${ibatis_class_dao}.insert", ${param});
	}

	@Override
	public Integer update(${domain.className} ${param}) throws DAOException {
		return (Integer) getSqlMapClientTemplate().update("${ibatis_class_dao}.updateById", ${param});
	}

	@Override
	public Integer deleteById(Long id) throws DAOException {
		return (Integer) getSqlMapClientTemplate().delete("${ibatis_class_dao}.deleteById",id);
	}

	@Override
	public Integer updateDynamic(${domain.className} ${param}) throws DAOException {
		return (Integer) getSqlMapClientTemplate().update("${ibatis_class_dao}.update.dynamic", ${param});
	}

	@Override
	public ${domain.className} selectById(Long id) throws DAOException {
		return (${domain.className}) getSqlMapClientTemplate().queryForObject("${ibatis_class_dao}.selectById", id);
	}

	@Override
	public Long selectCountDynamic(${domain.className} ${param}) throws DAOException {
		return (Long) getSqlMapClientTemplate().queryForObject("${ibatis_class_dao}.select.dynamic.count", ${param});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<${domain.className}> selectDynamic(${domain.className} ${param}) throws DAOException {
		return getSqlMapClientTemplate().queryForList("${ibatis_class_dao}.select.dynamic", ${param});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<${domain.className}> selectDynamicPageQuery(${domain.className} ${param}) throws DAOException {
		return getSqlMapClientTemplate().queryForList("${ibatis_class_dao}.select.dynamic.page.query", ${param});
	}


}