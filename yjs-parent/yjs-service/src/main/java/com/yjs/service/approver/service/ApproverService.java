package com.yjs.service.approver.service;

import com.yjs.bean.approver.Approver;
import net.sf.json.JSONArray;

import java.util.List;

public interface ApproverService {
    List<Approver> findAllByBusinessCode(String businessCode);

    void updateLackMaterials(String businessCode, String lackMaterials);
}
