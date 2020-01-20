package com.yjs.bean.matter;


import com.yjs.bean.Ids;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name="ro_matter")
@Getter
@Setter
public class Matter {


    @Id
    @Column(name = "id")
    protected Integer id;

    String name;
    String target;
    String accept;
    String isdo;
    String matterCode;
    String iszx;

    String creator;
    Object creatorId;
    Date createDate;
    Boolean deleted;

}
