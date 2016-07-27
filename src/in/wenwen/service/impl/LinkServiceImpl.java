package in.wenwen.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import in.wenwen.dto.QueryDto;
import in.wenwen.entity.Link;
import in.wenwen.service.ILinkService;
import in.wenwen.util.PageResult;

import org.jiucheng.aop.Aop;
import org.jiucheng.ioc.annotation.Service;
import org.jiucheng.orm.SQLHelper;
import org.jiucheng.orm.interceptor.Close;
import org.jiucheng.orm.interceptor.Tx;
import org.jiucheng.plugin.db.BaseServiceImpl;
import org.jiucheng.util.StringUtil;

@Service("linkService")
@Aop(Close.class)
public class LinkServiceImpl extends BaseServiceImpl implements ILinkService {
    
    @Aop(Tx.class)
    public void deleteUserByIds(Long webappId, List<Long> ids) {
        Link link;
        for(Long id : ids) {
            link  = new Link();
            link.setWebappId(webappId);
            link.setId(id);
            getBaseDao().delete(link);
        }
    }
    
    public PageResult<Link> page(QueryDto queryDto) {
        PageResult<Link> pru = new PageResult<Link>();
        SQLHelper sh = new SQLHelper();
        String filterSql = "1=1 AND webapp_id = ? ";
        sh.insertValue(queryDto.getWebappId());
        if(StringUtil.isNotBlank(queryDto.getQ())) {
            filterSql += " AND (name LIKE ? OR uid LIKE ? OR url LIKE ?) ";
            sh.insertValue("%" + queryDto.getQ() + "%");
            sh.insertValue("%" + queryDto.getQ() + "%");
            sh.insertValue("%" + queryDto.getQ() + "%");
        }
        sh.append("SELECT count(*) num FROM link WHERE " + filterSql);
        List<Map> map = getBaseDao().listBySQL(sh);
        pru.setResults(Integer.parseInt((map.get(0).get("num")).toString()));
        List<Link> articles = new ArrayList<Link>();
        if(pru.getResults() > 0) {
            sh.clearSql();
            sh.append("SELECT * FROM link WHERE " + filterSql + " ORDER BY id DESC LIMIT ?,?");
            sh.insertValue(queryDto.getPageIndex() * queryDto.getLimit());
            sh.insertValue(queryDto.getLimit());
            articles = getBaseDao().listBySQL(Link.class, sh);
        }
        pru.setRows(articles);
        return pru;
    }
    
    public List<Link> getNewFr(Long webappId, Long size) {
        SQLHelper sh = new SQLHelper("SELECT a.uid, a.name, a.url, a.remark FROM link a WHERE a.webapp_id = ? AND a.is_halt = 'F' ORDER BY a.modify_datetime DESC LIMIT ?");
        sh.insertValue(webappId);
        sh.insertValue(size);
        return getBaseDao().listBySQL(Link.class, sh);
    }

    public List<Link> listNew(Long webappId) {
        SQLHelper sh = new SQLHelper("SELECT a.uid, a.name, a.url, a.remark FROM link a WHERE a.webapp_id = ? AND a.is_halt = 'F' ORDER BY a.modify_datetime DESC");
        sh.insertValue(webappId);
        return getBaseDao().listBySQL(Link.class, sh);
    }
    
    public void updateJiaFr(Long webappId, String uid) {
        SQLHelper sh = new SQLHelper("UPDATE link SET fr = fr + 1,modify_datetime = NOW() WHERE webapp_id = ? AND uid = ?");
        sh.insertValue(webappId);
        sh.insertValue(uid);
        getBaseDao().updateBySQL(sh);
    }
}
