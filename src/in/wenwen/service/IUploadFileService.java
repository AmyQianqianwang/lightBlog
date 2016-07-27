package in.wenwen.service;

import in.wenwen.entity.UploadFile;

import org.jiucheng.plugin.db.IBaseService;

public interface IUploadFileService extends IBaseService {
    public UploadFile findByName(String name);
}
