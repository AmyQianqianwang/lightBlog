package in.wenwen.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import in.wenwen.dto.ArticleDto;
import in.wenwen.dto.BlogDto;
import in.wenwen.dto.QueryDto;
import in.wenwen.entity.Article;
import in.wenwen.entity.ArticleTag;
import in.wenwen.entity.ArticleType;
import in.wenwen.entity.Tag;
import in.wenwen.service.IArticleService;
import in.wenwen.service.IArticleTagService;
import in.wenwen.service.ITagService;
import in.wenwen.util.ConstUtil;
import in.wenwen.util.HtmlUtil;
import in.wenwen.util.Page;
import in.wenwen.util.PageResult;
import in.wenwen.util.UserManage;

import org.jiucheng.aop.Aop;
import org.jiucheng.ioc.annotation.Inject;
import org.jiucheng.ioc.annotation.Service;
import org.jiucheng.orm.DataAccessException;
import org.jiucheng.orm.SQLHelper;
import org.jiucheng.orm.interceptor.Close;
import org.jiucheng.orm.interceptor.Tx;
import org.jiucheng.plugin.db.BaseServiceImpl;
import org.jiucheng.util.ObjectUtil;
import org.jiucheng.util.StringUtil;

@Service("articleService")
@Aop(Close.class)
public class ArticleServiceImpl extends BaseServiceImpl implements IArticleService {
    
    @Inject
    private IArticleTagService articleTagService;
    @Inject
    private ITagService tagService;
    
    public Page<ArticleDto> pageS(QueryDto queryDto) {
        String q = queryDto.getQ();
        int p = queryDto.getPageIndex();
        Page<ArticleDto> page = new Page<ArticleDto>();
        page.setCurrentPage(p);
        page.setTitle(q);
        page.setPrev("<");
        page.setNext(">");
        page.setPageSize(queryDto.getLimit());
        page.setBarSize(3);
        
        SQLHelper sh = new SQLHelper();
        String filterSql = "1=1 AND a.is_halt = 'F' AND a.webapp_id = ? ";
        sh.insertValue(queryDto.getWebappId());
        if(StringUtil.isNotBlank(q)) {
            filterSql += " AND a.title LIKE ? ";
            sh.insertValue("%" + q + "%");
        }
        if(ObjectUtil.isNotNull(queryDto.getTypeId())) {
            filterSql += " AND a.type_id = ? ";
            sh.insertValue(queryDto.getTypeId());
        }
        sh.append("SELECT count(*) num FROM article a WHERE " + filterSql);
        List<Map> map = getBaseDao().listBySQL(sh);
        page.setTotalRow(Integer.parseInt((map.get(0).get("num")).toString()));
        if(page.getTotalRow() > 0) {
            int suf = page.getTotalRow() % page.getPageSize();
            int tp = page.getTotalRow() / page.getPageSize();
            if(suf != 0) {
                tp ++;
            }
            page.setTotalPage(tp);
            if(page.getCurrentPage() < 1) {
                page.setCurrentPage(1);
            }
            if(page.getCurrentPage() > tp) {
                page.setCurrentPage(tp);
            }
            sh.clearSql();
            sh.append("SELECT a.id,a.title,a.content,a.create_datetime,a.click,a.assess,IFNULL(b.nick,'匿名') user_name "
                    + " FROM article a LEFT JOIN user b ON  a.user_id = b.id WHERE " + filterSql + " ORDER BY a.id DESC LIMIT ?,? ");
            sh.insertValue((page.getCurrentPage() - 1) * page.getPageSize());
            sh.insertValue(page.getPageSize());
            List<ArticleDto> articles = getBaseDao().listBySQL(ArticleDto.class, sh);
            String content;
            String logo;
            for(ArticleDto ad : articles) {
                logo = HtmlUtil.getImgSrc(ad.getContent());
                if(StringUtil.isNotBlank(logo)) {
                    ad.setLogo(logo);
                }
                content = HtmlUtil.getText(ad.getContent());
                if(content.length() > 256) {
                    content = content.substring(0, 256) + "...";
                }
                ad.setTitleHtml(HtmlUtil.markKeywods(q, ad.getTitle()));
                ad.setContent(content);
            }
            page.setResults(articles);
        }
        page.setUrl("/s?p={0}");
        return page;
    }
    
