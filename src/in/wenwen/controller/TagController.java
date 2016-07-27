package in.wenwen.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import in.wenwen.entity.Tag;
import in.wenwen.handler.IndexHandler;
import in.wenwen.out.JSONStringOut;
import in.wenwen.service.ITagService;
import in.wenwen.util.ConstUtil;
import in.wenwen.util.UserManage;

import org.jiucheng.ioc.annotation.Inject;
import org.jiucheng.web.annotation.Controller;
import org.jiucheng.web.annotation.Controller.GET;

@Controller(IndexHandler.class)
public class TagController {
    
    @Inject
    private ITagService tagService;
    
    @GET("/tag")
    public String index(HttpServletRequest request) {
        List<Tag> tags = tagService.listTagDescSize(UserManage.getWebappId());
        request.setAttribute("tags", tags);
        return ConstUtil.getUri("tag");
    }
    
    @GET(value="/tag/top/json", out = JSONStringOut.class)
    public List<Tag> tagTopJson() {
        return tagService.getTopJson(UserManage.getWebappId(), 12L);
    }
}
