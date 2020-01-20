package com.yjs.service.matter.service;


import com.yjs.bean.matter.Matter;

public interface MatterService {

	String findNameByMatterId(int matterId);


    Matter findAllBymatterCode(String matterCode);

    Matter findAllById(int matterId);
}
