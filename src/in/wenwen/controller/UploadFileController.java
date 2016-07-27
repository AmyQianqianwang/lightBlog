package in.wenwen.controller;

import in.wenwen.entity.UploadFile;
import in.wenwen.out.UserOut;
import in.wenwen.service.IUploadFileService;
import in.wenwen.util.MD5Util;
import in.wenwen.util.UploadResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jiucheng.ioc.annotation.Inject;
import org.jiucheng.util.FileUtil;
import org.jiucheng.util.ObjectUtil;
import org.jiucheng.web.annotation.Controller;
import org.jiucheng.web.annotation.Controller.POST;
import org.jiucheng.web.util.WebUtil;

@Controller
public class UploadFileController {
	
    @Inject
    private IUploadFileService uploadFileService;
    
	@POST( value = "/upload-file.html", out = UserOut.class)
	public UploadResult uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    UploadResult ur = new UploadResult();
	    ur.setUrl("上传出错");
		System.out.println(WebUtil.getWebRoot());
		//最大文件大小
		long maxSize = 1000000;
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List<FileItem> items = upload.parseRequest(request);
		Iterator<FileItem> itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			String fileName = item.getName();
			long fileSize = item.getSize();
			if (!item.isFormField()) {
				//检查文件大小
//				if(item.getSize() > maxSize){
//					out.println(getError("上传文件大小超过限制。"));
//					return;
//				}
				//检查扩展名
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
//				if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
//					out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
//					return;
//				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				String yyyyMMdd = sdf.format(new Date());
				String newFileName = MD5Util.getMd5ByBytes(item.get());
				UploadFile uf = uploadFileService.findByName(newFileName);
				if(ObjectUtil.isNull(uf)) {
	                File uploadedFile = FileUtil.newFile(uploadRoot(request) + yyyyMMdd + "/" + newFileName);
	                item.write(uploadedFile);
	                uf = new UploadFile();
	                uf.setName(newFileName);
	                uf.setRealName(fileName);
	                uf.setSize(fileSize);
	                uf.setDirCode("A");
	                uf.setDir(yyyyMMdd);
	                Date now = new Date();
	                uf.setCreateDatetime(now);
	                uf.setModifyDatetime(now);
	                uf.setFileType(fileExt);
	                uploadFileService.save(uf);
				}else {
				    yyyyMMdd = uf.getDir();
				}
		        ur.setError(0);
				ur.setUrl("/upload/" + yyyyMMdd + "/" + newFileName);
				return ur;
			}
		}
        return ur;
	}
	
	private String uploadRoot(HttpServletRequest request) {
		return WebUtil.getWebRoot() + "upload" + File.separator;
	}
	
}
