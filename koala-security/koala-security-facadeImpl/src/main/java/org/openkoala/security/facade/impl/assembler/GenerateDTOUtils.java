package org.openkoala.security.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openkoala.security.core.domain.MenuResource;
import org.openkoala.security.core.domain.PageElementResource;
import org.openkoala.security.core.domain.Permission;
import org.openkoala.security.core.domain.Role;
import org.openkoala.security.core.domain.UrlAccessResource;
import org.openkoala.security.core.domain.User;
import org.openkoala.security.facade.dto.MenuResourceDTO;
import org.openkoala.security.facade.dto.PageElementResourceDTO;
import org.openkoala.security.facade.dto.PermissionDTO;
import org.openkoala.security.facade.dto.RoleDTO;
import org.openkoala.security.facade.dto.UrlAccessResourceDTO;
import org.openkoala.security.facade.dto.UserDTO;

/**
 * 生成DTO工具类
 * 
 * @author luzhao
 * 
 */
public final class GenerateDTOUtils {

	/**
	 * User 生成UserDto
	 * 
	 * @param user
	 * @return
	 */
	public static UserDTO generateUserDTOBy(User user) {
		UserDTO result = new UserDTO(user.getUserAccount(),user.getPassword(),user.getCreateDate(),user.getDescription());
		result.setId(user.getId());
		result.setName(user.getName());
		result.setEmail(user.getEmail());
		result.setTelePhone(user.getTelePhone());
		result.setLastLoginTime(user.getLastLoginTime());
		result.setVersion(user.getVersion());
		result.setCreateOwner(user.getCreateOwner());
		result.setLastModifyTime(user.getLastModifyTime());
		result.setDisabled(user.isDisabled());
		return result;
	}

	public static List<UserDTO> generateUserDTOsBy(List<User> users) {
		List<UserDTO> results = new ArrayList<UserDTO>();
		for (User user : users) {
			results.add(generateUserDTOBy(user));
		}
		return results;
	}

	public static List<RoleDTO> generateRoleDTOsBy(List<Role> roles) {
		List<RoleDTO> results = new ArrayList<RoleDTO>();
		for (Role role : roles) {
			results.add(generateRoleDTOBy(role));
		}
		return results;
	}

	public static List<PermissionDTO> generatePermissionDTOsBy(Collection<Permission> permissions) {
		List<PermissionDTO> results = new ArrayList<PermissionDTO>();
		for (Permission permission : permissions) {
			results.add(generatePermissionDTOBy(permission));
		}
		return results;
	}

	/**
	 * 生成RoleDto
	 * 
	 * @param role
	 * @return
	 */
	public static RoleDTO generateRoleDTOBy(Role role) {
		RoleDTO result = new RoleDTO(role.getId(), role.getName());
		result.setVersion(role.getVersion());
		result.setDescription(role.getDescription());
		return result;
	}

	/**
	 * 生成PermissionDto
	 * 
	 * @param permission
	 * @return
	 */
	public static PermissionDTO generatePermissionDTOBy(Permission permission) {
		PermissionDTO result = new PermissionDTO(permission.getId(),permission.getName(),permission.getIdentifier(),permission.getDescription());
		result.setVersion(permission.getVersion());
		return result;
	}

	public static MenuResourceDTO generateMenuResourceDTOBy(MenuResource menuResource) {
		MenuResourceDTO result = new MenuResourceDTO(menuResource.getId(),menuResource.getName());
		result.setVersion(menuResource.getVersion());
		result.setIcon(menuResource.getMenuIcon());
		result.setLevel(menuResource.getLevel());
		result.setUrl(menuResource.getUrl());
		result.setDescription(menuResource.getDescription());
		return result;
	}

	public static UrlAccessResourceDTO generateUrlAccessResourceDTOBy(UrlAccessResource urlAccessResource) {
		UrlAccessResourceDTO result = new UrlAccessResourceDTO(urlAccessResource.getId(), urlAccessResource.getName(), urlAccessResource.getUrl());
		result.setVersion(urlAccessResource.getVersion());
		result.setDescription(urlAccessResource.getDescription());
		return result;
	}
	
	public static PageElementResourceDTO generatePageElementResourceDTOBy(PageElementResource pageElementResource) {
		PageElementResourceDTO result = new PageElementResourceDTO(pageElementResource.getName(),pageElementResource.getDescription());
		result.setId(pageElementResource.getId());
		result.setVersion(pageElementResource.getVersion());
		result.setIdentifier(pageElementResource.getIdentifier());
		return result;
	}
}