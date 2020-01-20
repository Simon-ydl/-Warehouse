package com.yjs.service.file.Impl;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.yjs.bean.approver.Approver;
import com.yjs.bean.dto.PageResult;
import com.yjs.bean.file.Files;
import com.yjs.bean.yjs.FileVo;
import com.yjs.dao.mapper.ApproverMapper;
import com.yjs.dao.mapper.FileMapper;
import com.yjs.dao.mapper.FileVoMapper;
import com.yjs.service.file.FileService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	FileMapper fileMapper;
	@Autowired
	ApproverMapper approverMapper;
	@Autowired
	FileVoMapper fileVoMapper;

	@Override
	public void save(Files files) {
		fileMapper.save(files);
	}

	@Override
	public void delete(int id) {
		fileMapper.delete(id);
	}

	@Override
	public List<Files> findAllByBusinessCode(String businessCode) {
		List<Files> filesList = fileMapper.findAllByBusinessCode(businessCode);
		return filesList;
	}

	@Override
	public List<Files> findAllByBusinessCodeAnditemsId(String businessCode, int itemsId) {
		List<Files> files = fileMapper.findAllByBusinessCodeAnditemsId(businessCode,itemsId);
		return files;
	}

	@Override
	public Files findAllById(int id) {
		Files files = fileMapper.findAllById(id);
		return files;
	}

	@Override
	public Files findAllByBusinessCodeAndfileName(String businessCode, String fileName) {
		Files files = fileMapper.findAllByBusinessCodeAndfileName(businessCode,fileName);
		return files;
	}

	@Override
	public void update(Files files) {
		fileMapper.update(files);
	}

	@Override
	public PageResult againUpload(Map<String, Object> maps) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new  JSONObject();
		PageResult pageResult = new PageResult();
		String fileIdes  = maps.get("fileId").toString();
		String[] fileIds = fileIdes.split(",");
		if(fileIds.length == 0){
			pageResult.setCode(1);
			pageResult.setMsg("回调成功");
			pageResult.setData(jsonArray);
			return pageResult;
		}else{
			for (int i = 0 ; i < fileIds.length; i++){
				Files files = fileMapper.findAllById(Integer.valueOf(fileIds[i]));
				if(files == null){
					continue;
				}else{
					int itemId = files.getItemId();
					String businessCode = files.getBusinessCode();
					Approver approver = approverMapper.findAllByBusinessCodeAndItemId(businessCode,itemId);
					String lackMaterials = approver.getLackMaterials();
					if(lackMaterials != null) {
						JSONArray jsonArylack = JSONArray.fromObject(lackMaterials);
						for (int j = 0; j < jsonArylack.size(); j++) {
							int fileId = Integer.valueOf(String.valueOf(jsonArylack.getJSONObject(j).get("id")));
							if(fileId == files.getId()) {
								String correction = jsonArylack.getJSONObject(j).get("correction").toString();
								JSONObject jsonObj = JSONObject.fromObject(files);
								jsonObj.accumulate("correction", correction);
								jsonArray.add(jsonObj);
							}else{
								continue;
							}
						}
					}
					if(files.getFileAgain() == 2){  //判断否当前重新上传的文件
						jsonArray.getJSONObject(i).accumulate("againUpload", true);
					}else {
						jsonArray.getJSONObject(i).accumulate("againUpload", false);
					}
				}
			}
			jsonObject.accumulate("fileFalse", jsonArray);
			pageResult.setCode(1);
			pageResult.setMsg("回调成功");
			pageResult.setData(jsonObject);
			return pageResult;
		}

	}

	@Override
	public void updateFileAgent(String businessCode, int fileAgain) {
		fileMapper.updateFileAgent(businessCode,fileAgain);
	}

	@Override
	public void saveFileVo(FileVo fileVo) {
		fileVoMapper.saveFileVo(fileVo);
	}

	@Override
	public void updatefileVo(FileVo fileVo) {
		fileVoMapper.updatefileVo(fileVo);
	}

	@Override
	public void deleteFileVo(int id) {
		fileVoMapper.deleteFileVo(id);
	}

	/**
	 * 根据业务流水号，事项id，文件标识查询文件
	 * @param businessCode
	 * @param fileCode
	 * @param itemsId
	 * @return
	 */
	@Override
	public List<Files> findAllByBusinessCodeAndfileCodeAnditemId(String businessCode, String fileCode, int itemsId) {
		List<Files> filesList = fileMapper.findAllByBusinessCodeAndfileCodeAnditemId(businessCode, fileCode, itemsId);
		return filesList;
	}

	/**
	 * 查询所有公安审批的文件
	 * @param
	 * @return
	 */
	@Override
	public List<FileVo> findByItemId() {
		List<FileVo> fileVoList = fileVoMapper.findByItemId();
		return fileVoList;
	}

	/**
	 * 根据fileId查询文件
	 * @param id
	 * @return
	 */
	@Override
	public FileVo findByFileId(int fileId) {
		FileVo fileVo = fileVoMapper.findByFileId(fileId);
		return fileVo;
	}

	@Override
	public Files findAllByBusinessCodeAndfileUrl(String businessCode, String fileUrl) {
		Files files = fileMapper.findAllByBusinessCodeAndfileUrl(businessCode, fileUrl);
		return files;
	}


}
