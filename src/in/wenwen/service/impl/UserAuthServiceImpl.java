package in.wenwen.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import in.wenwen.dto.QueryDto;
import in.wenwen.entity.UserAuth;
import in.wenwen.service.IUserAuthService;
import in.wenwen.util.PageResult;

import org.jiucheng.aop.Aop;
import org.jiucheng.ioc.annotation.Service;
import org.jiucheng.orm.SQLHelper;
import org.jiucheng.orm.interceptor.Close;
import org.jiucheng.orm.interceptor.Tx;
import org.jiucheng.plugin.db.BaseServiceImpl;
import org.jiucheng.util.ObjectUtil;
import org.jiucheng.util.StringUtil;

@Service("userAuthService")
@Aop(Close.class)
public class UserAuthServiceImpl extends BaseServiceImpl implements IUserAuthService {
    
    public UserAuth getByWebappIdUri(Long webappId, String uri) {
        UserAuth userAuth = new UserAuth();
        userAuth.setWebappId(webappId);
        userAuth.setUri(uri);
        List<UserAuth> uas = list(userAuth);
        if(uas.size() > 0) {
            return uas.get(0);
        }
        return ObjectUtil.getNull();
    }
    
    @Aop(Tx.class)
    public void deleteByIds(Long webappId, List<Long> ids) {
        UserAuth userAuth;
        for(Long id : ids) {
            userAuth = new UserAuth();
            userAuth.setWebappId(webappId);
            userAuth.setId(id);
            getBaseDao().delete(userAuth);
        }
    }
    
    public UserAuth get(Long id) {
        UserAuth userAuth = new UserAuth();
        userAuth.setId(id);
        List<UserAuth> uas = list(userAuth);
        if(uas.size() > 0) {
            return uas.get(0);
        }
        return ObjectUtil.getNull();
    }
    
    @SuppressWarnings("rawtypes")
    public PageResult<UserAuth> page(QueryDto queryDto) {
        PageResult<UserAuth> pru = new PageResult<UserAuth>();
        SQLHelper sh = new SQLHelper();
        String filterSql = "1=1 AND webapp_id = ?";
        sh.insertValue(queryDto.getWebappId());
        if(StringUtil.isNotBlank(queryDto.getQ())) {
            filterSql += " AND (uri LIKE ? OR descript LIKE ?) ";
            sh.insertValue("%" + queryDto.getQ() + "%");
            sh.insertValue("%" + queryDto.getQ() + "%");
        }
        sh.append("SELECT count(*) num FROM user_auth WHERE " + filterSql);
        List<Map> map = getBaseDao().listBySQL(sh);
        pru.setResults(Integer.parseInt((map.get(0).get("num")).toString()));
        List<UserAuth> useres = new ArrayList<UserAuth>();
        if(pru.getResults() > 0) {
            sh.clearSql();
            sh.append("SELECT * FROM user_auth WHERE " + filterSql + " ORDER BY id DESC LIMIT ?,?");
            sh.insertValue(queryDto.getPageIndex() * queryDto.getLimit());
            sh.insertValue(queryDto.getLimit());
            useres = getBaseDao().listBySQL(UserAuth.class, sh);
        }
        pru.setRows(useres);
        return pru;
    }
}
