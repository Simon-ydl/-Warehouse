package com.yjs.dao.mapper;


import com.yjs.bean.application.Applicat;
import org.springframework.stereotype.Repository;


@Repository
public interface ApplicatMapper {
    void save(Applicat applicant);


    Applicat findAllByBusinessCode(String businessCode);

    void update(Applicat applicat);
}
