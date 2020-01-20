package com.yjs.dao.mapper;

import com.yjs.bean.file.Files;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileMapper {
    void save(Files files);

    void delete(int id);

    List<Files> findAllByBusinessCode(String businessCode);

    List<Files> findAllByBusinessCodeAnditemsId(@Param("businessCode") String businessCode, @Param("itemsId") int itemsId);

    Files findAllById(int id);

    Files findAllByBusinessCodeAndfileName(@Param("businessCode") String businessCode, @Param("fileName") String fileName);

    void update(Files files);

    void updateFileAgent(@Param("businessCode") String businessCode,@Param("fileAgain") int fileAgain);

    List<Files> findAllByBusinessCodeAndfileCodeAnditemId(@Param("businessCode") String businessCode, @Param("fileCode") String fileCode, @Param("itemsId") int itemsId);

    Files findAllByBusinessCodeAndfileUrl(@Param("businessCode") String businessCode, @Param("fileUrl") String fileUrl);
}
