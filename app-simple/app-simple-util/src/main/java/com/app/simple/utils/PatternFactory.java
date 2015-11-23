package com.app.simple.utils;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections.FastHashMap;

/**
 * <p>公共方法类</p>
 * <p>正则表达式pattern工厂</p>
 * @version 1.0
 *
 */

public class PatternFactory {
        static Map patternMap = new FastHashMap();

        public PatternFactory() {
        }

        /**
         * 获取正则表达式的pattern
         *
         * @param regExp -
         *            正则表达式
         * @return Pattern
         * @throws MalformedPatternException
         */
        public static Pattern getPattern(String regExp){
                Pattern pattern = null;
                if (patternMap.containsKey(regExp)) {
                        pattern = (Pattern) patternMap.get(regExp);
                } else {
                        pattern =  Pattern.compile(regExp);
                        patternMap.put(regExp, pattern);
                }
                return pattern;
        }
}
