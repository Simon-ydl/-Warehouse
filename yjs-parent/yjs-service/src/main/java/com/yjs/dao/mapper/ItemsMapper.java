package com.yjs.dao.mapper;

import com.yjs.bean.item.Items;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ItemsMapper {
    Items findByItemCode(String itemCode);

    Items findByItemsId(int itemsId);
}
