package com.yjs.service.agent;


import com.yjs.bean.agent.Agent;

public interface AgentService {

	void save(Agent agent);

    Agent findAllByBusinessCode(String businessCode);

    void update(Agent agents);
}
