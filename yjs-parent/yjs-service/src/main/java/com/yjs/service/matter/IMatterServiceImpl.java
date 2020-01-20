package com.yjs.service.matter;

import com.yjs.bean.matter.Matter;
import com.yjs.dao.mapper.MatterMapper;
import com.yjs.service.matter.service.MatterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class IMatterServiceImpl implements MatterService {
	
	@Autowired
	MatterMapper matterMapper;


	@Override
	public String findNameByMatterId(int matterId) {
		// TODO Auto-generated method stub
		String name = matterMapper.findNameByMatterId(matterId);
		return name;
	}

	@Override
	public Matter findAllBymatterCode(String matterCode) {
		Matter matter = matterMapper.findAllBymatterCode(matterCode);
		return matter;
	}

	@Override
	public Matter findAllById(int matterId) {
		Matter matter = matterMapper.findAllById(matterId);
		return matter;
	}


}
