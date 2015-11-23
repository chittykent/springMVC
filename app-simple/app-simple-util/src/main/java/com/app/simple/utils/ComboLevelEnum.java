package com.app.simple.utils;

public enum ComboLevelEnum {
	LEVEL1("级别1"), 
	LEVEL2("级别2"),
	LEVEL3("级别3"),
	LEVEL4("级别4"),
	LEVEL5("级别5"),
	LEVEL6("级别6"),
	LEVEL7("级别7"),
	LEVEL8("级别8"),
	LEVEL9("级别9"),
	LEVEL10("级别10");
	
	private String name ;
	
	private ComboLevelEnum(String name){
		this.name = name;
	}
	
	public static String getNameByLevel(String level){
		String levelName = "";
		switch(level){
		case "1": 
			levelName = ComboLevelEnum.LEVEL1.name;
			break;
		case "2": 
			levelName = ComboLevelEnum.LEVEL2.name;
			break;
		case "3": 
			levelName = ComboLevelEnum.LEVEL3.name;
			break;
		case "4": 
			levelName = ComboLevelEnum.LEVEL4.name;
			break;
		case "5": 
			levelName = ComboLevelEnum.LEVEL5.name;
			break;
		case "6": 
			levelName = ComboLevelEnum.LEVEL6.name;
			break;
		case "7": 
			levelName = ComboLevelEnum.LEVEL7.name;
			break;
		case "8": 
			levelName = ComboLevelEnum.LEVEL8.name;
			break;
		case "9": 
			levelName = ComboLevelEnum.LEVEL9.name;
			break;
		case "10": 
			levelName = ComboLevelEnum.LEVEL10.name;
			break;
		}
		
		return levelName ;
	}
}
