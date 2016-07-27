package in.wenwen.service;

import org.jiucheng.plugin.db.IBaseService;

public interface ILogUriService extends IBaseService {
    public void saveLogUri(String pathInfo, String ip);
}
