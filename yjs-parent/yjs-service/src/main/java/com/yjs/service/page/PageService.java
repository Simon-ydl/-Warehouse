package com.yjs.service.page;

import com.yjs.bean.page.Page;

import java.util.List;


public interface PageService {

	List<Object> findAllByMatterId(int matterId);

	Page findAllByMatterIdAndItemId(int matterId, String id);
}
