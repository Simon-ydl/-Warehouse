package com.yjs.service.materials;

import com.yjs.bean.materials.Materials;

import java.util.List;

public interface IMaterialsService {

    List<Materials> queryMaterialsbyCode(String businessCode);
}
