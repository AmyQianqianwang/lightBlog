package in.wenwen.service;

import java.util.List;

import in.wenwen.dto.QueryDto;
import in.wenwen.entity.UserAuth;
import in.wenwen.util.PageResult;

import org.jiucheng.plugin.db.IBaseService;

public interface IUserAuthService extends IBaseService {
    
    public UserAuth getByWebappIdUri(Long webappId, String uri);
    public void deleteByIds(Long webappId, List<Long> ids);
    public UserAuth get(Long id);
    public PageResult<UserAuth> page(QueryDto queryDto);
}
