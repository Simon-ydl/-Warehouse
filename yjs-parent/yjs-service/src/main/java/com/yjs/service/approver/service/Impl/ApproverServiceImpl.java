package com.yjs.service.approver.service.Impl;

import com.yjs.bean.approver.Approver;
import com.yjs.dao.mapper.ApproverMapper;
import com.yjs.service.approver.service.ApproverService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApproverServiceImpl implements ApproverService {

    @Autowired
    ApproverMapper approverMapper;

    /**
     * 根据业务流水号查询所有Approver
     * @param businessCode 业务流水号
     * @return
     */
    @Override
    public List<Approver> findAllByBusinessCode(String businessCode) {
        List<Approver> approverList = approverMapper.findAllByBusinessCode(businessCode);
        return approverList;
    }

    @Override
    public void updateLackMaterials(String businessCode, String lackMaterials) {
        approverMapper.updateLackMaterials(businessCode,lackMaterials);
    }
}
