package in.wenwen.service;

import java.util.List;

import in.wenwen.dto.ArticleDto;
import in.wenwen.dto.BlogDto;
import in.wenwen.dto.QueryDto;
import in.wenwen.entity.Article;
import in.wenwen.util.Page;
import in.wenwen.util.PageResult;

import org.jiucheng.plugin.db.IBaseService;

public interface IArticleService extends IBaseService {
    public PageResult<ArticleDto> pageDto(QueryDto queryDto);
    public PageResult<Article> page(QueryDto queryDto);
    public void deleteUserByIds(Long webappId, List<Long> ids);
    public Page<ArticleDto> pageS(QueryDto queryDto);
    public Article getPrev(Long webappId, Long id);
    public Article getNext(Long webappId, Long id);
    public List<BlogDto> getRnd(Long webappId, Integer limit);
    public Article saveOrUpdateArticle(Article article);
}
