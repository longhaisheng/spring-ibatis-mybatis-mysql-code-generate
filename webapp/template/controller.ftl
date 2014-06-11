package ${packgeDO.controllerPackageName};

<#assign len = domain.className?index_of("DO")>
<#assign param="${domain.className?uncap_first}">
<#assign viewParam="${param?substring(0,len)}">

import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import ${domain.packageName}.${domain.className};
import ${packgeDO.servicePackageName}.${domain.className?substring(0,len)}Service;
import ${packgeDO.utilPackageName}.Page;

@Controller
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ${domain.className?substring(0,len)}Controller {

	@Autowired
	${domain.className?substring(0,len)}Service ${viewParam}Service;

	@RequestMapping(value="/${viewParam}/add",method=RequestMethod.GET)
	public String add(final ModelMap model) {
		${domain.className} ${param} = new ${domain.className}();
		model.addAttribute("${viewParam}", ${param});
		return "${viewParam}/add";
	}

	@RequestMapping(value="/${viewParam}/edit/{id}",method=RequestMethod.GET)
	public String edit(final ModelMap model,@PathVariable Long id) {
		${domain.className} ${param} = ${viewParam}Service.get(id);
		model.addAttribute("${viewParam}", ${param});
		return "${viewParam}/edit";
	}

	@RequestMapping(value="/${viewParam}/view/{id}",method=RequestMethod.GET)
	public String view(final ModelMap model,@PathVariable Long id) {
		${domain.className} ${param} = ${viewParam}Service.get(id);
		model.addAttribute("${viewParam}", ${param});
		return "${viewParam}/view";
	}

	@RequestMapping(value="/${viewParam}/save",method=RequestMethod.POST)
	public String save(@ModelAttribute("${viewParam}")@Valid ${domain.className} ${viewParam},BindingResult result) {
		if (result.hasErrors()) {
			return null;
		}
		${viewParam}Service.insert(${viewParam});
		return "redirect:/${viewParam}/list";
	}

	@RequestMapping(value="/${viewParam}/update/{id}",method=RequestMethod.PUT)
	public String update(@ModelAttribute("${viewParam}")@Valid ${domain.className} ${viewParam},BindingResult result,@PathVariable ${tableDO.primaryColumnType} id) {
		if (result.hasErrors()) {
			return null;
		}
		${viewParam}.setId(id);
		${viewParam}Service.update(${viewParam});
		return "redirect:/${viewParam}/list";
	}

	@RequestMapping(value="/${viewParam}/delete/{id}",method=RequestMethod.DELETE)
	public String delete(@PathVariable Long id) {
		${viewParam}Service.delete(id);
		return "redirect:/${viewParam}/list";
	}

	@RequestMapping(value="/${viewParam}/list")
	public String list(@ModelAttribute("${viewParam}") ${domain.className} ${viewParam},final ModelMap model,WebRequest request) {
		if(${viewParam}==null){
			${viewParam}=new ${domain.className}();
		}
		int startPage=Integer.valueOf(StringUtils.isNotBlank(request.getParameter("pageNo"))?request.getParameter("pageNo"):"1");
		${viewParam}.setPageSize(20);
		${viewParam}.setStartPage(startPage);
		Page<${domain.className}> page=${viewParam}Service.queryPageList(${viewParam});

		model.addAttribute("page", page);
		return "${viewParam}/list";
	}

}
