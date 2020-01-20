package com.yjs.service.receipt;


import com.yjs.bean.Receipt.Receipt;

public interface ReceiptService {

	void save(Receipt receipt);

    Receipt findAllByBusinessCode(String businessCode);

    void update(Receipt receipt);
}
