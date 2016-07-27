package in.wenwen.service;

import java.util.List;

import in.wenwen.dto.ArticleDto;
import in.wenwen.dto.QueryDto;
import in.wenwen.entity.Tag;
import in.wenwen.util.Page;

import org.jiucheng.plugin.db.IBaseService;

public interface ITagService extends IBaseService {
    public Page<ArticleDto> pageT(QueryDto queryDto);
    public List<Tag> listTagDescSize(Long webappId);
    public List<Tag> getTopJson(Long webappId, Long size);
}
