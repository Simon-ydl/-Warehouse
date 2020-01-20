package com.yjs.service.item.Impl;

import com.yjs.bean.item.Items;
import com.yjs.dao.mapper.ItemsMapper;
import com.yjs.dao.mapper.item.IIItemMapper;
import com.yjs.service.item.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemsServiceImpl implements ItemsService {

    @Autowired
    ItemsMapper itemsMapper;

    @Autowired
    IIItemMapper itemMapper;

    @Override
    public Items findByItemCode(String itemCode) {
        Items items = itemsMapper.findByItemCode(itemCode);
        return items;
    }

    @Override
    public Items findByItemsId(int itemsId) {
        Items items = itemsMapper.findByItemsId(itemsId);
        return items;
    }

    @Override
    public int saveItem(Items items){

        return itemMapper.insert(items);
    }
}
