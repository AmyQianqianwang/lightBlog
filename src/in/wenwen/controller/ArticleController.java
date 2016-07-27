package in.wenwen.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.wenwen.dto.ArticleDto;
import in.wenwen.dto.BlogDto;
import in.wenwen.entity.Article;
import in.wenwen.entity.ArticleType;
import in.wenwen.entity.User;
import in.wenwen.handler.IndexHandler;
import in.wenwen.out.JSONStringOut;
import in.wenwen.service.IArticleService;
import in.wenwen.util.ConstUtil;
import in.wenwen.util.UserManage;

import org.jiucheng.ioc.annotation.Inject;
import org.jiucheng.util.ObjectUtil;
import org.jiucheng.util.StringUtil;
import org.jiucheng.web.annotation.Controller;
import org.jiucheng.web.annotation.Controller.GET;
import org.jiucheng.web.util.WebUtil;

@Controller(IndexHandler.class)
public class ArticleController {
    
    @Inject
    private IArticleService articleService;
    
    @GET(value="/article/rnd/json", out=JSONStringOut.class)
    public List<BlogDto> rnd() {
        return articleService.getRnd(UserManage.getWebappId(), 12);
    }

    // -------------------------------
    @GET("/blog/view/([0-9]+)")
    public void blogView(String[] args) {
        WebUtil.redirect("/index?fr=".concat(args[0]));
    }
    
    @GET("/blog/view")
    public void blogView2(String[] args) {
        WebUtil.redirect("/index?fr=".concat(args[0]));
    }
    
    @GET("/blog/clazz")
    public void blogClazz(String[] args) {
        WebUtil.redirect("/index?fr=".concat(args[0]));
    }
    
    @GET("/blog/clazz/([0-9]+)")
    public void blogClass(String[] args) {
        WebUtil.redirect("/index?fr=".concat(args[0]));
    }
    
    @GET("/search/tag")
    public void searchTag(String[] args) {
        WebUtil.redirect("/index?fr=".concat(args[0]));
    }
    // -----------------------------
    
    @GET("/article/([0-9]+)")
    public String blog(String[] args, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String arg1 = args[1];
        ArticleDto article = articleService.get(ArticleDto.class, Long.parseLong(arg1));
        if(article == null || !UserManage.getWebappId().equals(article.getWebappId())) {
            response.sendError(404);
            return null;
        }
        article.setClick(article.getClick() + 1);
        Article a = new Article();
        a.setId(article.getId());
        a.setClick(article.getClick());
        articleService.update(a);
        User user = articleService.get(User.class, article.getUserId());
        article.setUserName("匿名");
        if(ObjectUtil.isNotNull(user)) {
            article.setUserName(user.getNick());
        }
        // 设置文章分类
        Long typeId = article.getTypeId();
        if(typeId != null && typeId.compareTo(Long.valueOf(0L)) > 0) {
            ArticleType articleType = articleService.get(ArticleType.class, typeId);
            if(ObjectUtil.isNotNull(articleType)) {
                article.setTypeName(articleType.getName());
            }else {
                article.setTypeId(Long.valueOf(0L));
                article.setTypeName("无");
            }
        }else {
            article.setTypeId(Long.valueOf(0L));
            article.setTypeName("无");
        }
        // \设置文章分类
        
        request.setAttribute("article", article);
        List<String> tags = new ArrayList<String>();
        if(StringUtil.isNotBlank(article.getTags())) {
            tags = Arrays.asList(article.getTags().split(","));
        }
        request.setAttribute("tags", tags);
        
        request.setAttribute("prev", articleService.getPrev(UserManage.getWebappId(), article.getId()));
        request.setAttribute("next", articleService.getNext(UserManage.getWebappId(), article.getId()));
        return ConstUtil.getUri("article");
    }
}
