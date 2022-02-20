package com.czc.example.shiro.jdbc;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 金陵笑笑生
 * @description: TODO
 * @date 2022/2/20下午9:23
 */
public class JdbcShiro {
    private static final transient Logger log = LoggerFactory.getLogger(JdbcShiro.class);

    public static void main(String[] args) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:jdbc_shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("jack", "12345");
        try{
            subject.login(token);
            log.info("登陆成功");
        }catch (AuthenticationException e){
            e.printStackTrace();
        }
        subject.logout();
        log.info("登出");
    }
}
