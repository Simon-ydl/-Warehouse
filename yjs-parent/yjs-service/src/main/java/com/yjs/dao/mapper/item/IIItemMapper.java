package com.yjs.dao.mapper.item;

import com.yjs.bean.item.Items;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

//SELECT * FROM ro_items WHERE department_id = 2 AND `status` = 3;
@Repository
public interface IIItemMapper extends Mapper<Items>, SelectByIdListMapper<Items,Integer> {


}
