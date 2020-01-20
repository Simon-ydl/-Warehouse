package com.yjs.service.page.Impl;


import java.util.List;

import com.yjs.bean.page.Page;
import com.yjs.dao.mapper.PageMapper;
import com.yjs.service.page.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class PageServiceImpl implements PageService {

	@Autowired
	PageMapper pageMapper;
	
	@Override
	public List<Object> findAllByMatterId(int matterId) {
		// TODO Auto-generated method stub
		List<Object> list = pageMapper.findAllByMatterId(matterId);
		return list;
	}

	@Override
	public Page findAllByMatterIdAndItemId(int matterId, String itemId) {
		Page page = pageMapper.findAllByMatterIdAndItemId(matterId,itemId);
		return page;
	}

}
