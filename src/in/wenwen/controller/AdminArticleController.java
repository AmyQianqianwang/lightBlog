package in.wenwen.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import in.wenwen.dto.ArticleDto;
import in.wenwen.dto.QueryDto;
import in.wenwen.entity.Article;
import in.wenwen.handler.AdminHandler;
import in.wenwen.out.UserOut;
import in.wenwen.service.IArticleService;
import in.wenwen.util.ConstUtil;
import in.wenwen.util.Err;
import in.wenwen.util.PageResult;
import in.wenwen.util.UserManage;

import org.jiucheng.exception.UncheckedException;
import org.jiucheng.ioc.annotation.Inject;
import org.jiucheng.util.ObjectUtil;
import org.jiucheng.util.StringUtil;
import org.jiucheng.web.annotation.Controller;
import org.jiucheng.web.annotation.Param;
import org.jiucheng.web.annotation.Controller.GET;
import org.jiucheng.web.annotation.Controller.POST;

@Controller(AdminHandler.class)
public class AdminArticleController {
    
    @Inject
    private IArticleService articleService;
    
    @GET("/admin/article/index.html")
    public String index() {
        return ConstUtil.getAdminUri("article/index");
    }
    
    @GET(value = "/admin/article/page.json", out = UserOut.class)
    public PageResult<ArticleDto> json(@Param QueryDto queryDto) {
        queryDto.setWebappId(UserManage.getWebappId());
        PageResult<ArticleDto> pru = articleService.pageDto(queryDto);
        return pru;
    }
    
    @GET("/admin/article/add.html")
    public String add() {
        return ConstUtil.getAdminUri("article/add");
    }
    
    @POST("/admin/article/add.html")
    public String addPost(@Param Article article, HttpServletRequest request) {
        article.setWebappId(UserManage.getWebappId());
        article.setUserId(UserManage.getUserDto().getId());
        article.setModifyDatetime(UserManage.getReceiveDatetime());
        article.setCreateDatetime(UserManage.getReceiveDatetime());
        article = articleService.saveOrUpdateArticle(article);
        request.setAttribute("article", article);
        return ConstUtil.getAdminUri("article/add-success");
    }
    
    @GET(value = "/admin/article/delete.html", out = UserOut.class)
    public Err delete(HttpServletRequest request) {
        Err err = new Err("删除失败");
        List<Long> ids = new ArrayList<Long>();
        String id;
        for(int i = 0; i < 100; i ++) {
            id = request.getParameter("id[" + i + "]");
            if(StringUtil.isBlank(id)) {
                break;
            }
            if(id.matches("[0-9]+")) {
                ids.add(Long.parseLong(id));
            }else {
                ids = new ArrayList<Long>();
                break;
            }
        }
        if(ids.size() == 0) {
            return err;
        }
        articleService.deleteUserByIds(UserManage.getWebappId(), ids);
        err.setErr("0");
        return err;
    }
    
    @GET("/admin/article/edit.html")
    public String edit(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isNotBlank(idStr) && idStr.matches("[0-9]+")) {
            Long id = Long.parseLong(idStr);
            Article article = new Article();
            article.setId(id);
            List<Article> useres = articleService.list(article);
            if(useres.size() == 0 || !UserManage.getWebappId().equals(useres.get(0).getWebappId())) {
                throw new UncheckedException("非法操作");
            }
            request.setAttribute("article", useres.get(0));
        }
        return ConstUtil.getAdminUri("article/edit");
    }
    
    @POST("/admin/article/edit.html")
    public String editPost(@Param Article article, HttpServletRequest request) {
        if(ObjectUtil.isNotNull(article.getId())) {
            article.setModifyDatetime(UserManage.getReceiveDatetime());
            articleService.saveOrUpdateArticle(article);
            request.setAttribute("article", article);
        }
        return ConstUtil.getAdminUri("article/edit-success");
    }
    
    @GET("/admin/article/detail.html")
    public void detail() {
        
    }
}
