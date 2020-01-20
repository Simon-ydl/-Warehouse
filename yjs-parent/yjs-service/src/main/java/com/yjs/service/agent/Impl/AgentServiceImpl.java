package com.yjs.service.agent.Impl;

import com.yjs.bean.agent.Agent;
import com.yjs.dao.mapper.AgentMapper;
import com.yjs.service.agent.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AgentServiceImpl implements AgentService {

	@Autowired
	AgentMapper agentMapper;
	
	@Override
	public void save(Agent agent) {
		// TODO Auto-generated method stub
		agentMapper.save(agent);
	}

	@Override
	public Agent findAllByBusinessCode(String businessCode) {
		Agent agent = agentMapper.findAllByBusinessCode(businessCode);
		return agent;
	}

	@Override
	public void update(Agent agents) {
		agentMapper.update(agents);
	}
}
