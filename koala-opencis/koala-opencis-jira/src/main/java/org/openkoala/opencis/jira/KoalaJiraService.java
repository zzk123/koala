package org.openkoala.opencis.jira;

import com.atlassian.jira.rpc.soap.client.*;
import org.openkoala.opencis.CISClientBaseRuntimeException;
import org.openkoala.opencis.api.Developer;
import org.openkoala.opencis.api.Project;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: zjzhai
 * Date: 1/19/14
 * Time: 2:36 PM
 */
public class KoalaJiraService {

    private String token;

    private JiraSoapService jiraService;


    private KoalaJiraService() {
    }

    public KoalaJiraService(String token, JiraSoapService jiraService) {
        this.token = token;
        this.jiraService = jiraService;
    }

    public RemoteProject[] getProjectsNoSchemes() throws RemoteException {
        return jiraService.getProjectsNoSchemes(token);
    }


    public RemoteUser getUser(String userName) throws RemoteException {
        return jiraService.getUser(token, userName);
    }


    public void createProject(Project project, String jiraServerAddress) throws RemoteException {
        jiraService.createProject(token, project.getProjectName().toUpperCase(), project.getProjectName(),
                project.getDescription(), jiraServerAddress, project.getProjectLead(),
                jiraService.getPermissionSchemes(token)[0], null, null);
    }

    public void createRole(String roleName) throws RemoteException {
        RemoteProjectRole remoteProjectRole = new RemoteProjectRole();
        remoteProjectRole.setName(roleName);
        jiraService.createProjectRole(token, remoteProjectRole);
    }

    public boolean isProjectExist(Project project) {

        try {
            return jiraService.getProjectByKey(token, getProjectKey(project)) != null;
        } catch (RemoteException e) {
          //  e.printStackTrace();
            //找不到会抛异常
            return false;
        }

    }

    private RemoteProjectRole getRemoteProjectRole(String roleName) {
        RemoteProjectRole[] allProjectRoles = getAllRoles();
        if (allProjectRoles == null || allProjectRoles.length == 0) {
            return null;
        }

        for (RemoteProjectRole role : allProjectRoles) {
            if (role.getName().equals(roleName)) {
                return role;
            }
        }

        return null;

    }

    /**
     * 获取JIRA上的所有角色
     *
     * @return
     */
    public RemoteProjectRole[] getAllRoles() {
        RemoteProjectRole[] remoteProjectRoleArray = null;
        try {
            remoteProjectRoleArray = jiraService.getProjectRoles(token);
        } catch (java.rmi.RemoteException e) {
            throw new CISClientBaseRuntimeException("jira.remoteException", e);
        }
        return remoteProjectRoleArray;
    }

    public boolean isRoleExist(String roleName) {
        List<String> roleNames = null;
        try {
            roleNames = getAllProjectRoleNames();
        } catch (java.rmi.RemoteException e) {
            throw new CISClientBaseRuntimeException("jira.getAllRoleNameFailure");
        }
        if (roleNames.isEmpty()) {
            return false;
        }
        for (String everyRoleName : roleNames) {
            if (everyRoleName.equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isUserExist(String username_or_developId) {
        RemoteUser remoteUser = null;
        try {
            remoteUser = jiraService.getUser(token, username_or_developId);
        } catch (RemotePermissionException e1) {
            throw new CISClientBaseRuntimeException("该账号没有权限查询用户！");
        } catch (RemoteAuthenticationException e1) {
            throw new RuntimeException("the token is invalid or the SOAP session has timed out,please login again.", e1);
        } catch (java.rmi.RemoteException e1) {
            throw new RuntimeException(e1);
        }
        return remoteUser != null;

    }

    public void deleteProject(Project project) throws RemoteException {
        jiraService.deleteProject(token, getProjectKey(project));
    }


    public RemoteProject getRemoteProjectByProjectKey(String projectKey) {
        RemoteProject remoteProject = null;
        try {
            remoteProject = jiraService.getProjectByKey(token, projectKey);
        } catch (RemotePermissionException e) {
            e.printStackTrace();
            throw new CISClientBaseRuntimeException("getRemoteProjectByProjectKey RemotePermissionException", e);
        } catch (RemoteAuthenticationException e) {
            e.printStackTrace();
            throw new CISClientBaseRuntimeException("getRemoteProjectByProjectKey RemoteAuthenticationException", e);
        } catch (com.atlassian.jira.rpc.soap.client.RemoteException e) {
            e.printStackTrace();
            throw new CISClientBaseRuntimeException("getRemoteProjectByProjectKey RemoteException", e);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new CISClientBaseRuntimeException("getRemoteProjectByProjectKey RemoteException", e);
        }
        return remoteProject;
    }


    public RemoteProjectRole[] getProjectRoles() throws RemoteException {
        return jiraService.getProjectRoles(token);
    }

    private List<String> getAllProjectRoleNames() throws RemoteException {
        List<String> roleNames = new ArrayList<String>();
        for (RemoteProjectRole remoteProjectRole : getProjectRoles()) {
            roleNames.add(remoteProjectRole.getName());
        }
        return roleNames;
    }


    public String getToken() {
        return token;
    }


    public JiraSoapService getJiraService() {
        return jiraService;
    }

    public void createUser(Developer developer) throws RemoteException {
        jiraService.createUser(token, developer.getId(), developer.getPassword(), developer.getName(),
                developer.getEmail());
    }

    public void deleteUser(String userName) throws RemoteException {
        jiraService.deleteUser(token, userName);
    }

    public void addActorsToProjectRole(String projectKey, String userName, String projectRoleName) throws RemoteException {
        String[] userNames = new String[]{userName};
        RemoteProjectRole remoteProjectRole = getRemoteProjectRole(projectRoleName);
        RemoteProject remoteProject = getRemoteProjectByProjectKey(projectKey);
        //只有两种类型：atlassian-user-role-actor  atlassian-group-role-actor
        String actorType = "atlassian-user-role-actor";

        jiraService.addActorsToProjectRole(token, userNames, remoteProjectRole, remoteProject, actorType);

    }

    protected static String getProjectKey(Project project) {
        return project.getProjectName().toUpperCase();
    }
}