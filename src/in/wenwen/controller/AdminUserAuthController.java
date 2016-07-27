package in.wenwen.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import in.wenwen.dto.QueryDto;
import in.wenwen.entity.UserAuth;
import in.wenwen.handler.AdminHandler;
import in.wenwen.out.UserOut;
import in.wenwen.service.IUserAuthService;
import in.wenwen.service.IUserService;
import in.wenwen.util.ConstUtil;
import in.wenwen.util.Err;
import in.wenwen.util.PageResult;
import in.wenwen.util.UserManage;

import org.jiucheng.exception.UncheckedException;
import org.jiucheng.ioc.annotation.Inject;
import org.jiucheng.util.ObjectUtil;
import org.jiucheng.util.StringUtil;
import org.jiucheng.web.annotation.Controller;
import org.jiucheng.web.annotation.Controller.GET;
import org.jiucheng.web.annotation.Controller.POST;
import org.jiucheng.web.annotation.Param;

@Controller(AdminHandler.class)
public class AdminUserAuthController {
    
    @Inject
    private IUserService userService;
    @Inject
    private IUserAuthService userAuthService;
    
    @GET("/admin/user/auth/index.html")
    public String index() {
        return ConstUtil.getAdminUri("user/auth/index");
    }
    
    @GET(value = "/admin/user/auth/page.json", out = UserOut.class)
    public PageResult<UserAuth> json(@Param QueryDto queryDto, HttpServletRequest request) {
        queryDto.setWebappId(UserManage.getWebappId());
        PageResult<UserAuth> pru = userAuthService.page(queryDto);
        return pru;
    }
    
    @GET(value = "/admin/user/auth/delete.html", out = UserOut.class)
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
        userAuthService.deleteByIds(UserManage.getWebappId(), ids);
        err.setErr("0");
        return err;
    }
    
    @GET("/admin/user/auth/edit.html")
    public String edit(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isNotBlank(idStr) && idStr.matches("[0-9]+")) {
            Long id = Long.parseLong(idStr);
            UserAuth userAuth = userAuthService.get(id);
            if(ObjectUtil.isNull(userAuth) || userAuth.getWebappId().equals(UserManage.getWebappId())) {
                throw new UncheckedException("非法操作");
            }
            request.setAttribute("userAuth", userAuthService.get(id));
        }
        return ConstUtil.getAdminUri("user/auth/edit");
    }
    
    @POST("/admin/user/auth/edit.html")
    public String editPost(@Param UserAuth userAuth, HttpServletRequest request) {
        if(ObjectUtil.isNotNull(userAuth.getId())) {
            userAuth.setModifyDatetime(new Date());
            userService.update(userAuth);
            request.setAttribute("userAuth", userAuth);
        }
        return ConstUtil.getAdminUri("user/auth/edit-success");
    }
    
    @GET("/admin/user/auth/add.html")
    public String add() {
        return ConstUtil.getAdminUri("user/auth/add");
    }
    
    @POST("/admin/user/auth/add.html")
    public String addPost(@Param UserAuth userAuth, HttpServletRequest request) {
        Enumeration<String> objs = request.getParameterNames();
        while (objs.hasMoreElements()) {
            Object object = (Object) objs.nextElement();
            System.out.println(object.toString() + ","
                    + request.getParameter(object.toString()));
        }
        
        userAuth.setWebappId(UserManage.getWebappId());
        Date now = new Date();
        userAuth.setCreateDatetime(now);
        userAuth.setModifyDatetime(now);
        Long id = (Long) userService.save(userAuth);
        userAuth.setId(id);
        request.setAttribute("userAuth", userAuth);
        return ConstUtil.getAdminUri("user/auth/add-success");
    }
    
    @GET("/admin/user/auth/detail.html")
    public void detail() {
        
    }
}
  