<%@ page contentType="text/html; charset=utf-8"%><%@ page import="org.sdf.servlet.*,
                           org.sdf.rdb.*,
                           org.sdf.util.*,
                           org.sdf.log.*,
                           org.sdf.lang.*,
                           com.steg.org.json.simple.*"
                import="java.util.*,java.sql.*,
                				java.io.*,
                				java.nio.*,
                				java.nio.channels.*"
                import="com.steg.efc.*"
                import="java.awt.Image"
                import="javax.swing.ImageIcon"
%><%
	Box box = HttpUtility.getBox(request);	
	RecordSet rs = null;

	try {
		ICE ice = ICE.getInstance();
		EntityMap map = ice.map();
		Entity ent = map.getEntity("FILE");
		Sqls sqls = ice.sqls();

		if(!ent.hasFileTable) {
			return;
		}

		String fTableName =  ent.getFileTableName();
		HashMap hashmap = new HashMap();
		hashmap.put("tname", fTableName);

		if(box.valid("key")){

			Result r = sqls.getResult("UIItem.Attach.Select", hashmap, box);
			if(!r.isError){ 
				rs = r.getRecordSet();
			}
		}

		if(rs == null) {
			rs = new RecordSet();
		}

	} catch(Exception e) {
		Log.biz.err(e);
	}

	String id = "";
	String att_path = "";
	String att_filename = "";
	if(rs.next()) {
		id = rs.get("att_id");
		att_path = rs.get("att_path");
		att_filename = rs.get("att_filename");
	}

	File file = new File(att_path, att_filename);

	if(!file.exists()){
		return;
	}

	/**
	// 이미지 읽기
	Image img = new ImageIcon(f).getImage();

	int imgWidth = img.getWidth(null);		//가로 사이즈
	int imgHeight = img.getHeight(null);	//세로 사이즈

	Log.act.info("W : "+imgWidth);
	Log.act.info("H : "+imgHeight);
	**/

	FileInputStream fin = null;
	FileChannel inc = null;
	OutputStream os = null;
	WritableByteChannel outc = null;

	try {
		fin = new FileInputStream( file );
		inc = fin.getChannel();
		os = response.getOutputStream();
		outc = Channels.newChannel(os);

		ByteBuffer buf = ByteBuffer.allocateDirect(1024);
		while (true) {
		if(inc.read(buf) == -1) break;
			buf.flip();
			outc.write(buf);
			buf.clear(); 
		}
		
	}catch(FileNotFoundException fe){
    Log.dbg.debug(fe.getMessage());  /* 20150921-Logger */
  }catch(IOException ie){
    Log.dbg.debug(ie.getMessage());  /* 20150921-Logger */
	} catch(Exception e) {
		Log.biz.err("/ckeditor/img.jsp Error : " + e);
	} finally {
		try { fin.close(); } catch(Exception e) { Log.dbg.debug(e.getMessage());  /* 20150921-Logger */ }
		try { inc.close(); } catch(Exception e) { Log.dbg.debug(e.getMessage());  /* 20150921-Logger */ }
		try { os.close(); } catch(Exception e) { Log.dbg.debug(e.getMessage());  /* 20150921-Logger */ }
		try { outc.close(); } catch(Exception e) { Log.dbg.debug(e.getMessage());  /* 20150921-Logger */ }
	}
%>