    public PageResult<ArticleDto> pageDto(QueryDto queryDto) {
        PageResult<ArticleDto> pru = new PageResult<ArticleDto>();
        SQLHelper sh = new SQLHelper();
        String filterSql = "1=1 AND a.webapp_id = ? ";
        sh.insertValue(queryDto.getWebappId());
        if(StringUtil.isNotBlank(queryDto.getQ())) {
            filterSql += " AND (a.title LIKE ? OR a.id = ?) ";
            sh.insertValue("%" + queryDto.getQ() + "%");
            sh.insertValue(queryDto.getQ());
        }
        sh.append("SELECT count(*) num FROM article a WHERE " + filterSql);
        @SuppressWarnings("rawtypes")
        List<Map> map = getBaseDao().listBySQL(sh);
        pru.setResults(Integer.parseInt((map.get(0).get("num")).toString()));
        List<ArticleDto> articles = new ArrayList<ArticleDto>();
        if(pru.getResults() > 0) {
            sh.clearSql();
            sh.append("SELECT a.id, a.title, IFNULL(b.name,'无') as type_name,a. is_halt, a.create_datetime, a.modify_datetime "
                    + "FROM article a LEFT JOIN article_type b "
                    + "ON a.webapp_id = b.webapp_id AND a.type_id = b.id WHERE "
                    + filterSql + " ORDER BY a.id DESC LIMIT ?,?");
            sh.insertValue(queryDto.getPageIndex() * queryDto.getLimit());
            sh.insertValue(queryDto.getLimit());
            articles = getBaseDao().listBySQL(ArticleDto.class, sh);
        }
        pru.setRows(articles);
        return pru;
    }
    
    public PageResult<Article> page(QueryDto queryDto) {
        PageResult<Article> pru = new PageResult<Article>();
        SQLHelper sh = new SQLHelper();
        String filterSql = "1=1 AND a.webapp_id = ? ";
        sh.insertValue(queryDto.getWebappId());
        if(StringUtil.isNotBlank(queryDto.getQ())) {
            filterSql += " AND (title LIKE ? OR id = ?) ";
            sh.insertValue("%" + queryDto.getQ() + "%");
            sh.insertValue(queryDto.getQ());
        }
        sh.append("SELECT count(*) num FROM article WHERE " + filterSql);
        @SuppressWarnings("rawtypes")
        List<Map> map = getBaseDao().listBySQL(sh);
        pru.setResults(Integer.parseInt((map.get(0).get("num")).toString()));
        List<Article> articles = new ArrayList<Article>();
        if(pru.getResults() > 0) {
            sh.clearSql();
            sh.append("SELECT * FROM article WHERE " + filterSql + " ORDER BY id DESC LIMIT ?,?");
            sh.insertValue(queryDto.getPageIndex() * queryDto.getLimit());
            sh.insertValue(queryDto.getLimit());
            articles = getBaseDao().listBySQL(Article.class, sh);
        }
        pru.setRows(articles);
        return pru;
    }
    
    /**
     * 删除文章
     * 1.要更新文章分类条数
     * 2.要更新标签条数(标签如果就一个则删除标签)
     */
    @Aop(Tx.class)
    public void deleteUserByIds(Long webappId, List<Long> ids) {
        for(Long id : ids) {
            Article article = get(Article.class, id);
            if(article == null || !webappId.equals(article.getWebappId())) {
                throw new DataAccessException("非法操作！");
            }
            Article tmp  = new Article();
            tmp.setId(id);
            getBaseDao().delete(article);
            // 1
            if(article.getTypeId() != null) {
                ArticleType articleType = get(ArticleType.class, article.getTypeId());
                if(articleType != null && webappId.equals(articleType.getWebappId())) {
                    Long size = Long.valueOf(0L);
                    if(articleType.getSize() != null && articleType.getSize().compareTo(size) > 0) {
                        size = articleType.getSize() - 1L;
                    }
                    articleType.setSize(size);
                    articleType.setModifyDatetime(UserManage.getReceiveDatetime());
                    update(articleType);
                }
            }
            // 2
            ArticleTag articleTag = new ArticleTag();
            articleTag.setWebappId(webappId);
            articleTag.setArticleId(id);
            List<ArticleTag> ats = list(articleTag);
            Tag tag;
            for(ArticleTag at : ats) {
                tag = get(Tag.class, at.getTagId());
                if(tag != null && webappId.equals(tag.getWebappId())) {
                    if(tag.getSize() == null || tag.getSize() < 2) {
                        Tag t = new Tag();
                        t.setId(tag.getId());
                        delete(t);
                    }else {
                        tag.setSize(tag.getSize() - 1L);
                        tag.setModifyDatetime(UserManage.getReceiveDatetime());
                        update(tag);
                    }
                }
            }
        }
    }
    
    public Article getPrev(Long webappId, Long id) {
        SQLHelper sh = new SQLHelper("SELECT a.id, a.title title FROM article a WHERE a.is_halt = 'F' AND a.webapp_id = ? AND a.id > ? ORDER BY a.id ASC LIMIT 1");
        sh.insertValue(webappId);
        sh.insertValue(id);
        List<Article> listArticle = getBaseDao().listBySQL(Article.class, sh);
        if(listArticle.size() > 0) {
            Article article = listArticle.get(0);
            if(article.getTitle().length() > 26) {
                article.setTitle(article.getTitle().substring(0, 26).concat("..."));
            }
            return article;
        }
        return ObjectUtil.getNull();
    }
    
