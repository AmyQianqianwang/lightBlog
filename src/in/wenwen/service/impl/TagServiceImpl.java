package in.wenwen.service.impl;

import java.util.ArrayList;
import java.util.List;

import in.wenwen.dto.ArticleDto;
import in.wenwen.dto.QueryDto;
import in.wenwen.entity.Tag;
import in.wenwen.service.ITagService;
import in.wenwen.util.HtmlUtil;
import in.wenwen.util.Page;

import org.jiucheng.aop.Aop;
import org.jiucheng.ioc.annotation.Service;
import org.jiucheng.orm.SQLHelper;
import org.jiucheng.orm.interceptor.Close;
import org.jiucheng.plugin.db.BaseServiceImpl;
import org.jiucheng.util.ObjectUtil;
import org.jiucheng.util.StringUtil;

@Service("tagService")
@Aop(Close.class)
public class TagServiceImpl extends BaseServiceImpl implements ITagService {
    
    public List<Tag> getTopJson(Long webappId, Long size) {
        SQLHelper sh = new SQLHelper("SELECT a.name, a.size FROM tag a WHERE a.webapp_id = ? ORDER BY a.size DESC LIMIT ?");
        sh.insertValue(webappId);
        sh.insertValue(size);
        return getBaseDao().listBySQL(Tag.class, sh);
    }

    public Page<ArticleDto> pageT(QueryDto queryDto) {
        String t = queryDto.getT();
        int p = queryDto.getPageIndex();
        Page<ArticleDto> page = new Page<ArticleDto>();
        page.setCurrentPage(p);
        page.setTitle(t);
        page.setPrev("<");
        page.setNext(">");
        page.setPageSize(queryDto.getLimit());
        page.setBarSize(3);
        Long webappId = queryDto.getWebappId();
        SQLHelper sh = new SQLHelper("SELECT * FROM tag WHERE webapp_id = ? AND name = ? ");
        sh.insertValue(webappId);
        sh.insertValue(t);
        List<Tag> tags = getBaseDao().listBySQL(Tag.class, sh);
        Tag tag = null;
        if(tags.size() > 0) {
            tag = tags.get(0);
        }
        if(ObjectUtil.isNull(tag)) {
            page.setTotalRow(0);
            page.setTotalPage(0);
            page.setCurrentPage(1);
            page.setResults(new ArrayList<ArticleDto>());
            return page;
        }
        page.setTotalRow(tag.getSize().intValue());
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
            sh.clearValues();
            sh.append("SELECT a.id,a.title,a.content,a.create_datetime,a.click,a.assess,IFNULL(b.nick,'匿名') user_name "
                        + "FROM article a INNER JOIN article_tag c ON a.webapp_id = c.webapp_id AND a.id = c.article_id AND a.is_halt = 'F' AND c.tag_id = ? "
                        + "LEFT JOIN `user` b ON  a.user_id = b.id WHERE (1=1) ORDER BY a.id DESC LIMIT ?,?");
            sh.insertValue(tag.getId());
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
                ad.setTitleHtml(ad.getTitle());
                ad.setContent(content);
            }
            page.setResults(articles);
        }
        return page;
    }

    public List<Tag> listTagDescSize(Long webappId) {
        SQLHelper sh = new SQLHelper("SELECT * FROM tag WHERE webapp_id = ? AND is_halt = ? ORDER BY size DESC");
        sh.insertValue(webappId);
        sh.insertValue("F");
        return getBaseDao().listBySQL(Tag.class, sh);
    }
}
