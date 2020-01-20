package com.yjs.dao.mapper;

import com.yjs.bean.approver.Approver;
import net.sf.json.JSONArray;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ApproverMapper {

    List<Approver> findAllByBusinessCode(String businessCode);

    void updateLackMaterials(@Param("businessCode") String businessCode, @Param("lackMaterials") String lackMaterials);

    Approver findAllByBusinessCodeAndItemId(@Param("businessCode") String businessCode, @Param("itemId") int itemId);
}
