package com.survey.keyvalue.config;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.pool.impl.GenericObjectPool.Config;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class DbConfig {
	
	private long timeBetweenEvictionRunsMillis;
	
	private int maxActive;
	
	private int maxIdle;
	
	private int maxWait;
	
	private boolean testWhileIdle;
	
	private Map<String,DbInfo> masterDbInfoMap;
	
	private Map<String,DbInfo> slaveDbInfoMap;
	
	public DbConfig(String dbConfClassPath) {
		super();
		masterDbInfoMap=new HashMap<String, DbConfig.DbInfo>();
		slaveDbInfoMap=new HashMap<String, DbConfig.DbInfo>();
		this.loadDbConf(dbConfClassPath);
	}
	
	private void loadDbConf(String dbConfClassPath){
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
		    InputStream confInput = loader.getResourceAsStream(dbConfClassPath);
			SAXReader reader=new SAXReader();
			Document doc=reader.read(confInput);
			Element root=doc.getRootElement();
			this.timeBetweenEvictionRunsMillis=Long.parseLong(root.elementText("timeBetweenEvictionRunsMillis"));
			this.maxActive=Integer.parseInt(root.elementText("maxActive"));
			this.maxIdle=Integer.parseInt(root.elementText("maxIdle"));
			this.maxWait=Integer.parseInt(root.elementText("maxWait"));
			this.testWhileIdle=Boolean.parseBoolean(root.elementText("maxActive"));
			
			Element masterEle=root.element("masters");
			Map<String,DbInfo> masterDbInfoMap=this.parseDbInfo(masterEle.elements());
			Element slavesEle=root.element("slaves");
			Map<String,DbInfo> slaveDbInfoMap=this.parseDbInfo(slavesEle.elements());
			
			this.checkMasterSlave(masterDbInfoMap, slaveDbInfoMap);
			
			this.masterDbInfoMap.putAll(masterDbInfoMap);
			this.slaveDbInfoMap.putAll(slaveDbInfoMap);
			
		} catch (Exception e) {
			throw new RuntimeException("loadDbConf failed !"+e.getMessage());
		}
	}
	
	private void checkMasterSlave(Map<String,DbInfo> masterDbInfoMap,Map<String,DbInfo> slaveDbInfoMap){
		if(masterDbInfoMap.size()!=slaveDbInfoMap.size()){
			throw new RuntimeException("master and slave must equal size!");
		}
		for(String name:masterDbInfoMap.keySet()){
			if(!slaveDbInfoMap.containsKey(name)){
				throw new RuntimeException("slave have not master name:"+name);	
			}
		}
	}
	
	private Map<String,DbInfo> parseDbInfo(List<Element> dbInfoEles){
		if(dbInfoEles==null||dbInfoEles.isEmpty()){
			return Collections.emptyMap();
		}
		Map<String,DbInfo> masterDbInfoMap = new HashMap<String, DbConfig.DbInfo>();
		for(Element ele:dbInfoEles){
			String name=ele.elementText("name");
			String ip=ele.elementText("ip");
			String port=ele.elementText("port");
			masterDbInfoMap.put(name, new DbInfo(name, ip, Integer.parseInt(port)));
		}
		return masterDbInfoMap;
	}

	public Map<String, DbInfo> getMasterDbInfoMap() {
		return masterDbInfoMap;
	}

	public void setMasterDbInfoMap(Map<String, DbInfo> masterDbInfoMap) {
		this.masterDbInfoMap = masterDbInfoMap;
	}

	public Map<String, DbInfo> getSlaveDbInfoMap() {
		return slaveDbInfoMap;
	}

	public void setSlaveDbInfoMap(Map<String, DbInfo> slaveDbInfoMap) {
		this.slaveDbInfoMap = slaveDbInfoMap;
	}
	

	public long getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}


	public static class DbInfo{
		private String name;
		private String ip;
		private int port;
		
		private DbInfo(String name, String ip, int port) {
			super();
			this.name = name;
			this.ip = ip;
			this.port = port;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public int getPort() {
			return port;
		}
		public void setPort(int port) {
			this.port = port;
		}
		
	}
}
