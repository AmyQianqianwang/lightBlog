package in.wenwen.service;

import in.wenwen.dto.LyDto;
import in.wenwen.dto.LyQueryDto;
import in.wenwen.dto.QueryDto;
import in.wenwen.entity.Article;
import in.wenwen.entity.Ly;
import in.wenwen.util.PageResult;

import java.util.List;

import org.jiucheng.plugin.db.IBaseService;

public interface ILyService extends IBaseService {
    public void saveLyUpdateArticle(Ly ly, Article article);
    public List<Ly> getNew(Long webappid, Long size);
    public List<LyDto> getArticleLy(Long webappId, Long articleId, Long lyId);
    public PageResult<LyQueryDto> page(QueryDto queryDto);;
}
