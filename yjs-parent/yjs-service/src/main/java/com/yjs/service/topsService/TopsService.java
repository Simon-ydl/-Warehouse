package com.yjs.service.topsService;

import com.yjs.bean.tops.Tops;

public interface TopsService {
    Tops findAllByBusinessCode(String businessCode);

    void save(Tops tops);

    void update(Tops tops);
}
