package in.wenwen.handler;

import java.util.Date;
import java.util.List;

import in.wenwen.dto.UserDto;
import in.wenwen.dto.WebappDto;
import in.wenwen.entity.Domain;
import in.wenwen.entity.Sso;
import in.wenwen.entity.UserAuth;
import in.wenwen.entity.Webapp;
import in.wenwen.out.ErrOut;
import in.wenwen.service.IDomainService;
import in.wenwen.service.ILogUriService;
import in.wenwen.service.ISsoService;
import in.wenwen.service.IUserAuthService;
import in.wenwen.service.IWebappService;
import in.wenwen.util.ConstUtil;
import in.wenwen.util.Err;
import in.wenwen.util.UserManage;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.jiucheng.ioc.annotation.Impl;
import org.jiucheng.ioc.annotation.Inject;
import org.jiucheng.util.ObjectUtil;
import org.jiucheng.web.WebWrapper;
import org.jiucheng.web.handler.DefaultHandler;
import org.jiucheng.web.handler.Out;
import org.jiucheng.web.util.WebUtil;

public class IndexHandler extends DefaultHandler {
    
    @Inject
    private IWebappService webappService;
    @Inject
    private IDomainService domainService;
    @Inject
    private ISsoService ssoService;
    @Impl(ErrOut.class)
    private Out out;
    @Inject
    private IUserAuthService userAuthService;
    @Inject
    private ILogUriService logUriService;
    
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
            err.setMsg(domain + ",域名未绑定或已停用！");
            out.invoke(webWrapper, err);
            return false;
        }
        // \验证domain
        
        // webapp
        Webapp app = webappService.get(Webapp.class, d.getWebappId());
        if(ObjectUtil.isNull(app) || "T".equalsIgnoreCase(app.getIsHalt())) {
            err.setMsg("站点不存在！");
            out.invoke(webWrapper, err);
            return false;
        }
        // \webapp
        
        WebappDto webapp = new WebappDto();
        webapp.setDescript(app.getDescript());
        webapp.setName(app.getName());
        webapp.setId(d.getWebappId());
        webapp.setMain(d.getMain());
        webapp.setDomain(domain);
        webapp.setTemplet(d.getTemplet());
        webapp.setUrl(domain.concat(ConstUtil.getContext()));
        webapp.setContext(ConstUtil.getContext());
        webapp.setUri(pathInfo);
        request.setAttribute("webapp", webapp);
        UserManage.setWebappDto(webapp);
        
        // 查找不允许访问
        UserAuth userAuth = userAuthService.getByWebappIdUri(d.getWebappId(), pathInfo);
        if(ObjectUtil.isNotNull(userAuth) && "F".equals(userAuth.getIsHalt())) {
            WebUtil.redirect(webapp.getMain().concat("?fr=").concat(pathInfo));
            return false;
        }
        // \查找不允许访问
        
        // 记录访问日志
        logUriService.saveLogUri(pathInfo, WebUtil.getIp());
        // \记录访问日志
        
        // 用户登录
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
                        if(UserManage.getReceiveDatetime().getTime() - sso.getModifyDatetime().getTime() < 1000 * 60 * 30) {
                            sso.setModifyDatetime(UserManage.getReceiveDatetime());
                            ssoService.update(sso);//验证成功，同时更新最后登陆时间
                            
                            UserDto userDto = new UserDto();
                            userDto.setWebappId(UserManage.getWebappId());
                            userDto.setId(sso.getUserId());
                            List<UserDto> useres = ssoService.list(userDto);
                            if(useres.size() >0) {
                                userDto = useres.get(0);
                                userDto.setToken(sso.getToken());
                                webapp.setUser(userDto);
                            }
                        }
                    }
                }
            }
        }
        // \用户登录
        return true;
    }
}
