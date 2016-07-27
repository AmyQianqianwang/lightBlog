package in.wenwen.out;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.jiucheng.util.ObjectUtil;
import org.jiucheng.web.WebWrapper;
import org.jiucheng.web.handler.Out;
import org.jiucheng.web.util.WebUtil;

import com.alibaba.fastjson.JSON;

public class JSONStringOut implements Out {

    public void invoke(WebWrapper webWrapper, Object rs) {
        HttpServletResponse response = WebUtil.getResponse();
        response.setCharacterEncoding(WebUtil.getEncoding());
        response.setContentType("text/html;charset=" + WebUtil.getEncoding());
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(ObjectUtil.isNotNull(rs)) {
            out.print(JSON.toJSONString(rs));
        }
        if(ObjectUtil.isNotNull(out)) {
            out.flush();
            out.close();
        }
    }
}
