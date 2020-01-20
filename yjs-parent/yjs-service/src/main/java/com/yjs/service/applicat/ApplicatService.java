package com.yjs.service.applicat;


import com.yjs.bean.application.Applicat;



public interface ApplicatService {

	void save(Applicat applicant);


	Applicat findAllByBusinessCode(String businessCode);

	void update(Applicat applicat);
}
