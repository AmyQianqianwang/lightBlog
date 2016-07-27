package in.wenwen.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import in.wenwen.dto.QueryDto;
import in.wenwen.entity.Link;
import in.wenwen.handler.AdminHandler;
import in.wenwen.out.UserOut;
import in.wenwen.service.ILinkService;
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
public class AdminLinkController {
    
    @Inject
    private ILinkService linkService;
    
    @GET("/admin/link/index.html")
    public String index() {
        return ConstUtil.getAdminUri("link/index");
    }
    
    @GET(value = "/admin/link/page.json", out = UserOut.class)
    public PageResult<Link> json(@Param QueryDto queryDto) {
        queryDto.setWebappId(UserManage.getWebappId());
        PageResult<Link> pru = linkService.page(queryDto);
        return pru;
    }
    
    @GET("/admin/link/add.html")
    public String add() {
        return ConstUtil.getAdminUri("link/add");
    }
    
    @POST("/admin/link/add.html")
    public String addPost(@Param Link link, HttpServletRequest request) {
        if(link.getRemark() == null) {
            link.setRemark("");
        }
        link.setWebappId(UserManage.getWebappId());
        Date now = UserManage.getReceiveDatetime();
        link.setCreateDatetime(now);
        link.setModifyDatetime(now);
        Long id = (Long) linkService.save(link);
        link.setId(id);
        request.setAttribute("link", link);
        return ConstUtil.getAdminUri("link/add-success");
    }
    
    @GET(value = "/admin/link/delete.html", out = UserOut.class)
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
        linkService.deleteUserByIds(UserManage.getWebappId(), ids);
        err.setErr("0");
        return err;
    }
    
    @GET("/admin/link/edit.html")
    public String edit(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isNotBlank(idStr) && idStr.matches("[0-9]+")) {
            Long id = Long.parseLong(idStr);
            Link link = new Link();
            link.setWebappId(UserManage.getWebappId());
            link.setId(id);
            List<Link> linkes = linkService.list(link);
            request.setAttribute("link", linkes.get(0));
        }
        return ConstUtil.getAdminUri("link/edit");
    }
    
    @POST("/admin/link/edit.html")
    public String editPost(@Param Link link, HttpServletRequest request) {
        if(ObjectUtil.isNotNull(link.getId())) {
            link.setModifyDatetime(UserManage.getReceiveDatetime());
            linkService.update(link);
            request.setAttribute("link", link);
        }
        return ConstUtil.getAdminUri("link/edit-success");
    }
    
    @GET("/admin/link/detail.html")
    public void detail() {
        
    }
}
