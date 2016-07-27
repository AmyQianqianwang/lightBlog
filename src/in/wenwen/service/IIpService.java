package in.wenwen.service;

import java.util.List;

import org.jiucheng.plugin.db.IBaseService;

public interface IIpService extends IBaseService {
    public void saveIp(List<String> strs);
    public String getName(Long ip);
}
