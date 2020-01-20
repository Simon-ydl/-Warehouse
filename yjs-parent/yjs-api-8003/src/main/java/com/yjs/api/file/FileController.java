package com.yjs.api.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.yjs.bean.Ids;
import com.yjs.bean.department.Department;
import com.yjs.bean.dto.PageResult;
import com.yjs.bean.enclosure.Enclosure;
import com.yjs.bean.file.Files;
import com.yjs.bean.yjs.FileVo;
import com.yjs.dataSource.DataSourceSwitch;
import com.yjs.service.approver.service.ApproverService;
import com.yjs.service.department.Service.DepartmentService;
import com.yjs.service.enclosure.service.EnclosureService;
import com.yjs.service.file.FileService;
import com.yjs.service.item.ItemsService;
import com.yjs.utils.ApiAccess;
import com.yjs.utils.FileBase64ConvertUitl;
import com.yjs.utils.JsonKit;
import net.sf.json.JSONObject;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;


@Controller
@RequestMapping("/file")
public class FileController {

	@Autowired
	FileService fileService;
	@Autowired
	ItemsService itemsService;
	@Autowired
	private FdfsWebServer fdfsWebServer;
	@Autowired
	private FastFileStorageClient storageClient;
	@Autowired
	DepartmentService departmentService;

	@ResponseBody
	@RequestMapping("/delete")
	public String delete(@RequestBody String jsonStr,HttpSession session) {
		Map<String, Object> maps = JsonKit.toMap(jsonStr);
		Map<String, Object> map = new HashMap<>();
//		 String businessCode = maps.get("businessCode").toString();
		Files files = fileService.findAllById(Integer.parseInt(maps.get("id").toString()));
		if(files.getFileAgain() == 2){
			files.setFileAgain(1);
			fileService.update(files);
			map.put("status", "success");
			map.put("message", "删除成功");
			map.put("code", 1);
			return JsonKit.toJsonStr(map);
		}

		Department department = departmentService.findByItemsId(files.getItemId());
		if(department.getId() == 7){
			DataSourceSwitch.set("yth_dba");
			fileService.deleteFileVo(Integer.parseInt(maps.get("id").toString()));
		}
		DataSourceSwitch.set(null);
		fileService.delete(Integer.parseInt(maps.get("id").toString()));
//		 if (files.getItemId() == 10){
////			 DataSourceSwitch.set("ga_dba");
////			 fileService.deleteFileVo(Integer.parseInt(maps.get("id").toString()));
////		 }
////		 DataSourceSwitch.set(null);
////		 fileService.delete(Integer.parseInt(maps.get("id").toString()));

		StorePath storePath = StorePath.parseFromUrl(files.getFileUrl());
		storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
		map.put("status", "success");
		map.put("message", "删除成功");
		map.put("code", 1);
		return JsonKit.toJsonStr(map);

	}

