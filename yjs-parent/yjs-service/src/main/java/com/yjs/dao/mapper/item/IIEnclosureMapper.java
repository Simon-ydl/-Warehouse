package com.yjs.dao.mapper.item;

import com.yjs.bean.enclosure.Enclosure;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface IIEnclosureMapper extends Mapper<Enclosure>, SelectByIdListMapper<Enclosure,Integer> {
    @Insert("insert into ro_enclosure (enclosure_name,enclosure_url) values(#{enclosure.enclosureName},#{enclosure.enclosureUrl})")
//    @Options(useGeneratedKeys = true,keyProperty = "enclosure.id",keyColumn = "id")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="enclosure.id", before=false, resultType=Integer.class)
    Integer insertDate(@Param("enclosure") Enclosure enclosure);
}
