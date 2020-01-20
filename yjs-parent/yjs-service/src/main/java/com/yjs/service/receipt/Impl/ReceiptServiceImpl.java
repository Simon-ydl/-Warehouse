package com.yjs.service.receipt.Impl;

import com.yjs.bean.Receipt.Receipt;
import com.yjs.dao.mapper.ReceiptMapper;
import com.yjs.service.receipt.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReceiptServiceImpl implements ReceiptService {

	@Autowired
	ReceiptMapper receiptMapper;
	
	@Override
	public void save(Receipt receipt) {
		// TODO Auto-generated method stub
		receiptMapper.save(receipt);
	}

	@Override
	public Receipt findAllByBusinessCode(String businessCode) {
		Receipt receipt = receiptMapper.findAllByBusinessCode(businessCode);
		return receipt;
	}

	@Override
	public void update(Receipt receipt) {
		receiptMapper.update(receipt);
	}


}
