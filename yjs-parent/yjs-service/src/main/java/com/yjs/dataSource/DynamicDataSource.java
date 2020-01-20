package com.yjs.dataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;  


public class DynamicDataSource extends AbstractRoutingDataSource {  
  
    @Override  
    protected Object determineCurrentLookupKey() {  
    	  if(DynamicDataSourceContextHolder.getDataSourceType()==null){
    		  System.err.println("数据源为:===="+"主库");  
    	  }else{
    		  System.err.println("数据源为:===="+DynamicDataSourceContextHolder.getDataSourceType());
    	  }
    	  
          return DynamicDataSourceContextHolder.getDataSourceType();  
    }  
  
}  