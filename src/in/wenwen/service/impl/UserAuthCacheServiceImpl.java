package in.wenwen.service.impl;

import java.util.List;

import in.wenwen.entity.UserAuthCache;
import in.wenwen.service.IUserAuthCacheService;

import org.jiucheng.aop.Aop;
import org.jiucheng.ioc.annotation.Service;
import org.jiucheng.orm.interceptor.Close;
import org.jiucheng.plugin.db.BaseServiceImpl;
import org.jiucheng.util.ObjectUtil;

@Service("userAuthCacheService")
@Aop(Close.class)
public class UserAuthCacheServiceImpl extends BaseServiceImpl implements IUserAuthCacheService {
    public UserAuthCache getByWebappIdUserId(Long webappId, Long userId) {
        UserAuthCache userAuthCache = new UserAuthCache();
        userAuthCache.setWebappId(webappId);
        userAuthCache.setUserId(userId);
        List<UserAuthCache> uacs = list(userAuthCache);
        if(uacs.size() > 0) {
            return uacs.get(0);
        }
        return ObjectUtil.getNull();
    }
}
