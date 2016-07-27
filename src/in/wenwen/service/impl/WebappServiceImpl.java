package in.wenwen.service.impl;

import in.wenwen.service.IWebappService;

import org.jiucheng.aop.Aop;
import org.jiucheng.ioc.annotation.Service;
import org.jiucheng.orm.interceptor.Close;
import org.jiucheng.plugin.db.BaseServiceImpl;

@Service("webappService")
@Aop(Close.class)
public class WebappServiceImpl extends BaseServiceImpl implements IWebappService {

}
