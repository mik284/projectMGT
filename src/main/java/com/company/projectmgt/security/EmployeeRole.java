package com.company.projectmgt.security;

import com.company.projectmgt.entity.Task;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;

@ResourceRole(name = "EmployeeRole", code = EmployeeRole.CODE)
public interface EmployeeRole {
    String CODE = "employee-role";

    @EntityAttributePolicy(entityClass = Task.class, attributes = {"name", "description"}, action = EntityAttributePolicyAction.MODIFY)
    @EntityAttributePolicy(entityClass = Task.class, attributes = {"id", "attachment", "dueDate", "assignee", "priority", "estimation", "project"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Task.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void task();

    @SpecificPolicy(resources = "ui.loginToUi")
    void specific();
}