package com.oasis.util.security;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;

import com.oasis.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * DB 컬럼 암호화 유틸
 */
@Slf4j
public class DBSecure {
	static DBSecure sec = new DBSecure();

	ISecure secure = null;
	boolean isSecure = false;	// 암호화 여부
	String secureClass = "";	// 암호화 Class
	HashMap queryColumns = null;
	HashMap sqlColumns = null;
	HashMap entColumns = null;
	HashMap sqls = null;

	@Value("${dbsecure}") private String dbsecure;
	@Value("${dbsecure.class}") private String dbsecure_class;

	@Value("${dbsecure.query.cols}") private String queryColStr;
	@Value("${dbsecure.dml.sql.cols}") private String sqlColStr;
	@Value("${dbsecure.dml.entity.fields}") private String entColStr;
	
	private DBSecure(){
		init();
	}
	
	public static DBSecure getInstance(){
		return sec;
	}
	
	public boolean isSecure(){
		return isSecure;
	}
	
	public boolean isSecureColumn(String col){
		return isSecure && queryColumns.containsKey(col.toUpperCase());
	}

	/**
	 * Entity 의 컬럼이 암호화 대상 컬럼인지
	 * @param ent_id 대상 엔터티
	 * @param col 컬럼 명
	 * @return
	 */
	public boolean isSecureEntityColumn(String ent_id, String col){
		String id = ent_id + ":" + col.toUpperCase();
		return isSecure && entColumns.containsKey(id);
	}
	
	public boolean isSecureSqlColumn(String sql_id, String col){
		String id = sql_id + ":" + col.toUpperCase();
		return isSecure && sqlColumns.containsKey(id);
	}
	
	public boolean isSecureSql(String sql_id){
		return isSecure && sqls.containsKey(sql_id);
	}

	/**
	 * 초기화
	 */
	public void init() {
		isSecure = false;
		secure = null;
		queryColumns = new HashMap();
		sqlColumns = new HashMap();
		entColumns = new HashMap();
		sqls = new HashMap();
		
		secureClass = "";
		queryColStr = "";
		sqlColStr = "";
		entColStr = "";
		/* 
		 * dbsecure = true
		 * dbsecure.class = [AES|AES256|SHA|RSA|com.xxx.xxx.XXXX]
		 * dbsecure.query.cols = col1,col2,col3,...
		 */
		
		// 암호화 여부
		if(!StringUtil.valid(dbsecure) || !dbsecure.toLowerCase().equals("true")) return;
		
		if(!StringUtil.valid(dbsecure_class)) return;
		
		log.info("DBSecure initing...");
		SecureUtil sutil = SecureUtil.getInstance();
		secure = sutil.getSecure(secureClass);
		
		log.info("secure_gubun : " + secure);
		
		if(secure == null){
			log.error("["+secureClass + "] not found.");
			return;
		}

		try{
			// SQL 중 컬럼
			String[] arr = StringUtil.getArray(queryColStr,';');
			for(int i=0; i<arr.length; i++){
				queryColumns.put(arr[i].toUpperCase().trim(), "1");
			}

			arr = StringUtil.getArray(sqlColStr,';');
			for(int i=0; i<arr.length; i++){
				String[] arr1 = StringUtil.getArray(arr[i].trim(),':');
				if(arr1.length != 2) continue;

				String id = arr1[0];
				sqls.put(id, "1");
				String[] arr2 = StringUtil.getArray(arr1[1].toUpperCase(),',');
				for(int j=0; j<arr2.length; j++){
					sqlColumns.put(id + ":" + arr2[j], "1");
				}
			}

			arr = StringUtil.getArray(entColStr,';');
			for(int i=0; i<arr.length; i++){
				String[] arr1 = StringUtil.getArray(arr[i].trim(),':');
				if(arr1.length != 2) continue;
				String id = arr1[0];
				String[] arr2 = StringUtil.getArray(arr1[1].toUpperCase(),',');
				for(int j=0; j<arr2.length; j++){
					entColumns.put(id + ":" + arr2[j], "1");
				}
			}

			isSecure = true;
		}catch(Exception e){
			log.error("DBSecure init Error",e);
		}
		log.info(
				"\n------------------------------------" +
				"\nSecure : " + isSecure() +
				"\nSecure Class : " + secureClass +
				"\nSecure Query Columns : " + queryColStr +
				"\nSecure Entity Fields : " + entColStr +
				"\nSecure Sql Columns : " + sqlColStr +
				"\n------------------------------------" 
		);
	}

	/**
	 * Entity 컬럼의 암호화 방식을 설정
	 * @param entId 암호화 대상 엔터티
	 * @param column 암호화 대상 컬럼
	 * @return Secure 컬럼 여부
	 */
	public boolean addSecureEntityColumn(String entId, String column) {
		entColumns.put(entId + ":" + column, "1");

		return true;
	}

	/**
	 * 복호화
	 * @param s
	 * @return
	 */
	public String encrypt(String s){
		if(secure != null) return secure.encrypt(s);
		return s;
	}

	/**
	 * 암호화 대상컬럼의 방식을 받아서 복호화
	 * @param secureCls
	 * @param s
	 * @return
	 */
	public String encrypt(String secureCls, String s){
		if(StringUtil.invalid(secureCls)) {
			secureCls = secureClass;	// 이런 경우는 없지만 Secureclass가 없는 경우 slim.cfg에 설정된 클래스로 설정
		}
		ISecure tmpSecure = SecureUtil.getInstance().getSecure(secureCls);

		if(tmpSecure != null) return tmpSecure.encrypt(s);

		return s;
	}

	/**
	 * 암호화
	 * @param s
	 * @return
	 */
	public String decrypt(String s){
		if(secure != null) return secure.decrypt(s);
		return s;
	}


	/**
	 * 암호화
	 * @param secureCls
	 * @param s
	 * @return
	 */
	public String decrypt(String secureCls, String s){

		if(StringUtil.invalid(secureCls)) {
			secureCls = secureClass;	// 이런 경우는 없지만 Secureclass가 없는 경우 slim.cfg에 설정된 클래스로 설정
		}
		ISecure tmpSecure = SecureUtil.getInstance().getSecure(secureCls);


		if(secure != null) return tmpSecure.decrypt(s);
		return s;
	}
	
	public static void reset(){
		sec.init();
	}

	/* SECURE-201911 */
	/*
	public static void main(String[] args) {
		ISecure secure = null;
		SecureUtil sutil = SecureUtil.getInstance();
		secure = sutil.getSecure("AES256");
		System.out.println("AAAA : " + secure);
		if(secure == null){
			System.out.println("not found.");
			//return;
		}		
	}	*/
}
