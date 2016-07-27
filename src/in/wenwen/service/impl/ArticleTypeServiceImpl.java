package in.wenwen.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import in.wenwen.dto.ArticleTypeDto;
import in.wenwen.dto.QueryDto;
import in.wenwen.entity.ArticleType;
import in.wenwen.service.IArticleTypeService;
import in.wenwen.util.PageResult;

import org.jiucheng.aop.Aop;
import org.jiucheng.ioc.annotation.Service;
import org.jiucheng.orm.DataAccessException;
import org.jiucheng.orm.SQLHelper;
import org.jiucheng.orm.interceptor.Close;
import org.jiucheng.orm.interceptor.Tx;
import org.jiucheng.plugin.db.BaseServiceImpl;
import org.jiucheng.util.StringUtil;

@Service("articleTypeService")
@Aop(Close.class)
public class ArticleTypeServiceImpl extends BaseServiceImpl implements IArticleTypeService {

    public List<ArticleTypeDto> getBlogTypeDto(Long webappid) {
        ArticleTypeDto blogTypeDto = new ArticleTypeDto();
        blogTypeDto.setWebappId(webappid);
        blogTypeDto.setIsHalt("F");
        return list(blogTypeDto);
    }

    public PageResult<ArticleType> page(QueryDto queryDto) {
        PageResult<ArticleType> pru = new PageResult<ArticleType>();
        SQLHelper sh = new SQLHelper();
        String filterSql = "1=1 AND webapp_id = ? ";
        sh.insertValue(queryDto.getWebappId());
        if(StringUtil.isNotBlank(queryDto.getQ())) {
            filterSql += " AND (name LIKE ? OR id = ?) ";
            sh.insertValue("%" + queryDto.getQ() + "%");
            sh.insertValue(queryDto.getQ());
        }
        sh.append("SELECT count(*) num FROM article_type WHERE " + filterSql);
        @SuppressWarnings("rawtypes")
        List<Map> map = getBaseDao().listBySQL(sh);
        pru.setResults(Integer.parseInt((map.get(0).get("num")).toString()));
        List<ArticleType> articles = new ArrayList<ArticleType>();
        if(pru.getResults() > 0) {
            sh.clearSql();
            sh.append("SELECT * FROM article_type WHERE " + filterSql + " ORDER BY id DESC LIMIT ?,?");
            sh.insertValue(queryDto.getPageIndex() * queryDto.getLimit());
            sh.insertValue(queryDto.getLimit());
            articles = getBaseDao().listBySQL(ArticleType.class, sh);
        }
        pru.setRows(articles);
        return pru;
    }
    
    @Aop(Tx.class)
    public void deleteUserByIds(Long webappId, List<Long> ids) {
        ArticleType articleType;
        SQLHelper sh = new SQLHelper("UPDATE article SET type_id = ? WHERE webapp_id = ? AND type_id = ?");
        for(Long id : ids) {
            articleType = get(ArticleType.class, id);
            if(articleType == null) {
                throw new DataAccessException("文章分类不存在！");
            }
            if(!webappId.equals(articleType.getWebappId())) {
                throw new DataAccessException("非法操作！");
            }
            articleType = new ArticleType();
            articleType.setId(id);
            getBaseDao().delete(articleType);
            sh.clearValues();
            sh.insertValue(Long.valueOf(0L));
            sh.insertValue(webappId);
            sh.insertValue(id);
            getBaseDao().updateBySQL(sh);
        }
    }

    public List<ArticleTypeDto> listSizeDesc(Long webappId) {
        SQLHelper sh = new SQLHelper("SELECT a.* FROM article_type a WHERE a.webapp_id = ? AND a.is_halt = 'F' ORDER BY size DESC");
        sh.insertValue(webappId);
        return getBaseDao().listBySQL(ArticleTypeDto.class, sh);
    }
}
