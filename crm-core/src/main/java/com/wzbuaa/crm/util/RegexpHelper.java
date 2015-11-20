package com.wzbuaa.crm.util;

import org.apache.oro.text.GlobCompiler;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Matcher;

public class RegexpHelper {
	
	private static String separator = ",";
	
	/**
	 * 根据正则表达式匹配字符，正则表达式可以是一个逗号表达式数组，则分别匹配
	 * @param str
	 * @param patternstr
	 * @return
	 */
	public static final boolean isGlobmatches(String str, String patternstr) {
        if(patternstr == null || "".equals(patternstr)){
            return false;
        }
        GlobCompiler compiler = new GlobCompiler();
        PatternMatcher matcher = new Perl5Matcher();
        if(patternstr.indexOf(separator) == -1){
        	Pattern pattern = null;
			try {
				pattern = compiler.compile(patternstr);
			} catch (MalformedPatternException e) {
				e.printStackTrace();
			}
            return matcher.matches(str, pattern);
        }
        
        String[] patterns = patternstr.split(separator);
        for(int i = 0; i<patterns.length; i++){
        	Pattern pattern = null;
			try {
				pattern = compiler.compile(patterns[i]);
			} catch (MalformedPatternException e) {
				e.printStackTrace();
			}
	        boolean result = matcher.matches(str, pattern);
	        if(result){
	            return result;
	        }
	        continue; 
        }
        return false;
    }
	
	public static void main(String[] args) {
//		System.out.println(isGlobmatches("sdfdf.jar", "*.jar"));
        System.out.println(isGlobmatches("com/cn/list.htm", "com/cn/list.htm*,com/cn/main.htm"));
	}
}
