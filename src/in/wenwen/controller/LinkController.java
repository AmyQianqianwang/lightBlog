package in.wenwen.controller;

import in.wenwen.entity.Link;
import in.wenwen.handler.IndexHandler;
import in.wenwen.out.JSONStringOut;
import in.wenwen.service.ILinkService;
import in.wenwen.util.ConstUtil;
import in.wenwen.util.UserManage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jiucheng.ioc.annotation.Inject;
import org.jiucheng.util.StringUtil;
import org.jiucheng.web.annotation.Controller;
import org.jiucheng.web.annotation.Controller.GET;
import org.jiucheng.web.util.WebUtil;

@Controller(IndexHandler.class)
public class LinkController {
    
    @Inject
    private ILinkService linkService;
    
    @GET("/link")
    public String all(HttpServletRequest request) {
        List<Link> linkes = linkService.listNew(UserManage.getWebappId());
        request.setAttribute("linkes", linkes);
        return ConstUtil.getUri("link");
    }
    
    @GET(value="/link/new/fr/json", out = JSONStringOut.class)
    public List<Link> newFr() {
        return linkService.getNewFr(UserManage.getWebappId(), 12L);
    }
    
    @GET("/link/to/(.+)")
    public void to(String[] args) {
        Link link = new Link();
        link.setWebappId(UserManage.getWebappId());
        link.setUid(args[1]);
        List<Link> linkes = linkService.list(link);
        String url = UserManage.getWebappDto().getUrl();
        if(linkes.size() > 0) {
            link = linkes.get(0);
            if(StringUtil.isNotBlank(link.getUrl())) {
                url = link.getUrl();
                Long id = link.getId();
                Long to = link.getTo();
                link = new Link();
                link.setId(id);
                link.setTo(to + 1);
                linkService.update(link);
            }
        }
        WebUtil.redirect(url);
    }
}
