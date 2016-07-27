package in.wenwen.util;

import java.util.Date;

import org.jiucheng.util.ObjectUtil;

import in.wenwen.dto.UserDto;
import in.wenwen.dto.WebappDto;

public class UserManage {
	private static final ThreadLocal<WebappDto> THREAD_WEBAPPDTO = new ThreadLocal<WebappDto>();
	private static final ThreadLocal<Date> THREAD_RECEIVE_DATETIME = new ThreadLocal<Date>();
	
	public static Date getReceiveDatetime() {
	    return THREAD_RECEIVE_DATETIME.get();
	}
	
	public static void setReceiveDatetime(Date value) {
	    THREAD_RECEIVE_DATETIME.set(value);
	}
	
	public static String getMain() {
	    return getWebappDto().getMain();
	}
	
	public static Long getWebappId() {
	    return getWebappDto().getId();
	}
	
	public static String getWebappName() {
	    String name = getWebappDto().getName();
	    if(ObjectUtil.isNotNull(name)) {
	        return name;
	    }
	    return "";
	}
	
	public static void setWebappDto(WebappDto webappDto) {
		THREAD_WEBAPPDTO.set(webappDto);
	}
	
	public static WebappDto getWebappDto() {
		return THREAD_WEBAPPDTO.get();
	}
	
	public static String getWebappUrl() {
	    return getWebappDto().getUrl();
	}
	
	public static UserDto getUserDto() {
	    return getWebappDto().getUser();
	}
	
	public static String getUserNick() {
	    return getUserDto().getNick();
	}
	
	public static String getToken() {
	    return getUserDto().getToken();
	}
	
	public static String getTemplet() {
	    return getWebappDto().getTemplet();
	}
	
	public static String getDomain() {
	    return getWebappDto().getDomain();
	}
}
