package in.wenwen.service;

import in.wenwen.dto.QueryDto;
import in.wenwen.entity.User;
import in.wenwen.util.PageResult;

import java.util.List;

import org.jiucheng.plugin.db.IBaseService;

public interface IUserService extends IBaseService {
    
    public PageResult<User> page(QueryDto queryDto);
    public PageResult<User> page(int pageIndex, int limit);
    public void deleteUserByIds(Long webappId, List<Long> ids);
    
}
