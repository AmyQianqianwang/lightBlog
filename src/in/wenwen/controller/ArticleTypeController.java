package in.wenwen.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.wenwen.dto.ArticleDto;
import in.wenwen.dto.ArticleTypeDto;
import in.wenwen.dto.QueryDto;
import in.wenwen.entity.Article;
import in.wenwen.entity.ArticleType;
import in.wenwen.handler.IndexHandler;
import in.wenwen.out.JSONStringOut;
import in.wenwen.service.IArticleService;
import in.wenwen.service.IArticleTypeService;
import in.wenwen.util.ConstUtil;
import in.wenwen.util.Page;
import in.wenwen.util.UserManage;

import org.jiucheng.ioc.annotation.Inject;
import org.jiucheng.util.ObjectUtil;
import org.jiucheng.util.StringUtil;
import org.jiucheng.web.annotation.Controller;
import org.jiucheng.web.annotation.Controller.GET;

@Controller(IndexHandler.class)
public class ArticleTypeController {
    
    @Inject
    private IArticleTypeService articleTypeService;
    @Inject
    private IArticleService articleService;
    
    @GET(value="/article/type/json", out=JSONStringOut.class)
    public List<ArticleTypeDto> blogType() {
        return articleTypeService.getBlogTypeDto(UserManage.getWebappId());
    }
    
    @GET("/article/type")
    public String index(HttpServletRequest request) {
        List<ArticleTypeDto> articleTypes = articleTypeService.listSizeDesc(UserManage.getWebappId());
        request.setAttribute("articleTypes", articleTypes);
        return ConstUtil.getUri("article_type");
    }
    
    @GET("/article/type/([0-9]+)")
    public String typeId(String[] args, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(args[1]);
        ArticleType articleType = articleTypeService.get(ArticleType.class, id);
        if(ObjectUtil.isNull(articleType) || "T".equals(articleType.getIsHalt()) || !UserManage.getWebappId().equals(articleType.getWebappId())) {
            response.sendError(404);
            return ObjectUtil.getNull();
        }
        request.setAttribute("articleType", articleType);
        Article article = new Article();
        article.setWebappId(UserManage.getWebappId());
        article.setTypeId(id);
        article.setIsHalt("F");
        
        String pStr = request.getParameter("p");//当前页
        int p = 1;
        if(StringUtil.isNotBlank(pStr) && pStr.matches("[0-9]+")) {
            p = Integer.parseInt(pStr);
        }
        QueryDto queryDto = new QueryDto();
        queryDto.setWebappId(UserManage.getWebappId());
        queryDto.setPageIndex(p);
        queryDto.setTypeId(id);
        Page<ArticleDto> page = articleService.pageS(queryDto);
        page.setUrl("article/type/" + id + "?p={0}");
        request.setAttribute("page", page);
        return ConstUtil.getUri("article_type_id");
        
    }
}
