package in.wenwen.service;

import in.wenwen.entity.UserAuthCache;

import org.jiucheng.plugin.db.IBaseService;

public interface IUserAuthCacheService extends IBaseService {
    public UserAuthCache getByWebappIdUserId(Long webappId, Long userId);
}
