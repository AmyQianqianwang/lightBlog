package in.wenwen.service;

import in.wenwen.entity.Domain;

import org.jiucheng.plugin.db.IBaseService;

public interface IDomainService extends IBaseService {
    public Domain getBySite(String site);
}
