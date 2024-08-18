package com.company.projectmgt.security;

import com.company.projectmgt.entity.Member;
import com.company.projectmgt.entity.Otp;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;

@ResourceRole(name = "anonymous", code = AnonymousRole.CODE, scope = "API")
public interface AnonymousRole {
    String CODE = "ANONYMOUS";

    @SpecificPolicy(resources = {"rest.enabled", "rest.fileDownload.enabled", "rest.fileUpload.enabled"})
    void specific();

    @EntityAttributePolicy(entityClass = Member.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Member.class, actions = EntityPolicyAction.ALL)
    void member();

    @EntityAttributePolicy(entityClass = Otp.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Otp.class, actions = EntityPolicyAction.ALL)
    void otp();
}