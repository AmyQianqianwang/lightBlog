package in.wenwen.handler;

import java.util.Date;
import java.util.List;

import in.wenwen.dto.UserDto;
import in.wenwen.dto.WebappDto;
import in.wenwen.entity.Domain;
import in.wenwen.entity.Sso;
import in.wenwen.entity.UserAuth;
import in.wenwen.entity.UserAuthCache;
import in.wenwen.out.ErrOut;
import in.wenwen.service.IDomainService;
import in.wenwen.service.ISsoService;
import in.wenwen.service.IUserAuthCacheService;
import in.wenwen.service.IUserAuthService;
import in.wenwen.util.ConstUtil;
import in.wenwen.util.Err;
import in.wenwen.util.UserManage;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.jiucheng.ioc.annotation.Component;
import org.jiucheng.ioc.annotation.Impl;
import org.jiucheng.ioc.annotation.Inject;
import org.jiucheng.util.ObjectUtil;
import org.jiucheng.util.StringUtil;
import org.jiucheng.web.WebWrapper;
import org.jiucheng.web.handler.DefaultHandler;
import org.jiucheng.web.handler.Out;
import org.jiucheng.web.util.WebUtil;

@Component
public class AdminHandler extends DefaultHandler {
    
    @Inject
    private ISsoService ssoService;
    @Inject
    private IDomainService domainService;
    @Inject
    private IUserAuthService userAuthService;
    @Inject
    private IUserAuthCacheService userAuthCacheService;
    @Impl(ErrOut.class)
    private Out out;
    
    @Override
    public boolean before(WebWrapper webWrapper) throws ServletException {
        super.before(webWrapper);
        UserManage.setReceiveDatetime(new Date());
        HttpServletRequest request = WebUtil.getRequest();
        String pathInfo = WebUtil.getPathInfo();
        String domain = ConstUtil.getURL(request);
        
        // 验证domain
        Domain d = domainService.getBySite(domain);
        Err err = new Err();
        if(ObjectUtil.isNull(d) || "T".equalsIgnoreCase(d.getIsHalt())) {
            err.setMsg(domain + ",域名未绑定或已停用!");
            out.invoke(webWrapper, err);
            return false;
        }
        // \验证domain
        
        // webapp
        
        // \webapp
        
        WebappDto webapp = new WebappDto();
        webapp.setId(d.getWebappId());
        webapp.setDomain(domain);
        webapp.setUrl(domain.concat(ConstUtil.getContext()));
        webapp.setContext(ConstUtil.getContext());
        webapp.setUri(pathInfo);
        request.setAttribute("webapp", webapp);
        UserManage.setWebappDto(webapp);
        
        // 后台登陆验证
        Cookie[] cookies = request.getCookies();
        Sso sso = ObjectUtil.getNull();
        if(ObjectUtil.isNotNull(cookies)) {
            for(Cookie cookie : cookies) {
                if(ConstUtil.TOKEN.equals(cookie.getName())) {
                    sso = new Sso();
                    sso.setToken(cookie.getValue());
                    List<Sso> ssoes = ssoService.list(sso);
                    if(ssoes.size() > 0) {
                        sso = ssoes.get(0);
                        //验证有没有过期, 有效期30分钟
                        Date now = new Date();
                        if(now.getTime() - sso.getModifyDatetime().getTime() < 1000 * 60 * 30) {
                            sso.setModifyDatetime(now);
                            ssoService.update(sso);//验证成功，同时更新最后登陆时间
                        }else {
                            sso = ObjectUtil.getNull();
                        }
                    }else {
                        sso = ObjectUtil.getNull();
                    }
                    if(ObjectUtil.isNotNull(sso)) {
                        UserDto userDto = new UserDto();
                        userDto.setWebappId(UserManage.getWebappId());
                        userDto.setId(sso.getUserId());
                        List<UserDto> useres = ssoService.list(userDto);
                        if(useres.size() >0) {
                            userDto = useres.get(0);
                            userDto.setToken(sso.getToken());
                        }else {
                            userDto = ObjectUtil.getNull();
                        }
                        webapp.setUser(userDto);
                    }
                    break;
                }
            }
        }
        // \后台登陆验证
        
        // 权限验证
        UserAuth userAuth = userAuthService.getByWebappIdUri(d.getWebappId(), pathInfo);
        boolean has = false;
        if(ObjectUtil.isNotNull(userAuth) && !"T".equalsIgnoreCase(userAuth.getIsHalt())) {
            Long userId = 0L;
            Long webappId = 0L;
            if(ObjectUtil.isNotNull(UserManage.getUserDto())) {
                userId = UserManage.getUserDto().getId();
                webappId = UserManage.getWebappId();
            }
            UserAuthCache userAuthCache = userAuthCacheService.getByWebappIdUserId(webappId, userId);
            if(ObjectUtil.isNotNull(userAuthCache)) {
                String cacheString = userAuthCache.getCacheString();
                if(userId.equals(0L) && "*".equals(cacheString)) {
                    has = true;
                }else if(StringUtil.isNotBlank(cacheString)) {
                    cacheString = ",".concat(cacheString).concat(",");
                    if(cacheString.indexOf( ",".concat( Long.toString(userAuth.getId()).concat(",") ) ) != -1) {
                        has = true;
                    }
                }
            }
        }else {
            has = true;
        }
        if(!has) {
            err.setMsg("[" + userAuth.getDescript() + "]" + "权限不足!");
            out.invoke(webWrapper, err);
            return false;
        }
        // \权限验证
        
        if(ObjectUtil.isNotNull(sso) && pathInfo.equals("/admin/login.html")) {
            WebUtil.redirect("/admin/index.html?fr=".concat(webapp.getUri()));
            return false;
        }
        if(ObjectUtil.isNull(sso) && pathInfo.startsWith("/admin") && !pathInfo.equals("/admin/login.html")) {
            WebUtil.redirect("/admin/login.html");
            return false;
        }
        return true;
    }
}
