package ${packgeDO.daoPackageName}.mybatis;
<#assign len = domain.className?index_of("DO")>
<#if use_statement_name_spaces?string=="1">
	<#assign ibatis_class_dao="${domain.packageName}.${domain.className?substring(0,len)}.Mybatis${domain.className?substring(0,len)}DAO">
<#else>
	<#assign ibatis_class_dao="Mybatis${domain.className?substring(0,len)}DAO">
</#if>
<#assign param="${domain.className?uncap_first}">
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
<#if bean_is_auto_wire?string=="1">
import org.springframework.stereotype.Component;
</#if>
import ${packgeDO.daoPackageName}.${domain.className?substring(0,len)}DAO;
import ${domain.packageName}.${domain.className};
import ${packgeDO.exceptionPackageName}.DAOException;

<#if bean_is_auto_wire?string=="1">
@Component(value="${domain.className?substring(0,len)?uncap_first}DAO")
</#if>
public class Mybatis${domain.className?substring(0,len)}DAO extends SqlSessionDaoSupport implements ${domain.className?substring(0,len)}DAO {
<#assign getPrimaryMethod="get${tableDO.primaryProperty?cap_first}()">
	public Long insert(${domain.className} ${param}) throws DAOException {
		int i = getSqlSession().insert("${ibatis_class_dao}_insert", ${param});
		if (i > 0) {
			return Long.valueOf(${param}.${getPrimaryMethod});
		}
		return 0L;
	}

	@Override
	public Integer update(${domain.className} ${param}) throws DAOException {
		return getSqlSession().update("${ibatis_class_dao}_updateById", ${param});
	}

	@Override
	public Integer deleteById(Long id) throws DAOException {
		return getSqlSession().delete("${ibatis_class_dao}_deleteById", id);
	}

	@Override
	public Integer updateDynamic(${domain.className} ${param}) throws DAOException {
		return getSqlSession().update("${ibatis_class_dao}_update_dynamic", ${param});
	}

	@Override
	public ${domain.className} selectById(Long id) throws DAOException {
		return getSqlSession().selectOne("${ibatis_class_dao}_selectById", id);
	}

	@Override
	public Long selectCountDynamic(${domain.className} ${param}) throws DAOException {
		return getSqlSession().selectOne("${ibatis_class_dao}_select_dynamic_count", ${param});
	}

	@Override
	public List<${domain.className}> selectDynamic(${domain.className} ${param}) throws DAOException {
		return getSqlSession().selectList("${ibatis_class_dao}_select_dynamic", ${param});
	}

	@Override
	public List<${domain.className}> selectDynamicPageQuery(${domain.className} ${param}) throws DAOException {
		return getSqlSession().selectList("${ibatis_class_dao}_select_dynamic_page_query", ${param});
	}

}
