package in.wenwen.service.impl;

import in.wenwen.entity.LogUri;
import in.wenwen.service.IIpService;
import in.wenwen.service.ILogUriService;
import in.wenwen.util.UserManage;

import org.jiucheng.aop.Aop;
import org.jiucheng.ioc.annotation.Inject;
import org.jiucheng.ioc.annotation.Service;
import org.jiucheng.orm.interceptor.Close;
import org.jiucheng.orm.interceptor.Tx;
import org.jiucheng.plugin.db.BaseServiceImpl;
import org.jiucheng.util.IpUtil;

@Service("logUriService")
@Aop(Close.class)
public class LogUriServiceImpl extends BaseServiceImpl implements ILogUriService {
    
    @Inject
    private IIpService ipService;
    
    @Aop(Tx.class)
    public void saveLogUri(String pathInfo, String ip) {
        if(IpUtil.isIp(ip)) {
            LogUri logUri = new LogUri();
            logUri.setWebappId(UserManage.getWebappId());
            logUri.setUri(pathInfo);
            logUri.setDomain(UserManage.getDomain());
            logUri.setIp(ip);
            logUri.setIpName(ipService.getName(IpUtil.toLong(ip)));
            logUri.setCreateDatetime(UserManage.getReceiveDatetime());
            logUri.setModifyDatetime(UserManage.getReceiveDatetime());
            save(logUri);
        }
    }
}
