package org.jiucheng.web.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.jiucheng.json.JSON;
import org.jiucheng.util.ObjectUtil;
import org.jiucheng.web.WebWrapper;
import org.jiucheng.web.result.JSONResult;
import org.jiucheng.web.util.WebUtil;

public class DefaultOut implements Out{

    public void invoke(WebWrapper webWrapper, Object rs) {
        HttpServletResponse response = WebUtil.getResponse();
        if(ObjectUtil.isNull(rs)) {
            return;
        }
        if(rs instanceof String) {
            response.setCharacterEncoding(WebUtil.getEncoding());
            response.setContentType("text/html;charset=" + WebUtil.getEncoding());
            WebUtil.dispatcher(rs.toString());
        }else if(rs instanceof JSONResult) {
            response.setCharacterEncoding(WebUtil.getEncoding());
            response.setContentType("text/html;charset=" + WebUtil.getEncoding());
            PrintWriter out;
            try {
                out = response.getWriter();
                out.print(JSON.toJSONString(rs));
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
