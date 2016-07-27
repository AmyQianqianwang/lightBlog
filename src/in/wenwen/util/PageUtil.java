package in.wenwen.util;

import java.text.MessageFormat;

public class PageUtil {
	public static String page(String pattern, long arg0) {
		return MessageFormat.format(pattern, arg0);
	}
}
