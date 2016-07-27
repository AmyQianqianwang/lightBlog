package in.wenwen.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import in.wenwen.dto.LyDto;
import in.wenwen.dto.LyQueryDto;
import in.wenwen.dto.QueryDto;
import in.wenwen.entity.Article;
import in.wenwen.entity.Ly;
import in.wenwen.entity.User;
import in.wenwen.service.IIpService;
import in.wenwen.service.ILyService;
import in.wenwen.util.EmailUtil;
import in.wenwen.util.PageResult;
import in.wenwen.util.UserManage;

import org.jiucheng.aop.Aop;
import org.jiucheng.ioc.annotation.Inject;
import org.jiucheng.ioc.annotation.Service;
import org.jiucheng.orm.SQLHelper;
import org.jiucheng.orm.interceptor.Close;
import org.jiucheng.orm.interceptor.Tx;
import org.jiucheng.plugin.db.BaseServiceImpl;
import org.jiucheng.util.IpUtil;
import org.jiucheng.util.ObjectUtil;
import org.jiucheng.util.StringUtil;
import org.jiucheng.web.util.WebUtil;

@Service("lyService")
@Aop(Close.class)
public class LyServiceImpl extends BaseServiceImpl implements ILyService {
    
    @Inject
    private IIpService ipService;
    
    @Aop(Tx.class)
    public void saveLyUpdateArticle(Ly ly, Article article) {
        Long assess = article.getAssess();
        Article tmp = new Article();
        tmp.setId(ly.getArticleId());
        tmp.setAssess(assess + 1);
        tmp.setModifyDatetime(UserManage.getReceiveDatetime());
        update(tmp);
        
        ly.setWebappId(UserManage.getWebappId());
        ly.setCreateDatetime(UserManage.getReceiveDatetime());
        ly.setModifyDatetime(UserManage.getReceiveDatetime());
        ly.setImg(getImg());
        String ip = WebUtil.getIp();
        if(IpUtil.isIp(ip)) {
            ly.setIp(ip);
            ly.setIpName(ipService.getName(IpUtil.toLong(ip)));
        }
        Long longId = (Long) save(ly);
        ly.setId(longId);
        // 邮件通知作者
        User user = ipService.get(User.class, article.getUserId());
        if(user != null && UserManage.getWebappId().equals(user.getWebappId())) {
            sendEmail(user.getEmail(), ly, article);
        }
        // 如果是回复其他留言则邮件通知
        Long lyId = ly.getLyId();
        if(lyId == null) {
            return;
        }
        Ly needCall = get(Ly.class, lyId);
        if(needCall == null || !UserManage.getWebappId().equals(needCall.getWebappId())) {
            return;
        }
        sendEmail(needCall.getEmail(), ly, article);
    }
    
    private void sendEmail(String toEmail, Ly ly, Article article) {
        if(ly == null || !UserManage.getWebappId().equals(ly.getWebappId()) || StringUtil.isBlank(toEmail)) {
            return;
        }
        String subject = "《" + article.getTitle() + "》" + ly.getAuthor() + "回复了你，" + UserManage.getWebappName();
        String msg = "<html>";
        String url = UserManage.getWebappUrl() + "article/" + article.getId() + "#cmt" + ly.getId();
        msg += "<h3>" + "<a href=\"" + url + "\">《" + article.getTitle() + "》</a>" + "</h3>";
        msg += "<p>回复内容：" + ly.getContent() + "</p>";
        msg += "<p>回复昵称：" + ly.getAuthor() + "</p>";
        msg += "<p>文章地址：<a href=\"" + url + "\">" + url + "</a></p>";
        msg += "</html>";
        try {
            EmailUtil.sendHtml(toEmail, subject, msg);
        }catch(Exception e) {
        }
    }
    
    private String getImg() {
        double d = (Math.random() * 100);
        d += 1;
        return Integer.toString((int)d).concat(".jpg");
    }

    public List<Ly> getNew(Long webappid, Long size) {
        SQLHelper sh = new SQLHelper("SELECT a.content,a.article_id FROM ly a WHERE a.webapp_id = ? AND a.is_halt = ? ORDER BY id DESC LIMIT ?");
        sh.insertValue(webappid);
        sh.insertValue("F");
        sh.insertValue(size);
        return getBaseDao().listBySQL(Ly.class, sh);
    }

    public List<LyDto> getArticleLy(Long webappId, Long articleId, Long lyId) {
        SQLHelper sh = new SQLHelper("SELECT a.id,a.author,a.url,a.img,IFNULL(a.ip_name,'未知') ip_name, a.content, a.article_id,DATE_FORMAT(a.create_datetime, '%y-%m-%d %k:%i') AS create_datetime,a.is_halt FROM ly a WHERE a.webapp_id = ? AND a.article_id = ? ");
        sh.insertValue(webappId);
        sh.insertValue(articleId);
        if(ObjectUtil.isNotNull(lyId)) {
            sh.append(" AND a.ly_id = ? ");
            sh.insertValue(lyId);
        }else {
            sh.append(" AND a.ly_id IS NULL ");
        }
        List<LyDto> lys = getBaseDao().listBySQL(LyDto.class, sh);
        for(LyDto ly : lys) {
            if("T".equals(ly.getIsHalt())) {
                ly.setContent("评论已删除！");
            }
            ly.setLys(getArticleLy(webappId, articleId, ly.getId()));
        }
        return lys;
    }
    
    public PageResult<LyQueryDto> page(QueryDto queryDto) {
        PageResult<LyQueryDto> pru = new PageResult<LyQueryDto>();
        SQLHelper sh = new SQLHelper();
        String filterSql = "1=1 AND a.webapp_id = ? ";
        sh.insertValue(queryDto.getWebappId());
        if(StringUtil.isNotBlank(queryDto.getQ())) {
            filterSql += " AND (a.name LIKE ? OR a.id = ?) ";
            sh.insertValue("%" + queryDto.getQ() + "%");
            sh.insertValue(queryDto.getQ());
        }
        sh.append("SELECT COUNT(1) num FROM ly a WHERE " + filterSql);
        @SuppressWarnings("rawtypes")
        List<Map> map = getBaseDao().listBySQL(sh);
        pru.setResults(Integer.parseInt((map.get(0).get("num")).toString()));
        List<LyQueryDto> articles = new ArrayList<LyQueryDto>();
        if(pru.getResults() > 0) {
            sh.clearSql();
            sh.append("SELECT a.*,IFNULL(b.title,'无此文章') AS article_title FROM ly a LEFT JOIN article b ON a.article_id = b.id WHERE " + filterSql + " ORDER BY a.id DESC LIMIT ?,?");
            sh.insertValue(queryDto.getPageIndex() * queryDto.getLimit());
            sh.insertValue(queryDto.getLimit());
            articles = getBaseDao().listBySQL(LyQueryDto.class, sh);
        }
        pru.setRows(articles);
        return pru;
    }
}
