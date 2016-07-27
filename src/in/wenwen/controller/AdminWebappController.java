package in.wenwen.controller;

import javax.servlet.http.HttpServletRequest;

import in.wenwen.entity.Webapp;
import in.wenwen.handler.AdminHandler;
import in.wenwen.service.IWebappService;
import in.wenwen.util.ConstUtil;
import in.wenwen.util.UserManage;

import org.jiucheng.ioc.annotation.Inject;
import org.jiucheng.util.StringUtil;
import org.jiucheng.web.annotation.Controller;
import org.jiucheng.web.annotation.Param;
import org.jiucheng.web.annotation.Controller.GET;
import org.jiucheng.web.annotation.Controller.POST;

@Controller(AdminHandler.class)
public class AdminWebappController {
    
    @Inject
    private IWebappService webappService;
    
    @GET("/admin/webapp/edit.html")
    public String edit(HttpServletRequest request) {
        Webapp zhan = webappService.get(Webapp.class, UserManage.getWebappId());
        request.setAttribute("zhan", zhan);
        return ConstUtil.getAdminUri("webapp/edit");
    }
    
    @POST("/admin/webapp/edit.html")
    public String editPost(@Param Webapp webapp, HttpServletRequest request) {
        String name = webapp.getName();
        String descript = webapp.getDescript();
        if(StringUtil.isNotBlank(name) && StringUtil.isNotBlank(descript)) {
            webapp = new Webapp();
            webapp.setId(UserManage.getWebappId());
            webapp.setName(name);
            webapp.setDescript(descript);
            webapp.setModifyDatetime(UserManage.getReceiveDatetime());
            webappService.update(webapp);
        }
        return ConstUtil.getAdminUri("webapp/edit-success");
    }
}
