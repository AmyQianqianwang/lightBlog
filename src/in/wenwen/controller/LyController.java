package in.wenwen.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import in.wenwen.dto.LyDto;
import in.wenwen.entity.Article;
import in.wenwen.entity.Ly;
import in.wenwen.handler.IndexHandler;
import in.wenwen.out.JSONStringOut;
import in.wenwen.service.ILyService;
import in.wenwen.util.ConstUtil;
import in.wenwen.util.UserManage;

import org.jiucheng.ioc.annotation.Inject;
import org.jiucheng.util.ObjectUtil;
import org.jiucheng.util.StringUtil;
import org.jiucheng.web.annotation.Controller;
import org.jiucheng.web.annotation.Controller.GET;
import org.jiucheng.web.annotation.Controller.POST;
import org.jiucheng.web.annotation.Param;
import org.jiucheng.web.util.WebUtil;

@Controller(IndexHandler.class)
public class LyController {
    
    @Inject
    private ILyService lyService;
    
    @GET(value="/ly/new/json", out=JSONStringOut.class)
    public List<Ly> newJson() {
        return lyService.getNew(UserManage.getWebappId(), 12L);
    }
    
    @GET(value="/article/([0-9]+)/ly", out=JSONStringOut.class)
    public List<LyDto> articleLy(String[] args){
        return lyService.getArticleLy(UserManage.getWebappId(), Long.parseLong(args[1]), null);
    }
    
    @POST("/article/ly")
    public void articleLy(String[] args, @Param Ly ly, HttpServletResponse response) {
        if(ObjectUtil.isNull(ly.getArticleId())) {
            WebUtil.redirect(ConstUtil.getRedirectUri("index").concat("?fr=").concat(args[0]));
            return;
        }
        if(StringUtil.isBlank(ly.getContent()) || StringUtil.isBlank(ly.getAuthor())) {
            WebUtil.redirect(ConstUtil.getRedirectUri("article/") + ly.getArticleId());
            return;
        }
        Article article = lyService.get(Article.class, ly.getArticleId());
        if(ObjectUtil.isNull(article) || !UserManage.getWebappId().equals(article.getWebappId())) {
            WebUtil.redirect(ConstUtil.getRedirectUri("index").concat("?fr=").concat(args[0]));
            return;
        }
        lyService.saveLyUpdateArticle(ly, article);
        WebUtil.redirect(ConstUtil.getRedirectUri("article/") + ly.getArticleId());
    }
}
