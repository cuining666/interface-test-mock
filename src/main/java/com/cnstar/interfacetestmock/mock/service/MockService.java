package com.cnstar.interfacetestmock.mock.service;

import com.alibaba.fastjson.JSONObject;
import com.cnstar.interfacetestmock.exception.MockErrorResponseException;
import com.cnstar.interfacetestmock.mock.stub.CommonStub;
import com.cnstar.interfacetestmock.mock.stub.StubManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MockService {
    @Autowired
    private CommonStub commonStub;
    @Autowired
    private StubManager stubManager;

    private static final String SVC = "Service8583";

    public JSONObject getMock(Object req) {
        String method = "importKey";
        log.info("Received import key request param is ?");
        try {
            JSONObject reqJson = (JSONObject) JSONObject.toJSON(req);
//            reqJson.put("key", "req 里的属性");
            stubManager.addGrpcCalls(SVC, method, reqJson);
            JSONObject respJson = commonStub.stubFor(reqJson, SVC, method);
            return respJson;
        } catch (MockErrorResponseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
