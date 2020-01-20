package com.yjs.dao.mapper;

import com.yjs.bean.datum.Datum;
import com.yjs.bean.datum.Datums;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatumMapper {

    void save(Datum datum);

    Datum findAllByBusinessCodeAndItemsId(@Param("businessCode") String businessCode, @Param("itemsId") int itemsId);


    void update(Datum datum);

    List<Datum> findAllByBusinessCode(String businessCode);


    void updateState(@Param("businessCode") String businessCode , @Param("state") String state);

    List<Datum> findAllByItemId();
}
