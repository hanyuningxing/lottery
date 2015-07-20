package com.cai310.lottery.service.activity;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.exception.DataException;

public interface AvtivityService {

	void TenSendOne(Scheme scheme);

	void noSendAvtivity(Scheme scheme) throws DataException;
	
}
