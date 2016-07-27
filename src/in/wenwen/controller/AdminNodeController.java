package in.wenwen.controller;

import in.wenwen.handler.AdminHandler;

import org.jiucheng.web.annotation.Controller;
import org.jiucheng.web.annotation.Controller.GET;
import org.jiucheng.web.util.WebUtil;

@Controller(AdminHandler.class)
public class AdminNodeController {
    
    @GET("/admin/node/index.html")
    public void index() {
        WebUtil.dispatcher("/WEB-INF/admin/node/index");
    }
}
