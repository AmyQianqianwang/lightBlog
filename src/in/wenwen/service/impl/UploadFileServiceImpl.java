package in.wenwen.service.impl;

import java.util.List;

import org.jiucheng.aop.Aop;
import org.jiucheng.ioc.annotation.Service;
import org.jiucheng.orm.interceptor.Close;
import org.jiucheng.plugin.db.BaseServiceImpl;
import org.jiucheng.util.ObjectUtil;

import in.wenwen.entity.UploadFile;
import in.wenwen.service.IUploadFileService;

@Service("uploadFileService")
@Aop(Close.class)
public class UploadFileServiceImpl extends BaseServiceImpl implements IUploadFileService {
    public UploadFile findByName(String name) {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setName(name);
        List<UploadFile> ufs = getBaseDao().list(uploadFile);
        if(ufs.size() == 0) {
            return ObjectUtil.getNull();
        }
        return ufs.get(0);
    }
}
