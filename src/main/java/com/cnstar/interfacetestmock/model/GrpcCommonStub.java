package com.cnstar.interfacetestmock.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GrpcCommonStub implements Serializable {
    private static final long serialVersionUID = -282027410478833936L;

    public enum ResponseType {
        OK,
        ERROR
    }

    private String name;
    private String desc;
    private String service;
    private String method;
    private Map<String, String> reqHeaders;
    private Map<String, String> respHeaders;

    private String respType;
    private Map<String, Object> req;
    private Map<String, Object> resp;

    // 对应的Json串
    /**
    {
        "name": "demo",
        "desc": "return error",
        "service": "Service8583",
        "method": "ActiveKey",
        "respType": "ERROR",
        "req": {
            "keyName": "123456"
        }
        "resp": {
            "errorCode": "500",
            "message": "testtest"
        }
    }
     */
}
