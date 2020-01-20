package com.yjs.dao.mapper;

import com.yjs.bean.agent.Agent;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentMapper {

    void save(Agent agent);

    Agent findAllByBusinessCode(String businessCode);

    String findNameByMatterId(int matterId);

    void update(Agent agents);
}
