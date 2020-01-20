package com.yjs.service.situation.Impl;

import com.yjs.bean.Situation.Situation;
import com.yjs.dao.mapper.SituationMapper;
import com.yjs.service.situation.SituationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SituationServiceImpl implements SituationService {
	
	@Autowired
	SituationMapper situationMapper;

	@Override
	public void save(Situation situation) {

		situationMapper.save(situation);
		
	}

	@Override
	public Situation findAllByBusinessCode(String businessCode) {
		Situation situation = situationMapper.findAllByBusinessCode(businessCode);
		return situation;
	}

	@Override
	public void update(Situation situation) {
		situationMapper.update(situation);
	}
}
