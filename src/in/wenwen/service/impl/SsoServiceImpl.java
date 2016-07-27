package in.wenwen.service.impl;

import in.wenwen.service.ISsoService;

import org.jiucheng.aop.Aop;
import org.jiucheng.ioc.annotation.Service;
import org.jiucheng.orm.interceptor.Close;
import org.jiucheng.plugin.db.BaseServiceImpl;

@Service("ssoService")
@Aop(Close.class)
public class SsoServiceImpl extends BaseServiceImpl implements ISsoService {
}
