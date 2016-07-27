package in.wenwen.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import in.wenwen.entity.User;
import in.wenwen.handler.AdminHandler;
import in.wenwen.service.IUserService;
import in.wenwen.util.ConstUtil;
import in.wenwen.util.MD5Util;
import in.wenwen.util.UserManage;

import org.jiucheng.ioc.annotation.Inject;
import org.jiucheng.util.StringUtil;
import org.jiucheng.web.annotation.Controller;
import org.jiucheng.web.annotation.Controller.GET;
import org.jiucheng.web.annotation.Controller.POST;

@Controller(AdminHandler.class)
public class AdminMeController {
    
    @Inject
    private IUserService userService;
    
    @GET("/admin/me/edit.html")
    public String edit() {
        return ConstUtil.getAdminUri("me/edit");
    }
    
    @POST("/admin/me/edit.html")
    public String editPost(HttpServletRequest request) {
        String ypassword = request.getParameter("ypassword");
        String xpassword = request.getParameter("xpassword");
        String rs = ConstUtil.getAdminUri("me/edit");
        if(StringUtil.isBlank(xpassword) || StringUtil.isBlank(ypassword)) {
            request.setAttribute(ConstUtil.ERR, "*密码不能为空");
            return rs;
        }
        
        User user = new User();
        user.setId(UserManage.getWebappDto().getUser().getId());
        List<User> useres = userService.list(user);
        if(useres.size() > 0) {
            user = useres.get(0);
        }else {
            request.setAttribute(ConstUtil.ERR, "*账号已不存在");
            return rs;
        }
        
        if(!MD5Util.getMd5(ypassword).equals(user.getPassword())) {
            request.setAttribute(ConstUtil.ERR, "*原密码错误");
            return rs;
        }
        
        user = new User();
        user.setId(UserManage.getUserDto().getId());
        user.setModifyDatetime(new Date());
        user.setPassword(MD5Util.getMd5(xpassword));
        userService.update(user);
        
        request.setAttribute(ConstUtil.ERR, "*修改成功");
        return rs;
    }
    
    @GET("/admin/me/nick/edit.html")
    public String nickEdit(HttpServletRequest request) {
        request.setAttribute("nick", UserManage.getUserNick());
        return ConstUtil.getAdminUri("me/nick/edit");
    }
    
    @POST("/admin/me/nick/edit.html")
    public String nickEditPost(HttpServletRequest request) {
        String nick = request.getParameter("nick");
        if(StringUtil.isNotBlank(nick)) {
            User user = new User();
            user.setId(UserManage.getUserDto().getId());
            user.setNick(nick.trim());
            user.setModifyDatetime(UserManage.getReceiveDatetime());
            userService.update(user);
        }
        return ConstUtil.getAdminUri("me/nick/edit-success");
    }
}
