package in.wenwen.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.wenwen.entity.Sso;
import in.wenwen.entity.User;
import in.wenwen.handler.AdminHandler;
import in.wenwen.out.ErrOut;
import in.wenwen.service.IUserService;
import in.wenwen.util.ConstUtil;
import in.wenwen.util.Err;
import in.wenwen.util.MD5Util;
import in.wenwen.util.UserManage;

import org.jiucheng.ioc.annotation.Inject;
import org.jiucheng.util.ObjectUtil;
import org.jiucheng.util.StringUtil;
import org.jiucheng.web.annotation.Controller;
import org.jiucheng.web.annotation.Controller.GET;
import org.jiucheng.web.annotation.Controller.POST;
import org.jiucheng.web.util.WebUtil;

@Controller(AdminHandler.class)
public class AdminController {
    
    @Inject
    private IUserService userService;
    
    private Err err = new Err("0", "已登录");
    @GET(value="/admin/online.json", out=ErrOut.class)
    public Err online() {
        return err;
    }
    
    @GET("/admin(/|/index.html){0,1}")
    public String index() {
        return ConstUtil.getAdminUri("index");
    }
    
    
    @GET("/admin/logout.html")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        //删除sso
        String token = getCookieValue(request.getCookies(), ConstUtil.TOKEN);
        if(StringUtil.isNotBlank(token)) {
            Sso sso = new Sso();
            sso.setToken(token);
            userService.delete(sso);
        }
        
        //删除token的Cookie凭证
        Cookie cookie = new Cookie(ConstUtil.TOKEN, UUID.randomUUID().toString().replaceFirst("-", ""));
        cookie.setPath("/");
//        cookie.setDomain(".jiucheng.org");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ConstUtil.getAdminUri("logout");
    }
    
    @GET("/admin/login.html")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        request.setAttribute("verifyHide", uuid);
        Cookie cookie = new Cookie("verifyHide", uuid);
        cookie.setPath("/admin/login.html");
//        cookie.setDomain(".jiucheng.org");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
        request.setAttribute("verifyHide", uuid);
        return ConstUtil.getAdminUri("login");
    }
    
    @POST("/admin/login.html")
    public String loginPost(HttpServletRequest request, HttpServletResponse response) {
        
        String verifyHide = request.getParameter("verifyHide");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        String cookieVerifyHide = getCookieValue(request.getCookies(), "verifyHide");
        
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        request.setAttribute("verifyHide", uuid);
        Cookie cookie = new Cookie("verifyHide", uuid);
        cookie.setPath("/admin/login.html");
//        cookie.setDomain(".jiucheng.org");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
        request.setAttribute("verifyHide", uuid);
        
        if(StringUtil.isBlank(cookieVerifyHide)) {
            request.setAttribute(ConstUtil.ERR, "*浏览器必须开启Cookie");
            return ConstUtil.getAdminUri("login");
        }
        
        if(StringUtil.isBlank(verifyHide) || StringUtil.isBlank(username) || StringUtil.isBlank(password)) {
            request.setAttribute(ConstUtil.ERR, "*各项不能为空");
            return ConstUtil.getAdminUri("login");
        }
        
        if(!cookieVerifyHide.equals(verifyHide)) {
            request.setAttribute(ConstUtil.ERR, "*非法请求");
            return ConstUtil.getAdminUri("login");
        }
        
        User user = new User();
        user.setWebappId(UserManage.getWebappId());
        user.setUid(username);
        List<User> useres = userService.list(user);
        if(useres.size() == 0) {
            request.setAttribute(ConstUtil.ERR, "*账号不存在");
            return ConstUtil.getAdminUri("login");
        }
        user = useres.get(0);
        if(!user.getPassword().equals(MD5Util.getMd5(password))) {
            request.setAttribute(ConstUtil.ERR, "*密码错误");
            return ConstUtil.getAdminUri("login");
        }
        
        //登陆成功
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        Sso sso = new Sso();
        sso.setUserId(user.getId());
        sso.setToken(token);
        Date now = new Date();
        sso.setCreateDatetime(now);
        sso.setModifyDatetime(now);
        userService.save(sso);
        cookie = new Cookie(ConstUtil.TOKEN, token);
        cookie.setPath("/");
//        cookie.setDomain(".jiucheng.org");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
        WebUtil.redirect("/admin/index.html");
        return ObjectUtil.getNull();
    }
    
    private String getCookieValue(Cookie[] cookies, String name) {
        if(ObjectUtil.isNull(cookies)) {
            return ObjectUtil.getNull();
        }
        for(Cookie cookie : cookies) {
            if(name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return ObjectUtil.getNull();
    }
}
