package com.yjs.dao.mapper;

import com.yjs.bean.Receipt.Receipt;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface ReceiptMapper {

	void save(Receipt receipt);

    Receipt findAllByBusinessCode(String businessCode);

    void update(Receipt receipt);
}
