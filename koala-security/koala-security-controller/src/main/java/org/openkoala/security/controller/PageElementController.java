package org.openkoala.security.controller;

import javax.inject.Inject;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.security.facade.SecurityAccessFacade;
import org.openkoala.security.facade.SecurityConfigFacade;
import org.openkoala.security.facade.command.ChangePageElementResourcePropsCommand;
import org.openkoala.security.facade.command.CreatePageElementResourceCommand;
import org.openkoala.security.facade.dto.PageElementResourceDTO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 页面元素权限资源控制器
 * 
 * @author luzhao
 * 
 */
@Controller
@RequestMapping("/auth/page")
public class PageElementController {

	@Inject
	private SecurityAccessFacade securityAccessFacade;

	@Inject
	private SecurityConfigFacade securityConfigFacade;

	/**
	 * 添加页面元素权限资源。
	 * 
	 * @param command
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public InvokeResult add(CreatePageElementResourceCommand command) {
		return securityConfigFacade.createPageElementResource(command);
	}

	/**
	 * 更新页面元素权限资源。
	 * 
	 * @param command
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public InvokeResult update(ChangePageElementResourcePropsCommand command) {
		return securityConfigFacade.changePageElementResourceProps(command);
	}

	/**
	 * 撤销页面元素权限资源。
	 * 
	 * @param pageElementResourceIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/terminate", method = RequestMethod.POST)
	public InvokeResult terminate(Long[] pageElementResourceIds) {
		return securityConfigFacade.terminatePageElementResources(pageElementResourceIds);
	}

	/**
	 * 为页面元素资源授予权限Permission
	 * 
	 * @param permissionId
	 * @param pageElementResourceId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/grantPermisssionsToPageElementResource", method = RequestMethod.POST)
	public InvokeResult grantPermisssionsToPageElementResource(Long permissionId, Long pageElementResourceId) {
		return	securityConfigFacade.grantPermisssionsToPageElementResource(permissionId, pageElementResourceId);			
	}

	/**
	 * 从页面元素资源中撤销权限Permission
	 * 
	 * @param permissionId
	 * @param pageElementResourceId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/terminatePermissionsFromPageElementResource", method = RequestMethod.POST)
	public InvokeResult terminatePermissionsFromPageElementResource(Long permissionId, Long pageElementResourceId) {
		return securityConfigFacade.terminatePermissionsFromPageElementResource(permissionId, pageElementResourceId);
	}

	/**
	 * 分页查询页面元素权限资源， 可根据页面元素权限资源{@link org.openkoala.security.facade.dto.PageElementResourceDTO}条件进行查询。
	 * 
	 * @param page
	 * @param pagesize
	 * @param queryPageElementResourceCondition
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pagingQuery", method = RequestMethod.GET)
	public InvokeResult pagingQuery(int page, int pagesize, PageElementResourceDTO queryPageElementResourceCondition) {
		return securityAccessFacade.pagingQueryPageElementResources(page, pagesize, queryPageElementResourceCondition);
	}

	/**
	 * 根据页面元素权限资源ID分页查询已经授权的权限Permission。
	 * 
	 * @param page
	 * @param pagesize
	 * @param pageElementResourceId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pagingQueryGrantPermissionsByPageElementResourceId", method = RequestMethod.GET)
	public InvokeResult pagingQueryGrantPermissionsByPageElementResourceId(int page, int pagesize,
			Long pageElementResourceId) {
		return securityAccessFacade.pagingQueryGrantPermissionsByPageElementResourceId(page,
				pagesize, pageElementResourceId);
	}

	/**
	 * 根据页面元素权限资源ID分页查询还未授权的权限Permission。
	 * 
	 * @param page
	 * @param pagesize
	 * @param pageElementResourceId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pagingQueryNotGrantPermissionsByPageElementResourceId", method = RequestMethod.GET)
	public InvokeResult pagingQueryNotGrantPermissionsByPageElementResourceId(int page, int pagesize,
			Long pageElementResourceId) {
		return  securityAccessFacade.pagingQueryNotGrantPermissionsByPageElementResourceId(page,
				pagesize, pageElementResourceId);
	}
}