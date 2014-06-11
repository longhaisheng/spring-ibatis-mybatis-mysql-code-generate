package ${packgeDO.constantPackageName};
<#assign len = domain.className?index_of("DO")>

 /**
 * ${data_object_comment} 常量
 * @author haisheng.Long ${create_time?if_exists?string("yyyy-MM-dd HH:mm:ss")}
 */
public interface ${domain.className?substring(0,len)}Constant{


}
