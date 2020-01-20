package com.yjs.dao.mapper;


import com.yjs.bean.yjs.FileVo;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FileVoMapper {

    void saveFileVo(FileVo fileVo);

    void updatefileVo(FileVo fileVo);

    void deleteFileVo(int id);

    List<FileVo> findByItemId();

    FileVo findByFileId(int fileId);
}
