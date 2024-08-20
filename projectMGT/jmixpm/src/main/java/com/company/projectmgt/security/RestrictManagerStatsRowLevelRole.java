package com.company.projectmgt.security;

import com.company.projectmgt.entity.Project;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "RestrictManagerStatsRowLevelRole", code = RestrictManagerStatsRowLevelRole.CODE)
public interface RestrictManagerStatsRowLevelRole {
    String CODE = "restrict-manager-stats-row-level-role";

    @JpqlRowLevelPolicy(entityClass = Project.class, where = "{E}.manager.username=:current_user_username")
    void project();
}