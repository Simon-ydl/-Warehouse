package com.yjs.service.item;

import com.yjs.bean.item.Items;

public interface ItemsService {

    Items findByItemCode(String itemCode);

    Items findByItemsId(int itemsId);

    int saveItem(Items items);
}
