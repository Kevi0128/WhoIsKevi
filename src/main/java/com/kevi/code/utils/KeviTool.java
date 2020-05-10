package com.kevi.code.utils;

import com.sun.corba.se.impl.orbutil.closure.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class KeviTool {

	private final static Logger logger = LoggerFactory.getLogger(com.kevi.code.utils.KeviTool.class);

	public static String GetRealPath(){
		String realpath = "";
		String getpath = Constant.class.getClassLoader().getResource("").getPath();
		char[] cleanpath = getpath.toCharArray();
		for(int i=1;i<getpath.length()-14;i++){
			realpath = realpath + cleanpath[i];
		}
		logger.info("RealPath==>"+realpath);
		return realpath;
	}

	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 *
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
	 * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 *
	 * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
	 * 192.168.1.100
	 *
	 * 用户真实IP为： 192.168.1.110
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	private static String reg_sql = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
	            + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
	private static Pattern sqlPattern = Pattern.compile(reg_sql, Pattern.CASE_INSENSITIVE);
	public static boolean isValid(String str, HttpServletRequest request) {
		HttpSession session = request.getSession();
	    if (sqlPattern.matcher(str).find()) {
	        logger.info("未能通过SQL过滤器");
	        String ip = request.getRemoteAddr();
			logger.info("警告！IP:"+ip+"发现SQL注入!");
			session.setAttribute("loginmessagestate", 1);
			session.setAttribute("loginmessage", "发现SQL注入！！！");
	        return false;
	    }
	    return true;
	}

	private static String reg_phone = "^1(3|4|5|7|8)\\d{9}$";
	private static Pattern phonePattern = Pattern.compile(reg_phone, Pattern.CASE_INSENSITIVE);

	public static int getrandom(final int min, final int max) {
		Random rand = new Random();
		int tmp = Math.abs(rand.nextInt());
		int therandom = tmp % (max - min + 1) + min;
		logger.info("生成的随机数为："+therandom);
		return therandom;
	}

	public static int CreateUserId_8() {
		Random rand = new Random();
		int tmp = Math.abs(rand.nextInt());
		int therandom = tmp % (9999 - 1000 + 1) + 1000;
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy");
		String time=format.format(date);
		int fournum = Integer.parseInt(time);
		int userID = fournum*10000+therandom;
		logger.info("用户生成ID:"+userID);
		return userID;
	}

	public static long CreateUserId() {
		Random rand = new Random();
		int tmp = Math.abs(rand.nextInt());
		int therandom = tmp % (9999 - 1000 + 1) + 1000;
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy");
		String time=format.format(date);
		int sixnum = Integer.parseInt(time);
		long userID = (long)sixnum*10000+therandom;
		logger.info("用户生成ID:"+userID);
		return userID;
	}

	/**
	 * 空值检测
	 * @return
	 */
	public static boolean isVoid(String a){
		if (a == null || "".equals(a) || a.length() == 0){
			return true;
		}
		return false;
	}

	public static boolean isVoid(String... strList){
		for (int i=0; i<strList.length; i++){
			String a = strList[i];
			if (a == null || "".equals(a) || a.length() == 0){
				return true;
			}
		}
		return false;
	}

	/**
	 * 检测字符串中是否存在SQL
	 * @param str 需要检测的字符串
	 * @return true 存在 false 不存在
	 */
	public static boolean SQLCheck(String str) {
		if (isVoid(str)) {
			//空值字符串直接跳过判断
			return false;
		}
		if (sqlPattern.matcher(str).find()) {
			logger.info("未能通过SQL过滤器");
			return true;
		}
		return false;
	}

	public static boolean SQLCheck(String... strList) {
		for (int i=0; i<strList.length; i++){
			String str = strList[i];
			if (isVoid(str)){
				//空值直接判断下一个
				continue;
			}
			if (sqlPattern.matcher(str).find()) {
				logger.info("未能通过SQL过滤器");
				return true;
			}
		}
		return false;
	}

	/**
	 * 简单检测字符串是否符合手机号规则
	 * @param str 需要检测的字符串
	 * @return true 是 false 不是
	 */
	public static boolean phoneCheck(String str) {
		if (phonePattern.matcher(str).find()) {
			return true;
		}
		logger.info("未能通过手机号过滤");
		return false;
	}

	private final static String reg_number = "^+?[0-9]+$"; //[1, max)
	private final static Pattern numberPattern = Pattern.compile(reg_number, Pattern.CASE_INSENSITIVE);

	public static boolean numberCheck(String str){
		if (numberPattern.matcher(str).find()) {
			return true;
		}
		logger.info("未能通过纯数字过滤");
		return false;
	}

	/*******************************************检测字符串长度*******************************************************/
	public static Integer strLenthReplace(String str){
		return str.replace(" ","").length();
	}

	public static Integer strLenthTrim(String str){
		return str.trim().length();
	}

	/********************************************时间快捷操作********************************************************/
	public static Calendar setTime(int hour, int minute, int second, int millisecond){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, millisecond);
		return calendar;
	}

	public static Calendar setTime(Date date, int hour, int minute, int second, int millisecond){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return setTime(calendar, hour, minute, second, millisecond);
	}

	public static Calendar setTime(Calendar calendar, int hour, int minute, int second, int millisecond){
		calendar.set(Calendar.MILLISECOND, millisecond);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		return calendar;
	}

	public static Calendar setTime(int day, int hour, int minute, int second, int millisecond){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return setTime(calendar, hour, minute, second, millisecond);
	}

	//等待时间严重不准，需要改进
	public static void waitTime_ms(int time){
		Date start = new Date();
		for (; ; ) {
			Date nowT = new Date();
			if (nowT.getTime() - start.getTime() > time) {
				logger.info( (nowT.getTime() - start.getTime())+ "ms");
				break;
			}
		}
	}

	public static void waitTime_s(int time){
		Date start = new Date();
		for (; ; ) {
			Date nowT = new Date();
			if (nowT.getTime() - start.getTime() > (time * 1000)) {
				break;
			}
		}
	}

	/************************************************Gzip压缩解压**************************************************/

	public static String inGzip(String fullString){
		ByteArrayOutputStream out = null;
		GZIPOutputStream gzip = null;
		try {
			if (fullString == null || fullString.length() == 0)
				return "void";
			out = new ByteArrayOutputStream();
			gzip = new GZIPOutputStream(out);
			gzip.write(fullString.getBytes());
//			String zipString = new BASE64Encoder().encode(out.toByteArray());
			gzip.flush();
		}catch (IOException e){
			e.printStackTrace();
			return "error";
		}finally {
			if (gzip != null) {
				try {
					gzip.close();
				}catch (IOException e){
					e.printStackTrace();
				}
			}
		}
//		return out.toString("ISO-8859-1");
		return new BASE64Encoder().encode(out.toByteArray());
	}

	public static String unGzip(String gzipString){
		try{
			if (gzipString == null)
				return "void";
			byte[] gzipByte = new BASE64Decoder().decodeBuffer(gzipString);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			byte[] gzipByte = gzipString.getBytes("ISO-8859-1");
			ByteArrayInputStream in = new ByteArrayInputStream(gzipByte);
			GZIPInputStream gzip = new GZIPInputStream(in);
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = gzip.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			out.flush();
			String fullString = out.toString();

			if (gzip != null)
				gzip.close();
			if (in != null)
				in.close();
			if (out != null)
				out.close();

			return fullString;
		}catch (IOException e){
			e.printStackTrace();
			return "error";
		}
	}


}