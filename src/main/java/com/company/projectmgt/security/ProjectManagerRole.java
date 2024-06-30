package com.company.projectmgt.security;

import io.jmix.security.role.annotation.ResourceRole;

@ResourceRole(name = "ProjectManager", code = ProjectManagerRole.CODE)
public interface ProjectManagerRole {
    String CODE = "project-manager";
}