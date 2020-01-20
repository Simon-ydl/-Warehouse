package com.yjs.dao.mapper.item;


import com.yjs.bean.file.Files;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface IIFileMapper extends Mapper<Files>, SelectByIdListMapper<Files,Integer> {

}
