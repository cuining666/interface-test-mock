package com.cnstar.interfacetestmock.mock.stub;

import com.alibaba.fastjson.JSONObject;
import com.cnstar.interfacetestmock.exception.MockErrorResponseException;
import com.cnstar.interfacetestmock.model.GrpcCommonStub;
import com.cnstar.interfacetestmock.util.JsonCompareUtil;
import io.grpc.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommonStub {
    @Autowired
    private StubManager stubManager;

    private GrpcCommonStub hitStub;

    public GrpcCommonStub getHitStub() {
        return hitStub;
    }

    public void setHitStub(GrpcCommonStub hitStub) {
        this.hitStub = hitStub;
    }

    public JSONObject stubFor(JSONObject reqJson, String service, String method) throws MockErrorResponseException {
        JSONObject respJson = new JSONObject();
        Collection<GrpcCommonStub> stubList = stubManager.getCommonStub(service, method);
        if (stubList != null && stubList.size() > 0) {
            for (GrpcCommonStub stub : stubList) {
                JSONObject stubReqJson = new JSONObject(stub.getReq());
                if (stubReqJson == null || JsonCompareUtil.compareJsonObject(stubReqJson, reqJson)) {
                    if (stub.getRespType() != null && stub.getRespType().equals(GrpcCommonStub.ResponseType.ERROR.name())) {
                        throw new MockErrorResponseException(new JSONObject(stub.getResp()).toJSONString());
                    }
                    respJson = new JSONObject(stub.getResp());
                    hitStub = stub;
                    break;
                }
            }
        }
        return respJson;
    }

    public Map<String, String> subForHeaders(String service, String method, Metadata reqHeadMeta) {
        Collection<GrpcCommonStub> stubList = stubManager.getCommonStub(service, method);
        Map<String, String> respHeaders = null;
        Map<String, String> reqHeaders = new HashMap<>();
        for (String key : reqHeadMeta.keys()) {
            reqHeaders.put(key, reqHeadMeta.get(Metadata.Key.of(key, Metadata.ASCII_STRING_MARSHALLER)));
        }
        if (stubList != null && stubList.size() > 0) {
            for (GrpcCommonStub stub : stubList) {
                Map<String, String> stubReqHeaders = stub.getReqHeaders();
                Map<String, String> stubRespHeaders = stub.getRespHeaders();
                if (stubReqHeaders == null && stubRespHeaders != null) {
                    respHeaders = stubRespHeaders;
                } else if (JsonCompareUtil.compareJsonObject(stubReqHeaders, reqHeaders)) {
                    respHeaders = stubRespHeaders;
                } else {
                    respHeaders = new HashMap<>();
                }
            }
        }
        return respHeaders;
    }
}
