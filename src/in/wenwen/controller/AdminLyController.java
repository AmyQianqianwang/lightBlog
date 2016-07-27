package in.wenwen.controller;

import in.wenwen.dto.LyQueryDto;
import in.wenwen.dto.QueryDto;
import in.wenwen.entity.Ly;
import in.wenwen.handler.AdminHandler;
import in.wenwen.out.UserOut;
import in.wenwen.service.ILyService;
import in.wenwen.util.ConstUtil;
import in.wenwen.util.Err;
import in.wenwen.util.PageResult;
import in.wenwen.util.UserManage;

import org.jiucheng.ioc.annotation.Inject;
import org.jiucheng.web.annotation.Controller;
import org.jiucheng.web.annotation.Param;
import org.jiucheng.web.annotation.Controller.GET;

@Controller(AdminHandler.class)
public class AdminLyController {
    
    @Inject
    private ILyService lyService;
    
    @GET("/admin/ly/index.html")
    public String index() {
        return ConstUtil.getAdminUri("ly/index");
    }
    
    @GET(value = "/admin/ly/page.json", out = UserOut.class)
    public PageResult<LyQueryDto> json(@Param QueryDto queryDto) {
        queryDto.setWebappId(UserManage.getWebappId());
        return lyService.page(queryDto);
    }
    
    @GET(value = "/admin/ly/halt.html", out = UserOut.class)
    public Err delete(@Param Ly ly) {
        Err err = new Err("操作失败");
        if(ly.getId() == null) {
            return err;
        }
        if(!("F".equals(ly.getIsHalt()) || "T".equals(ly.getIsHalt()))) {
            ly.setIsHalt("F");
        }
        Ly l = lyService.get(Ly.class, ly.getId());
        if(l == null || !UserManage.getWebappId().equals(l.getWebappId())) {
            return err;
        }
        l.setIsHalt(ly.getIsHalt());
        l.setModifyDatetime(UserManage.getReceiveDatetime());
        lyService.update(l);
        err.setErr("0");
        return err;
    }
}