	/**
	 * 文件上传
	 * @param request
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/fileload")
	@ResponseBody
	public String  springUpload(HttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Files files = new Files();
		long  startTime=System.currentTimeMillis();
		//将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
				request.getSession().getServletContext());
		//检查form中是否有enctype="multipart/form-data"
		if(multipartResolver.isMultipart(request))
		{

			//将request变成多部分request
			MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
			//获取multiRequest 中所有的文件名
			Iterator iter=multiRequest.getFileNames();
			String fileCode = multiRequest.getParameter("fileCode");
			int itemId = Integer.valueOf(multiRequest.getParameter("itemId"));
			String fileName =multiRequest.getParameter("fileName");
//				Items items = itemsService.findByItemCode(itemCode);
//				int itemId = items.getId();
			while(iter.hasNext()) {
				//一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				String path = multiRequest.getContextPath();
				String originalFileName = file.getOriginalFilename();
				//获取文件的后缀名 .jpg
				String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
				FileVo fileVo = new FileVo();
				if (file != null) {
					StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
					String businessCode = businessCode = multiRequest.getParameter("businessCode");
					session.setAttribute("businessCode", businessCode);
//					Files files1 = fileService.findAllByBusinessCodeAndfileName(businessCode,fileName);
//					if(files1 == null){
						files.setFileName(fileName);
						files.setOriginalFileName(originalFileName);
						String fileUrl = storePath.getFullPath();
						files.setFileUrl(fileUrl);
						files.setBusinessCode(businessCode);
						files.setItemId(itemId);
						files.setFileCode(fileCode);
						DataSourceSwitch.set(null);
						fileService.save(files);
						Files files2 = fileService.findAllByBusinessCodeAndfileUrl(businessCode,fileUrl);
						int ids = files2.getId();

//						List<Department> departmentList = departmentService.findByBusinessCode(businessCode);
						Department department = departmentService.findByItemsId(itemId);
//						for (Department department : departmentList){
							if(department.getId() == 7){
								DataSourceSwitch.set("yth_dba");
								StringBuffer buffer = new StringBuffer(fileName).append(suffix);
								String listobj = ApiAccess.StringApipost("uploadFileV2","encode_fileName=" + Utf8.encode(buffer) + "&" + "encode_fileContent=" + Utf8.encode(FileBase64ConvertUitl.multipartFile2base64(file)));
								Map mapobj = JsonKit.toMap(JsonKit.toJsonStr(listobj));
								if(Integer.valueOf(String.valueOf(mapobj.get("code"))) == 1){
//									System.out.println(mapobj.get("data").toString());
									Map mapfile = JsonKit.toMap(JsonKit.toJsonStr(mapobj.get("data")));
//									Map mapfile = JsonKit.toMap(JsonKit.toJsonStr(mapData.get("id")));
//									fileVo.setId(Integer.valueOf(String.valueOf(mapfile.get("id"))));
									fileVo.setNames(mapfile.get("name").toString());
									if(null != mapfile.get("nameEx")){
										fileVo.setNameEx(mapfile.get("nameEx").toString());
									}
									fileVo.setPath(mapfile.get("path").toString());
									fileVo.setSizes(mapfile.get("size").toString());
									fileVo.setBusinessCode(businessCode);
									fileVo.setItemId(itemId);
									fileVo.setFileId(ids);
									DataSourceSwitch.set("yth_dba");
									fileService.saveFileVo(fileVo); //保存到一件事
									DataSourceSwitch.set(null);
								}else{
									map.put("status", "false");
									map.put("message", "上传失败");
									map.put("code", 0);
									return JsonKit.toJsonStr(map);
								}
							}
//						}
						map.put("id", ids);
						map.put("status", "ok");
						map.put("message", "上传成功");
						map.put("code", 1);
						map.put("fileUrl", storePath.getFullPath());
					/*}else {
						DataSourceSwitch.set(null);
						files.setFileName(fileName);
						files.setOriginalFileName(originalFileName);
						String fileUrl = storePath.getFullPath();
						files.setFileUrl(fileUrl);
						files.setBusinessCode(businessCode);
						files.setItemId(itemId);
						files.setFileCode(fileCode);
						files.setFileAgain(2);
						DataSourceSwitch.set(null);
						fileService.update(files);
						Files files2 = fileService.findAllByBusinessCodeAndfileName(businessCode,fileName);
						int ids = files2.getId();
						List<Department> departmentList = departmentService.findByBusinessCode(businessCode);
						for (Department department : departmentList){
							if(department.getId() == 7){
								DataSourceSwitch.set("yth_dba");
								String listobj = ApiAccess.StringApipost("uploadFileV2","encode_fileName=" + fileName + "&encode_fileContent=" + FileBase64ConvertUitl.multipartFile2base64(file));
								Map mapobj = JsonKit.toMap(JsonKit.toJsonStr(listobj));
								Map mapfile = JsonKit.toMap(JsonKit.toJsonStr(mapobj.get("data")));
//								Map mapfile = JsonKit.toMap(JsonKit.toJsonStr(mapData.get("id")));
								fileVo.setNames(mapfile.get("name").toString());
								if(null != mapfile.get("nameEx")){
									fileVo.setNameEx(mapfile.get("nameEx").toString());
								}
								fileVo.setPath(mapfile.get("path").toString());
								fileVo.setSizes(mapfile.get("size").toString());
								fileVo.setBusinessCode(businessCode);
								fileVo.setItemId(itemId);
								fileVo.setFileId(ids);
								DataSourceSwitch.set("yth_dba");
								fileService.updatefileVo(fileVo);
							}
						}
						map.put("id", ids);
						map.put("status", "ok");
						map.put("message", "上传成功");
						map.put("code", 1);
						map.put("fileUrl", storePath.getFullPath());
					}*/

				}

			}
		}

		long  endTime=System.currentTimeMillis();
		System.out.println("方法三的运行时间："+String.valueOf(endTime-startTime)+"ms");
		return JsonKit.toJsonStr(map);
	}

	@PostMapping("/backFiles")
	@ResponseBody
	public Object backFiles(@RequestBody String params, HttpSession session){
		Map<String, Object> maps = JsonKit.toMap(params);
		Map<String, Object> map = new HashMap<>();
		String businessCode = maps.get("businessCode").toString();
		List<Files> filesList = null;
		if(maps.containsKey("fileCode") && maps.get("fileCode") != null && maps.containsKey("itemId") && maps.get("itemId") != null){
			filesList =  fileService.findAllByBusinessCodeAndfileCodeAnditemId(businessCode, maps.get("fileCode").toString(), Integer.valueOf(String.valueOf(maps.get("itemId"))));
		}else{
			filesList =  fileService.findAllByBusinessCode(businessCode);
		}

//		String originalFileName = session.getAttribute("originalFileName").toString();
		if(filesList == null){
			map.put("status", "ok");
			map.put("message", "回调成功,暂无数据");
			map.put("code", 1);
			return JSONObject.fromObject(map);
		}

		JSONArray array= JSONArray.parseArray(JSON.toJSONString(filesList));
	 		/*for(int i = 0; i < array.size(); i++){
				array.getJSONObject(i).put("originalFileName", originalFileName);
			}*/
		map.put("data", array);
		map.put("status", "ok");
		map.put("message", "回调成功");
		map.put("code", 1);
		return JSONObject.fromObject(map);
	}

	/**
	 * 回调失败重新上传的文件
	 * @param params
	 * @return
	 */
	@PostMapping("/againUpload")
	@ResponseBody
	public Object againUpload(@RequestBody String params){
		Map<String, Object> maps = JsonKit.toMap(params);
		PageResult pageResult = fileService.againUpload(maps);
		return pageResult;
	}

}
