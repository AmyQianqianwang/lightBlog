package in.wenwen.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import in.wenwen.dto.QueryDto;
import in.wenwen.entity.ArticleType;
import in.wenwen.handler.AdminHandler;
import in.wenwen.out.JSONStringOut;
import in.wenwen.out.UserOut;
import in.wenwen.service.IArticleTypeService;
import in.wenwen.util.ConstUtil;
import in.wenwen.util.Err;
import in.wenwen.util.PageResult;
import in.wenwen.util.UserManage;

import org.jiucheng.ioc.annotation.Inject;
import org.jiucheng.util.ObjectUtil;
import org.jiucheng.util.StringUtil;
import org.jiucheng.web.annotation.Controller;
import org.jiucheng.web.annotation.Param;
import org.jiucheng.web.annotation.Controller.GET;
import org.jiucheng.web.annotation.Controller.POST;

@Controller(AdminHandler.class)
public class AdminArticleTypeController {
    
    @Inject
    private IArticleTypeService articleTypeService;
    
    @GET("/admin/article/type/index.html")
    public String index() {
        return ConstUtil.getAdminUri("article/type/index");
    }
    
    @GET(value = "/admin/article/type/page.json", out = UserOut.class)
    public PageResult<ArticleType> json(@Param QueryDto queryDto) {
        queryDto.setWebappId(UserManage.getWebappId());
        PageResult<ArticleType> pru = articleTypeService.page(queryDto);
        return pru;
    }
    
    @GET("/admin/article/type/add.html")
    public String add() {
        return ConstUtil.getAdminUri("article/type/add");
    }
    
    @POST("/admin/article/type/add.html")
    public String addPost(@Param ArticleType articleType, HttpServletRequest request) {
        articleType.setWebappId(UserManage.getWebappId());
        Date now = new Date();
        articleType.setCreateDatetime(now);
        articleType.setModifyDatetime(now);
        Long id = (Long) articleTypeService.save(articleType);
        articleType.setId(id);
        request.setAttribute("articleType", articleType);
        return ConstUtil.getAdminUri("article/type/add-success");
    }
    
    @GET(value = "/admin/article/type/delete.html", out = UserOut.class)
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
        articleTypeService.deleteUserByIds(UserManage.getWebappId(), ids);
        err.setErr("0");
        return err;
    }
    
    @GET("/admin/article/type/edit.html")
    public String edit(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isNotBlank(idStr) && idStr.matches("[0-9]+")) {
            Long id = Long.parseLong(idStr);
            ArticleType articleType = new ArticleType();
            articleType.setId(id);
            List<ArticleType> useres = articleTypeService.list(articleType);
            request.setAttribute("articleType", useres.get(0));
        }
        return ConstUtil.getAdminUri("article/type/edit");
    }
    
    @POST("/admin/article/type/edit.html")
    public String editPost(@Param ArticleType articleType, HttpServletRequest request) {
        if(ObjectUtil.isNotNull(articleType.getId())) {
            articleType.setModifyDatetime(new Date());
            articleTypeService.update(articleType);
            request.setAttribute("articleType", articleType);
        }
        return ConstUtil.getAdminUri("article/type/edit-success");
    }
    
    @GET("/admin/article/type/detail.html")
    public void detail() {
        
    }
    
    @GET(value = "/admin/article/type/list.json", out = JSONStringOut.class)
    public List<ArticleType> json() {
        ArticleType articleType = new ArticleType();
        articleType.setWebappId(UserManage.getWebappId());
        return articleTypeService.list(articleType);
    }
}
