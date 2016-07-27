package in.wenwen.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jiucheng.aop.Aop;
import org.jiucheng.ioc.annotation.Service;
import org.jiucheng.orm.SQLHelper;
import org.jiucheng.orm.interceptor.Close;
import org.jiucheng.orm.interceptor.Tx;
import org.jiucheng.plugin.db.BaseServiceImpl;
import org.jiucheng.util.IpUtil;
import org.jiucheng.util.ObjectUtil;
import org.jiucheng.util.StringUtil;

import in.wenwen.entity.Ip;
import in.wenwen.service.IIpService;

@Service("ipService")
@Aop(Close.class)
public class IpServiceImpl extends BaseServiceImpl implements IIpService {
    
    @Aop(Tx.class)
    public void saveIp(List<String> strs) {
        String line;
        String[] ls;
        Ip ip;
        String str;
        ip = new Ip();
        delete(ip);
        for(int i = 0; i < strs.size(); i ++) {
            str = strs.get(i);
            if(StringUtil.isBlank(str)) {
                continue;
            }
            line = str.replaceAll("\\s+", " ").replaceAll("CZ88.NET", "未知").trim();
            //System.out.println(line);
            ls = line.split(" ");
            if(ls.length < 3) {
                continue;
            }
            ip = new Ip();
            ip.setId(Long.valueOf(i + 1));
            ip.setStart(ls[0]);
            ip.setStartNum(IpUtil.toLong(ls[0]));
            ip.setEndNum(IpUtil.toLong(ls[1]));
            ip.setEnd(ls[1]);
            ip.setName(line.substring(ls[0].length() + ls[1].length() + 2));
            save(ip);
        }
    }
    
    public String getName(Long ip) {
        if(ObjectUtil.isNull(ip)) {
            return ObjectUtil.getNull();
        }
        SQLHelper sh = new SQLHelper("SELECT `name` FROM ip WHERE start_num <= ? AND end_num >= ?");
        sh.insertValue(ip);
        sh.insertValue(ip);
        List<Map> rs= getBaseDao().listBySQL(sh);
        if(rs.size() > 0) {
            return Objects.toString(rs.get(0).get("name"), null);
        }
        return ObjectUtil.getNull();
    }
}
