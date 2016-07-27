package in.wenwen.controller;

import javax.servlet.http.HttpServletRequest;

import in.wenwen.dto.ArticleDto;
import in.wenwen.dto.QueryDto;
import in.wenwen.handler.IndexHandler;
import in.wenwen.service.IArticleService;
import in.wenwen.service.ILinkService;
import in.wenwen.service.ITagService;
import in.wenwen.util.ConstUtil;
import in.wenwen.util.Page;
import in.wenwen.util.UserManage;

import org.jiucheng.ioc.annotation.Inject;
import org.jiucheng.util.StringUtil;
import org.jiucheng.web.annotation.Controller;
import org.jiucheng.web.annotation.Controller.GET;
import org.jiucheng.web.util.WebUtil;

@Controller(IndexHandler.class)
public class IndexController {
    
    @Inject
    private IArticleService articleService;
    @Inject
    private ILinkService linkService;
    @Inject
    private ITagService tagService;
    
    @GET("/default")
    public String default0() {
        return ConstUtil.getUri("default");
    }
    
    @GET("/index")
    public String index(HttpServletRequest request) {
        String pStr = request.getParameter("p");//当前页
        int p = 1;
        if(StringUtil.isNotBlank(pStr) && pStr.matches("[0-9]+")) {
            p = Integer.parseInt(pStr);
        }
        QueryDto queryDto = new QueryDto();
        queryDto.setWebappId(UserManage.getWebappId());
        queryDto.setPageIndex(p);
        String t = request.getParameter("t");
        if(StringUtil.isBlank(t)) {
            Page<ArticleDto> page = articleService.pageS(queryDto);
            page.setUrl("index?p={0}");
            request.setAttribute("page", page);
        }else {
            queryDto.setT(t);
            Page<ArticleDto> page = tagService.pageT(queryDto);
            page.setUrl("index?t=" + queryDto.getT() + "&p={0}");
            request.setAttribute("page", page);
        }
        return ConstUtil.getUri("index");
    }
    
    @GET("/")
    public void index(String[] args, HttpServletRequest request) {
        String fr = request.getParameter("fr");
        if(StringUtil.isNotBlank(fr)) {
            linkService.updateJiaFr(UserManage.getWebappId(), fr);
        }
        WebUtil.redirect(UserManage.getMain().concat("?fr=").concat(args[0]));
    }
}
