package com.yjs.bean.test;




import com.yjs.bean.Ids;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="test")
@Getter
@Setter
public class Model extends Ids {

//    @javax.persistence.Id
//    @Id
//    protected Object id;
    String name;
    String sex;

}
