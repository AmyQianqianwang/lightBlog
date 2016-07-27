package in.wenwen.service.impl;

import in.wenwen.service.IArticleTagService;

import org.jiucheng.aop.Aop;
import org.jiucheng.ioc.annotation.Service;
import org.jiucheng.orm.interceptor.Close;
import org.jiucheng.plugin.db.BaseServiceImpl;

@Service("articleTagService")
@Aop(Close.class)
public class ArticleTagServiceImpl extends BaseServiceImpl implements IArticleTagService {

}
