package in.wenwen.util;

import javax.servlet.http.HttpServletRequest;

import org.jiucheng.util.DefaultPropertiesUtil;

public class ConstUtil {
    public static final String COMMA = ",";
    public static final String TOKEN = "token";
    public static final String ERR = "err";
    
    public static final String SMTP_HOST = "smtp.host";
    public static final String SMTP_PORT = "smtp.port";
    public static final String SMTP_USERNAME = "smtp.username";
    public static final String SMTP_PASSWORD = "smtp.password";
    public static final String SMTP_CHARSET = "smtp.charset";
    public static final String SMTP_FR = "smtp.fr";
    public static final String SMTP_NICK = "smtp.nick";
    
    public static String getRedirectUri(String uri) {
        return getContext().concat(uri);
    }
    
    // / 或 /wenwen/
    public static String getContext() {
        return DefaultPropertiesUtil.getString("context", "/");
    }
    
    public static String getAdminUri(String jsp) {
        return "/WEB-INF/admin/".concat(jsp).concat(DefaultPropertiesUtil.getString("suffix", ".jsp"));
    }
    
    public static String getUri(String jsp) {
        return "/WEB-INF/".concat(UserManage.getTemplet()).concat("/").concat(jsp).concat(DefaultPropertiesUtil.getString("suffix", ".jsp"));
    }
    
    /** 网络路径地址 */
	public static String getURL(HttpServletRequest request) {
		String contextPath = request.getContextPath().equals("/") ? "" : request.getContextPath();
		String url = "http://" + request.getServerName();
		// 端口号不为80就加上端口号
		if (null2Int(Integer.valueOf(request.getServerPort())) != 80)
			url = url + ":" + null2Int(Integer.valueOf(request.getServerPort())) + contextPath;
		else {
			url = url + contextPath;
		}
		return url;
	}

	/**Object转换为Int类型方法*/
	public static int null2Int(Object s) {
		int v = 0;
		if (s != null)
			try {
				v = Integer.parseInt(s.toString());
			} catch (Exception localException) {
			}
		return v;
	}
}
