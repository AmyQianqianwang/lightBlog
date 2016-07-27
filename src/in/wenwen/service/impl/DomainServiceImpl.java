package in.wenwen.service.impl;

import java.util.List;

import in.wenwen.entity.Domain;
import in.wenwen.service.IDomainService;

import org.jiucheng.aop.Aop;
import org.jiucheng.ioc.annotation.Service;
import org.jiucheng.orm.interceptor.Close;
import org.jiucheng.plugin.db.BaseServiceImpl;
import org.jiucheng.util.ObjectUtil;

@Service("domainService")
@Aop(Close.class)
public class DomainServiceImpl extends BaseServiceImpl implements IDomainService {
    public Domain getBySite(String site) {
        Domain domain = new Domain();
        domain.setSite(site);
        List<Domain> ds = list(domain);
        if(ds.size() > 0) {
            return ds.get(0);
        }
        return ObjectUtil.getNull();
    }
}