    public Article getNext(Long webappId, Long id) {
        SQLHelper sh = new SQLHelper("SELECT a.id, a.title title FROM article a WHERE a.is_halt = 'F' AND a.webapp_id = ? AND a.id < ? ORDER BY a.id DESC LIMIT 1");
        sh.insertValue(webappId);
        sh.insertValue(id);
        List<Article> listArticle = getBaseDao().listBySQL(Article.class, sh);
        if(listArticle.size() > 0) {
            Article article = listArticle.get(0);
            if(article.getTitle().length() > 26) {
                article.setTitle(article.getTitle().substring(0, 26).concat("..."));
            }
            return article;
        }
        return ObjectUtil.getNull();
    }

    public List<BlogDto> getRnd(Long webappId, Integer limit) {
        SQLHelper sh = new SQLHelper("SELECT count(1) size FROM article WHERE webapp_id = ? AND is_halt = ? ");
        sh.insertValue(webappId);
        sh.insertValue("F");
        List<Map> listSize = getBaseDao().listBySQL(sh);
        Integer size = ((Long) listSize.get(0).get("size")).intValue();
        Random rnd = new Random();
        Integer off = Integer.valueOf(0);
        if(size > 0) {
            off = rnd.nextInt(size);
        }
        sh.clearSql();
        sh.clearValues();
        sh = sh.append("SELECT a.id, a.title title FROM article a WHERE a.webapp_id = ? AND a.is_halt = 'F' ORDER BY id ASC LIMIT ?, ? ");
        sh.insertValue(webappId);
        sh.insertValue(off);
        sh.insertValue(limit);
        return getBaseDao().listBySQL(BlogDto.class, sh);
    }

    @Aop(Tx.class)
    public Article saveOrUpdateArticle(Article article) {
        Date now = article.getModifyDatetime();
        if(ObjectUtil.isNull(article.getId())) {
            Long id = (Long) save(article);
            article.setId(id);
        }else {
            Article a = get(Article.class, article.getId());
            ArticleType articleType = get(ArticleType.class, article.getTypeId());
            Long size = articleType.getSize();
            if(ObjectUtil.isNull(size)) {
                size = 1L;
            }
            articleType.setSize(articleType.getSize() - 1);
            articleType.setModifyDatetime(now);
            update(articleType);
            update(article);
            article.setWebappId(a.getWebappId());
        }
        if(ObjectUtil.isNotNull(article.getTypeId())) {
            ArticleType articleType = get(ArticleType.class, article.getTypeId());
            if(ObjectUtil.isNotNull(articleType)) {
                Long size = articleType.getSize();
                if(ObjectUtil.isNull(size)) {
                    size = 0L;
                }
                ++size;
                articleType.setSize(size);
                articleType.setModifyDatetime(now);
                update(articleType);
            }
        }
        saveOrUpdateTag(article);
        return article;
    }
    
    private void saveOrUpdateTag(Article article) {
        Long webappId = article.getWebappId();
        Date now = article.getModifyDatetime();
        
        //删除
        ArticleTag at = new ArticleTag();
        at.setWebappId(webappId);
        at.setArticleId(article.getId());
        List<ArticleTag> ats = list(at);
        Long atId;
        for(ArticleTag ag : ats) {
            atId = ag.getTagId();
            if(atId != null) {
                Tag t = get(Tag.class, atId);
                t.setSize(t.getSize() - 1);//标签个数减一
                t.setModifyDatetime(now);
                if(t.getSize() > 0) {
                    update(t);
                }else {
                    t = new Tag();
                    t.setId(ag.getTagId());
                    delete(t);
                }
                atId = ag.getId();
                ag = new ArticleTag();
                ag.setId(atId);
                delete(ag);
            }
        }
        //删除
        
        //标签保存
        String tags = article.getTags();
        if(StringUtil.isNotBlank(tags)) {
            String[] ts = tags.split(ConstUtil.COMMA);
            Tag tag;
            List<Long> ids = new ArrayList<Long>();
            List<Tag> listTag;
            for(int i = 0; i < ts.length; i ++) {
                tag = new Tag();
                tag.setWebappId(webappId);
                tag.setName(ts[i]);
                listTag = tagService.list(tag);
                if(listTag.size() > 0) {
                    tag = listTag.get(0);
                    tag.setModifyDatetime(article.getModifyDatetime());
                    tag.setSize(tag.getSize() + 1);
                    update(tag);
                }else {
                    tag.setSize(1L);
                    Long l = (Long) tagService.save(tag);
                    tag.setId(l);
                }
                ids.add(tag.getId());
            }
            
            for(Long d : ids) {
                at = new ArticleTag();
                at.setWebappId(webappId);
                at.setArticleId(article.getId());
                at.setTagId(d);
                save(at);
            }
        }
        //标签保存
    }
}
