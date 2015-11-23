package com.app.simple.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 公共验证类。
 */
public class ValidateUtil {
	/**
	 * 验证email是否合法
	 */
	public static boolean validateEmail(String email) {
		
		if(email==null || email.equals(""))
			return false;
		
		if(email.length()>100)
			return false;

		String objForm = email;
		if (objForm == "")
			return false;

		int num = 1;

		int start = objForm.indexOf("@", 0);
		if (start < 1) {
			return false;
		}
		for (int i = 0; objForm.indexOf("@", start + 1) != -1; i++) {
			start = objForm.indexOf("@", start + 1);
			num++;
			if (num > 1)
				break;
		}
		if (num != 1)
			return false;

		Pattern re = Pattern.compile(
				"\\s*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$");
		Matcher regexMatcher = re.matcher(objForm);
		return regexMatcher.find();
		
//		RE re = new RE(
//		"\\s*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$");
//				return re.match(objForm);
		
//		return true;
	}
	
	/**
	 * 验证电话号码（传真号码）是否合法
	 * @param phoneNo
	 * @return
	 */
	public static boolean validatePhoneNo(String phoneNo){
		if(phoneNo==null || phoneNo.equals(""))
			return false;
		
//		PatternCompiler compliler = new  Perl5Compiler();
//		Pattern pattern = null;
//		try {
//			//pattern = compliler.compile("(^\\d{1,4}\\-\\d{3,4}\\-\\d{5,}$)|(^\\d{3,4}\\-\\d{5,}$)|(^\\d{5,}$)|(^\\(\\d{3,4}\\)\\d{5,}$)|(^\\(\\d{1,4}\\)\\(\\d{3,4}\\)\\d{5,}$)");
//			pattern = compliler.compile("^\\d*$");
//		} catch (MalformedPatternException e) {
//			e.printStackTrace();
//			return false;
//		}
//		
//		if(pattern==null)
//			return false;
//		
//		PatternMatcher matcher = new Perl5Matcher();
////		return matcher.matches(phoneNo,pattern );
//		return matcher.contains(phoneNo,pattern );
		
		
		//RE re = new RE("(^\\d{1,4}\\-[0-9]{3,4}\\-[0-9]{5,}$)|(^[0-9]{3,4}\\-[0-9]{5,}$)|(^[0-9]+$)|(^\\([0-9]{3,4}\\)[0-9]{5,}$)|(^\\([0-9]{1,4}\\)\\([0-9]{3,4}\\)[0-9]{5,}$)");   
		//RE re = new RE("(^\\d{1,4}\\-\\d{3,4}\\-\\d{5,}*$)|(^\\d{3,4}\\-\\d{5,}$)|(^\\d*$)|(^\\(\\d{3,4}\\)\\d{5,}$)|(^\\(\\d{1,4}\\)\\(\\d{3,4}\\)\\d{5,}$)");   
//		RE re = new RE("(^\\d{1,4}\\-\\d{3,4}\\-\\d{5,}*$)");   
		
//		return re.match(phoneNo);
		
		StringBuffer buf = new StringBuffer("");
		buf.append("(^[0-9]{1,4}\\-[0-9]{3,4}\\-[0-9]{5,}(\\-[0-9]{1,6}){0,1}$)|");
		buf.append("(^[0-9]{3,4}\\-[0-9]{5,}$)|");
		buf.append("(^[0-9]{5,}(\\-[0-9]{1,6}){0,1}$)|");
		buf.append("(^\\([0-9]{3,4}\\)[0-9]{5,}(\\-[0-9]{1,6}){0,1}$)|");
		buf.append("(^\\([0-9]{1,4}\\)\\([0-9]{3,4}\\)[0-9]{5,}(\\-[0-9]{1,6}){0,1}$)");

		Pattern re = Pattern.compile(buf.toString());
		Matcher regexMatcher = re.matcher(phoneNo);
		return regexMatcher.find();
		
	}
	
	/**
	 * 验证手机号是否合法
	 * @param mobileNo
	 * @return
	 */
	public static boolean validateMobileNo(String mobileNo){
		if(mobileNo==null || mobileNo.equals(""))
			return false;
		
//		//RE re = new RE("(^[0-9]{1,4}\\-[0-9]{3,4}\\-[0-9]{5,}$)|(^[0-9]{3,4}\\-[0-9]{5,}$)|(^[0-9]*$)|(^\\([0-9]{3,4}\\)[0-9]{5,}$)|(^\\([0-9]{1,4}\\)\\([0-9]{3,4}\\)[0-9]{5,}$)");   
//		RE re = new RE("(^[0-9]{1,4}\\-{0,1}1\\d*$)|(^\\([0-9]{1,4}\\)1[0-9]*$)|(^1[0-9]*$)");
//		return re.match(mobileNo);
		
		Pattern re = Pattern.compile("(^[0-9]{1,4}\\-{0,1}1\\d*$)|(^\\([0-9]{1,4}\\)1[0-9]*$)|(^1[0-9]*$)");
		Matcher regexMatcher = re.matcher(mobileNo);
		return regexMatcher.find();
	}
	
	/**
	 * 验证url是否是一个合法的url。
	 */
	public static boolean validateUrl(String url){
		
		if(url==null || url.equals("") || url.length()<=6)
			return false;
		
		String array = url.substring(0,4);
		if(!array.equalsIgnoreCase("http")){
			return false;
		}else{
			char ch = url.charAt(4);
			if(ch=='s' || ch=='S'){
				StringBuffer buf = new StringBuffer("https");
				buf.append(url.substring(5,url.length()));
				url = buf.toString();
			}else{
				String u = url.substring(4,url.length());
				url=array.toLowerCase()+u;
			}				
		}
		
		Pattern re = Pattern.compile("^((http|https)://){0,1}(([a-zA-Z0-9_-]|((\\.){0,1}))+)(:[1-9][0-9]*)?(/[^/]+)*/?$");
		Matcher regexMatcher = re.matcher(url);
		return regexMatcher.find();	
		
	}
	
	/**
	 * 验证日期格式是否合法
	 * @param date
	 * @return
	 */
	public static boolean validateDate(String date){
		Pattern re = Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}");
		Matcher regexMatcher = re.matcher(date);
		if(regexMatcher.find()){
			String[] d = date.split("-");
			int year = Integer.parseInt(d[0]);
			if(year<1901 || year>2099){
				return false;
			}
			int month = Integer.parseInt(d[1]);
			if(month<1||month>12){
				return false;
			}
			int day = Integer.parseInt(d[2]);
			if(day<1 || day>31){
				return false;
			}
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * 验证年格式是否合法
	 * @param date
	 * @return
	 */
	public static boolean validateYear(String date){
		Pattern re = Pattern.compile("^[0-9]{4}");
		Matcher regexMatcher = re.matcher(date);
		if(regexMatcher.find()){
			int year = Integer.parseInt(date);
			if(year<1901 || year>2099){
				return false;
			}
			return true;
		}else{
			return false;
		}
		
	}
	
	public static void main(String[] args){
//		boolean result = validateUrl("htt");
//		System.out.println(result);
//		String email = "zhbd234@kkkk";
//		System.out.println(ValidateUtil.validateEmail(email));
		String date = "1999";
		System.out.println(ValidateUtil.validateYear(date));
		
	}
	
}
