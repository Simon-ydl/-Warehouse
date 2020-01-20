package com.yjs.service.file;


import com.yjs.bean.dto.PageResult;
import com.yjs.bean.file.Files;
import com.yjs.bean.yjs.FileVo;

import java.util.List;
import java.util.Map;

public interface FileService {

	void save(Files files);

	void delete(int id);


    List<Files> findAllByBusinessCode(String businessCode);

    List<Files> findAllByBusinessCodeAnditemsId(String businessCode, int itemsId);

    Files findAllById(int id);

    Files findAllByBusinessCodeAndfileName(String businessCode, String fileName);

    void update(Files files);

    PageResult againUpload(Map<String, Object> maps);

    void updateFileAgent(String businessCode, int fileAgain);

    void saveFileVo(FileVo fileVo);

    void updatefileVo(FileVo fileVo);

    void deleteFileVo(int id);

    List<Files> findAllByBusinessCodeAndfileCodeAnditemId(String businessCode, String fileCode, int itemsId);

    List<FileVo> findByItemId();

    FileVo findByFileId(int id);

    Files findAllByBusinessCodeAndfileUrl(String businessCode, String fileUrl);
}
