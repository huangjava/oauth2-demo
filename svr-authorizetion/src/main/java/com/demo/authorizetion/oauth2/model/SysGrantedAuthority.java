package com.demo.authorizetion.oauth2.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * 授权权限模型
 *
 * @author HZY
 * @created 2018/10/18 14:50
 */
public class SysGrantedAuthority  implements GrantedAuthority {

    private static final long serialVersionUID = 5698641074914331015L;

    /**
     * 权限
     */
    private String authority;

    /**
     * 权限
     * @return authority
     */
    public String getAuthority() {
        return authority;
    }

    /**
     * 权限
     * @param authority 权限
     */
    public void setAuthority(String authority) {
        this.authority = authority;
    }

}
