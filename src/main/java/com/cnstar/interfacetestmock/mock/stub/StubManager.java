package com.cnstar.interfacetestmock.mock.stub;

import com.alibaba.fastjson.JSONObject;
import com.cnstar.interfacetestmock.model.GrpcCommonStub;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope("singleton")
@Slf4j
public class StubManager {
    private Map<String, Map<String, GrpcCommonStub>> commonStubMap = new HashMap<>();
    private Map<String, String> otpResMap = new HashMap<>();
    private Map<String, List<String>> macingMsgMap = new HashMap<>();
    private Map<String, List<JSONObject>> grpcCalls = new LinkedHashMap<>();

    public synchronized void addCommonStub(GrpcCommonStub stub) {
        String key = getStubKey(stub);
        Map<String, GrpcCommonStub> stubMap;
        if (!commonStubMap.containsKey(key)) {
            stubMap = new HashMap<>();
            commonStubMap.put(key, stubMap);
        } else {
            stubMap = commonStubMap.get(key);
        }
        if (stubMap.containsKey(stub.getName())) {
            log.warn(String.format("The stub %s for %s.%s has already existed.", stub.getName(), stub.getService(), stub.getMethod()));
        }
        stubMap.put(stub.getName(), stub);
    }

    public synchronized GrpcCommonStub getCommonStub(String service, String method, String name) {
        Map<String, GrpcCommonStub> stubMap = commonStubMap.get(getStubKey(service, method));
        if (stubMap == null) {
            return null;
        } else {
            return stubMap.get(name);
        }
    }

    public synchronized Collection<GrpcCommonStub> getCommonStub(String service, String method) {
        Map<String, GrpcCommonStub> stubMap = commonStubMap.get(getStubKey(service, method));
        if (stubMap == null) {
            return null;
        } else {
            return stubMap.values();
        }
    }

    public synchronized Collection<GrpcCommonStub> getCommonStub() {
        Collection<Map<String, GrpcCommonStub>> stubMaps = commonStubMap.values();
        List<GrpcCommonStub> stubs = new ArrayList<>();
        if (stubs != null) {
            for (Map<String, GrpcCommonStub> stubMap : stubMaps) {
                stubs.addAll(stubMap.values());
            }
        }
        return stubs;
    }

    public synchronized void removeCommonStub(String service, String method) {
        commonStubMap.remove(getStubKey(service, method));
    }

    public synchronized void removeCommonStub(String service, String method, String name) {
        Map<String, GrpcCommonStub> stubMap = commonStubMap.get(getStubKey(service, method));
        if (stubMap != null) {
            stubMap.remove(name);
        }
    }

    public synchronized void removeCommonStub() {
        commonStubMap.clear();
    }

    public synchronized String getStubKey(GrpcCommonStub stub) {
        String svc = stub.getService();
        String method = stub.getMethod();
        return getStubKey(svc, method);
    }

    private String getStubKey(String svc, String method) {
        return svc + "." + method;
    }

    public synchronized void addGrpcCalls(String svc, String method, JSONObject req) {
        String key = getStubKey(svc, method);
        List<JSONObject> calls = grpcCalls.get(key);
        if (calls == null) {
            calls = new ArrayList<>();
            grpcCalls.put(key, calls);
        }
        calls.add(req);
    }

    public synchronized List<JSONObject> getGrpcCalls(String svcName, String methodName) {
        return grpcCalls.get(getStubKey(svcName, methodName));
    }

    public synchronized void clearGrpcCalls() {
        grpcCalls.clear();
    }
}
