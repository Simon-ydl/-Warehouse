package com.yjs.service.situation;


import com.yjs.bean.Situation.Situation;

public interface SituationService {

	void save(Situation situation);


    Situation findAllByBusinessCode(String businessCode);

    void update(Situation situation);
}
