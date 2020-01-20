package com.yjs.dataSource;

/**
* 
* @descrice 多个登录用户可能需要同时切换数据源，所以这里需要写一个线程安全的ThreadLocal
* @more 用户切换数据源只要在程序中使用 DataSourceSwitch.set("1") 即可完成数据源切换
*/ 
public class DataSourceSwitch {
	
	
		
	private DataSourceSwitch(){
		
	}
	
	public static void set(String dsKey){
		DynamicDataSourceContextHolder.setDataSourceType(dsKey);  
		
	}
	
	public static void setBaseDataSource(){
		DynamicDataSourceContextHolder.setDataSourceType(null);  
		
	}
	
	public static void clean(){
		DynamicDataSourceContextHolder.clearDataSourceType();
        
	}
	

}
