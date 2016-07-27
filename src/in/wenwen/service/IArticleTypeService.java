package in.wenwen.service;

import in.wenwen.dto.ArticleTypeDto;
import in.wenwen.dto.QueryDto;
import in.wenwen.entity.ArticleType;
import in.wenwen.util.PageResult;

import java.util.List;

import org.jiucheng.plugin.db.IBaseService;

public interface IArticleTypeService extends IBaseService {
    public List<ArticleTypeDto> getBlogTypeDto(Long webappid);
    public PageResult<ArticleType> page(QueryDto queryDto);
    public void deleteUserByIds(Long webappId, List<Long> ids);
    public List<ArticleTypeDto> listSizeDesc(Long webappId);
}
