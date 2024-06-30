package com.company.projectmgt.security;

import com.company.projectmgt.entity.Task;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "RestrictLoadAttachmentRowLevelRole", code = RestrictLoadAttachmentRowLevelRole.CODE)
public interface RestrictLoadAttachmentRowLevelRole {
    String CODE = "restrict-load-attachment-row-level-role";

    @JpqlRowLevelPolicy(entityClass = Task.class, where = "{E}.project.manager.username=:current_user_username")
    void task();
}