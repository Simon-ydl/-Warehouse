package com.yjs.service.applicat.Impl;



import com.yjs.bean.application.Applicat;
import com.yjs.dao.mapper.ApplicatMapper;
import com.yjs.service.applicat.ApplicatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ApplicatServiceImpl implements ApplicatService {

	@Autowired
	ApplicatMapper applicatMapper;
	
	@Override
	public void save(Applicat applicat) {
		// TODO Auto-generated method stub
		applicatMapper.save(applicat);
	}


	@Override
	public Applicat findAllByBusinessCode(String businessCode) {
		// TODO Auto-generated method stub
		Applicat applicats = applicatMapper.findAllByBusinessCode(businessCode);
		return applicats;
	}

	@Override
	public void update(Applicat applicat) {
		applicatMapper.update(applicat);
	}
}
