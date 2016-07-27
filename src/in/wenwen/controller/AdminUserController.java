package in.wenwen.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import in.wenwen.dto.QueryDto;
import in.wenwen.dto.UserAuthDto;
import in.wenwen.entity.User;
import in.wenwen.entity.UserAuthCache;
import in.wenwen.handler.AdminHandler;
import in.wenwen.out.UserOut;
import in.wenwen.service.IUserAuthCacheService;
import in.wenwen.service.IUserAuthService;
import in.wenwen.service.IUserService;
import in.wenwen.util.ConstUtil;
import in.wenwen.util.Err;
import in.wenwen.util.MD5Util;
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
public class AdminUserController {
    
    @Inject
    private IUserService userService;
    @Inject
    private IUserAuthService userAuthService;
    @Inject
    private IUserAuthCacheService userAuthCacheService;

    @GET("/admin/user/index.html")
    public String index() {
        return ConstUtil.getAdminUri("user/index");
    }
    
    @GET(value = "/admin/user/page.json", out = UserOut.class)
    public PageResult<User> json(@Param QueryDto queryDto, HttpServletRequest request) {
//        Enumeration<Object> objs = request.getParameterNames();
//        while (objs.hasMoreElements()) {
//            Object object = (Object) objs.nextElement();
//            System.out.println(object.toString() + "," + request.getParameter(object.toString()));
//        }
        /*
         id,
        limit,30
        startDate,
        start,0
        _,1435371280178
        endDate,
        stuName,
        pageIndex,0
         */
        queryDto.setWebappId(UserManage.getWebappId());
        PageResult<User> pru = userService.page(queryDto);
        return pru;
    }
    
    @GET(value = "/admin/user/delete.html", out = UserOut.class)
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
        userService.deleteUserByIds(UserManage.getWebappId(), ids);
        err.setErr("0");
        return err;
    }
    
    @GET("/admin/user/edit.html")
    public String edit(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isNotBlank(idStr) && idStr.matches("[0-9]+")) {
            Long id = Long.parseLong(idStr);
            User user = new User();
            user.setId(id);
            List<User> useres = userService.list(user);
            request.setAttribute("user", useres.get(0));
        }
        return ConstUtil.getAdminUri("user/edit");
    }
    
    @POST("/admin/user/edit.html")
    public String editPost(@Param User user, HttpServletRequest request) {
        if(ObjectUtil.isNotNull(user.getId())) {
            userService.update(user);
            request.setAttribute("user", user);
        }
        return ConstUtil.getAdminUri("user/edit-success");
    }
    
    @GET("/admin/user/add.html")
    public String add() {
        return ConstUtil.getAdminUri("user/add");
    }
    
    @POST("/admin/user/add.html")
    public String addPost(@Param User user, HttpServletRequest request) {
        Enumeration<String> objs = request.getParameterNames();
        while (objs.hasMoreElements()) {
            Object object = (Object) objs.nextElement();
            System.out.println(object.toString() + ","
                    + request.getParameter(object.toString()));
        }
        user.setWebappId(UserManage.getWebappId());
        user.setPassword(MD5Util.getMd5(user.getPassword()));
        Date now = new Date();
        user.setCreateDatetime(now);
        user.setModifyDatetime(now);
        Long id = (Long) userService.save(user);
        user.setId(id);
        request.setAttribute("user", user);
        return ConstUtil.getAdminUri("user/add-success");
    }
    
    @GET("/admin/user/auth.html")
    public String userAuth(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isNotBlank(idStr) && idStr.matches("[0-9]+")) {
            Long id = Long.parseLong(idStr);
            User user = userService.get(User.class, id);
            if(!user.getWebappId().equals(UserManage.getWebappId())) {
                throw new UncheckedException("非法操作");
            }
            user.setPassword("");
            request.setAttribute("user", user);
            UserAuthDto userAuthDto = new UserAuthDto();
            userAuthDto.setWebappId(UserManage.getWebappId());
            List<UserAuthDto> userAuthes = userAuthService.list(userAuthDto);
            UserAuthCache userAuthCache = userAuthCacheService.getByWebappIdUserId(UserManage.getWebappId(), id);
            if(ObjectUtil.isNotNull(userAuthCache) && StringUtil.isNotBlank(userAuthCache.getCacheString())) {
                String cacheString = "," + userAuthCache.getCacheString() + ",";
                for(UserAuthDto uad : userAuthes) {
                    if(cacheString.indexOf(uad.getId().toString()) != -1) {
                        uad.setChecked("checked");
                    }
                }
            }
            request.setAttribute("userAuthes", userAuthes);
        }
        return ConstUtil.getAdminUri("user/auth");
    }
    
    @POST("/admin/user/auth.html")
    public String userAuthPost(HttpServletRequest request) {
        Date now = new Date();
        String idStr = request.getParameter("id");
        Long userId = Long.parseLong(idStr);
        String[] auth = request.getParameterValues("auth");
        String authString = "";
        if(ObjectUtil.isNotNull(auth)) {
            authString = Arrays.toString(auth);
            authString = authString.substring(1, authString.length() - 1).replaceAll(" ", "");
        }
        UserAuthCache userAuthCache = userAuthCacheService.getByWebappIdUserId(UserManage.getWebappId(), userId);
        if(ObjectUtil.isNotNull(userAuthCache)) {
            //update
            userAuthCache.setCacheString(authString);
            userAuthCache.setModifyDatetime(now);
            userAuthCacheService.update(userAuthCache);
        }else {
            userAuthCache = new UserAuthCache();
            userAuthCache.setWebappId(UserManage.getWebappId());
            userAuthCache.setUserId(userId);
            userAuthCache.setCacheString(authString);
            userAuthCache.setCreateDatetime(now);
            userAuthCache.setModifyDatetime(now);
            userAuthCacheService.save(userAuthCache);
            request.setAttribute("id", userId);
        }
        return ConstUtil.getAdminUri("user/auth-success");
    }
    
    @GET("/admin/user/detail.html")
    public void detail() {
        
    }
}
  