package com.yjs.dao.mapper;

import com.yjs.bean.business.Business;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessMapper {
    void save(Business business);

    List<Business> findAllByUseridcode(String useridcode);

    Business findAllByBusinessCode(String businessCode);

    void updateBusinessState(@Param("business_code") String business_code,@Param("state") String state);//传入流水号修改business表的状态

    void update(Business business);

    Business findAllByUseridcodeAndMatterId(@Param("userIdCode") String userIdCode, @Param("matterId") int matterId);
}
