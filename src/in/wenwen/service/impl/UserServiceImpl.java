package in.wenwen.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import in.wenwen.dto.QueryDto;
import in.wenwen.entity.User;
import in.wenwen.service.IUserService;
import in.wenwen.util.PageResult;

import org.jiucheng.aop.Aop;
import org.jiucheng.ioc.annotation.Service;
import org.jiucheng.orm.SQLHelper;
import org.jiucheng.orm.interceptor.Close;
import org.jiucheng.orm.interceptor.Tx;
import org.jiucheng.plugin.db.BaseServiceImpl;
import org.jiucheng.util.StringUtil;

@Service("userService")
@Aop(Close.class)
public class UserServiceImpl extends BaseServiceImpl implements IUserService {
    
    @SuppressWarnings("rawtypes")
    public PageResult<User> page(QueryDto queryDto) {
        PageResult<User> pru = new PageResult<User>();
        SQLHelper sh = new SQLHelper();
        String filterSql = "1=1 AND webapp_id = ?";
        sh.insertValue(queryDto.getWebappId());
        if(StringUtil.isNotBlank(queryDto.getQ())) {
            filterSql += " AND (uid LIKE ? OR nick LIKE ?) ";
            sh.insertValue("%" + queryDto.getQ() + "%");
            sh.insertValue("%" + queryDto.getQ() + "%");
        }
        sh.append("SELECT count(*) num FROM user WHERE " + filterSql);
        List<Map> map = getBaseDao().listBySQL(sh);
        pru.setResults(Integer.parseInt((map.get(0).get("num")).toString()));
        List<User> useres = new ArrayList<User>();
        if(pru.getResults() > 0) {
            sh.clearSql();
            sh.append("SELECT * FROM user WHERE " + filterSql + " ORDER BY id DESC LIMIT ?,?");
            sh.insertValue(queryDto.getPageIndex() * queryDto.getLimit());
            sh.insertValue(queryDto.getLimit());
            useres = getBaseDao().listBySQL(User.class, sh);
        }
        pru.setRows(useres);
        return pru;
    }
    
    @SuppressWarnings("rawtypes")
    public PageResult<User> page(int pageIndex, int limit) {
        PageResult<User> pru = new PageResult<User>();
        SQLHelper sh = new SQLHelper();
        sh.append("SELECT * FROM user ORDER BY id DESC LIMIT ?,?");
        sh.insertValue(pageIndex * limit);
        sh.insertValue(limit);
        List<User> useres = getBaseDao().listBySQL(User.class, sh);
        sh = new SQLHelper("SELECT count(*) num FROM user");
        List<Map> map = getBaseDao().listBySQL(sh);
        pru.setResults(Integer.parseInt((map.get(0).get("num")).toString()));
        pru.setRows(useres);
        return pru;
    }
    
    @Aop(Tx.class)
    public void deleteUserByIds(Long webappId, List<Long> ids) {
        User user;
        for(Long id : ids) {
            user = new User();
            user.setId(id);
            user.setWebappId(webappId);
            getBaseDao().delete(user);
        }
    }
}
