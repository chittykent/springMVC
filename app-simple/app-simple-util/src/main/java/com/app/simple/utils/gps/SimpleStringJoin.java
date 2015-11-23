/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.app.simple.utils.gps;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 
 * @packageName com.dong.util
 * 
 * @createClass class SimpleStringJoin.java
 * 
 * @description <p>
 *              //服务器对应终端相应的字符拼接
 * 
 *              </p>
 * @author Chris Yu
 * 
 * @mail chao.yu.@sim.com
 * 
 * @createTime 2014-5-9 上午11:01:17
 * 
 * @version 1.0
 */
public class SimpleStringJoin {

	// 应答字符前缀
	private  static String ANSWER_PREFIX = "[";
	// 应答字符后缀
	private  static String ANSWER_SUFFIX = "]";
	// 应答连接字符
	private  static String JOIN_STR = ",";
	// 格式化日期字符串
	private  static String DATE_FROMAT_STR = "yyyy-MM-dd HH:mm:ss";

	private StringBuilder simpleStrJoin = new StringBuilder();

	public static SimpleStringJoin newStrJoin() {
		return new SimpleStringJoin();
	}

	/**
	 * 
	 * 
	 * @description <p>
	 *              拼接前部分字符
	 *              </p>
	 * 
	 * @Author Chris Yu
	 * 
	 * @createDate 2014-5-9 上午11:06:52
	 * 
	 * @return
	 */
	public SimpleStringJoin startJoin() {
		simpleStrJoin.append(ANSWER_PREFIX);
		simpleStrJoin.append(DateFormatUtils.format(new Date(),DATE_FROMAT_STR));
		return this;
	}
	
	public SimpleStringJoin startJoin(String currentDate) {
		simpleStrJoin.append(ANSWER_PREFIX);
		simpleStrJoin.append(currentDate);
		return this;
	}

	/**
	 * 
	 * 
	 * @description <p>
	 *              字符分隔符拼接
	 *              </p>
	 * 
	 * @Author Chris Yu
	 * 
	 * @createDate 2014-5-9 上午11:08:59
	 * 
	 * @return
	 */
	public SimpleStringJoin connectSymbol() {
		simpleStrJoin.append(JOIN_STR);
		return this;
	}

	/**
	 * 
	 * 
	 * @description <p>
	 *              字符自定数据拼接，重复使用
	 *              egg：
	 *              CustomdataJoin("a").connectSymbol().CustomdataJoin("b")
	 *              </p>
	 * 
	 * @Author Chris Yu
	 * 
	 * @createDate 2014-5-9 上午11:11:41
	 * 
	 * @return
	 */
	public SimpleStringJoin customContext(String CustomStr) {
		simpleStrJoin.append(CustomStr);
		return this;
	}

	/**
	 * 
	 * 
	 * @description <p>
	 *              字符结束符拼接
	 *              </p>
	 * 
	 * @Author Chris Yu
	 * 
	 * @createDate 2014-5-9 上午11:10:44
	 * 
	 * @return
	 */
	public SimpleStringJoin endStrJoin() {
		simpleStrJoin.append(ANSWER_SUFFIX);
		return this;
	}

	public String build() {
		return simpleStrJoin.toString();
	}
}
