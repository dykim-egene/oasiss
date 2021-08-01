<%@ page contentType="text/html; charset=utf-8"  %>
<%@ include file="/xefc/jsp/common/import.jspf" %>
<%
Log.biz.info("!!!!!!!!!!!!");
	Box box = com.steg.util.AttachUtil.getBox(request, "CKE");
	//Log.biz.info("box:" + box);
	Box obox = HttpUtility.getBox(request);
	//Log.biz.info(">>>>>>>>>>>>>obox:" + obox);

	String fid = StringUtil.valid(box.get("fid")) ? box.get("fid") : obox.get("fid");
	
	com.steg.efc.Texts texts = com.steg.efc.Texts.getInstance();
	String att_id = "";
	String file_name = "";
	String file_path = "";
	
	
	Data img_d = new Data();
	
	JSONObject jobj = new JSONObject();
	boolean is_err = false;
	String err_msg = "";
	
	String except = box.get("exception");
	
	if(except.equals("filesize")){
		Log.biz.info("!!!!!!!!!!!1!");
		try{     
			GlobalConfig gc = GlobalConfig.getInstance();
			int fsize = gc.getFileMaxsize();
			is_err = true;
			err_msg = texts.getText("파일사이즈의 제한이 있습니다." ,session) + "[" + fsize + "]mb " + texts.getText("이하" ,session);
			
			img_d.put("is_err", is_err);
			img_d.put("err_msg", err_msg);
	    
			out.clear();
			out.println(uploadImg(img_d));
			
			out.flush();
		}catch(Exception e){
			Log.biz.err("Save",e);
		}
		return;
	}

	ICE ice = ICE.getInstance();
	EntityMap map = ice.map();
	Entity ent = map.getEntity("FILE");
	Sqls sqls = ice.sqls();

	HashMap hashMap = new HashMap();
	hashMap.put("tname", ent.getFileTableName());

	// Box에 저장된 파일관련 정보
	List _files = (List) box.getObject("_files");
	for(int i=0; _files != null && i<_files.size(); i++){

		Data d = (Data) _files.get(i);
	
		// ------------------------------------
		d.get("att_id");     //key
		d.get("org_name");     //원본파일명
		d.get("name");             //물리파일명
		file_name = d.get("name");
		d.get("path");              //첨부파일디렉토리
		file_path = d.get("path");
		d.get("contentType");    // 파일타입
		d.get("size");                // 사이즈

		String fileExtend = file_name.substring(file_name.lastIndexOf(".") + 1, file_name.length());
		
		Log.biz.info("fileExtend : " + fileExtend);
		if(!fileExtend.equals("jpg") && !fileExtend.equals("gif") && !fileExtend.equals("png") && !fileExtend.equals("jpeg") && !fileExtend.equals("bmp")) {

			File file = new File(d.get("dir"), d.get("name"));
			if(file.exists()) {
				file.delete();
			}
			
			is_err = true;
			err_msg = texts.getText("이미지 파일만 선택할 수 있습니다." ,session);
			
			img_d.put("is_err", is_err);
			img_d.put("err_msg", err_msg);
	    
			out.clear();
			out.println(uploadImg(img_d));
			
			return;
		}

		// -------------------------------------------------
		// File Table Insert
		// -------------------------------------------------
		String sql_id = "File.Attach.Insert";

		Data data = new Data();

		data.put("key", fid);
		data.put("att_tas_id", "");
		data.put("att_cat_cd", "");
		data.put("att_emp_id", box.get("current.emp_id"));
		data.put("att_orgname", d.get("org_name"));
		data.put("att_filename", d.get("name"));
		data.put("att_path", d.get("path"));
		data.put("att_type", d.get("contentType"));
		data.put("att_size", d.get("size"));
		data.put("att_cnt", "");

		TrxResult tr = sqls.execute(sql_id, hashMap, data);
		att_id = tr.getKey();
		

		img_d.put("is_err", is_err);
		img_d.put("fileName", d.get("org_name"));
		img_d.put("key", att_id);

		out.clear();
		out.println(uploadImg(img_d));
	}
	
	if(_files == null || _files.size() < 1) {

		is_err = true;
		err_msg = texts.getText("이미지 파일이 아닙니다." ,session);
		
		img_d.put("is_err", is_err);
		img_d.put("err_msg", err_msg);
    
		out.clear();
		out.println(uploadImg(img_d));
	}
	
%>
<%!
	public JSONObject uploadImg (Data d) {
	
		JSONObject job = new JSONObject();
		job.put("uploaded", d.getBoolValue("is_err"));
		//Log.biz.info("[uploadImg]>>>>>>>>>>>>" + d.toString());
		if(!d.getBoolean("is_err")) {
			job.put("fileName", d.get("fileName"));
			job.put("url", "/xplugin/ckeditor/img.jsp?key=" + d.get("key"));
		} else {
			JSONObject eob = new JSONObject();
			eob.put("message", d.get("err_msg"));
			
			job.put("error", eob);
		}
		return job;
	}
%>