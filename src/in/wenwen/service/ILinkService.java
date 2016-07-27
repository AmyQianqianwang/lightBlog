package in.wenwen.service;

import in.wenwen.dto.QueryDto;
import in.wenwen.entity.Link;
import in.wenwen.util.PageResult;

import java.util.List;

import org.jiucheng.plugin.db.IBaseService;

public interface ILinkService extends IBaseService {
    public void deleteUserByIds(Long webappId, List<Long> ids);
    public PageResult<Link> page(QueryDto queryDto);
    public List<Link> getNewFr(Long webappId, Long size);
    public List<Link> listNew(Long webappId);
    public void updateJiaFr(Long webappId, String uid);
}
