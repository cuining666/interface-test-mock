package com.cnstar.interfacetestmock.controller;

import com.cnstar.interfacetestmock.mock.stub.StubManager;
import com.cnstar.interfacetestmock.model.GrpcCommonStub;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
public class GrpcStubController {

    @Autowired
    private StubManager stubManager;

    @GetMapping("/grpc/stub/general/service/{svcName}/method/{mtdName}")
    public ResponseEntity getCommonStub(@PathVariable String svcName, @PathVariable String mtdName) {
        try {
            if (StringUtils.isNotEmpty(svcName) && StringUtils.isNotEmpty(mtdName)) {
                return new ResponseEntity(stubManager.getCommonStub(svcName, mtdName), HttpStatus.OK);
            } else {
                return new ResponseEntity("Service name and method name cannot be empty.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/grpc/stub/general/service/{svcName}/method/{mtdName}/{stubName}")
    public ResponseEntity getCommonStub(@PathVariable String svcName, @PathVariable String mtdName, @PathVariable String stubName) {
        try {
            if (StringUtils.isNotEmpty(svcName) && StringUtils.isNotEmpty(mtdName) && StringUtils.isNotEmpty(stubName)) {
                return new ResponseEntity(stubManager.getCommonStub(svcName, mtdName, stubName), HttpStatus.OK);
            } else {
                return new ResponseEntity("Service name, method name and stub name cannot be empty.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/grpc/stub/general")
    public ResponseEntity getCommonStub() {
        try {
            return new ResponseEntity(stubManager.getCommonStub(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/grpc/stub/general")
    public ResponseEntity addCommonStub(@RequestBody GrpcCommonStub stub) {
        try {
            stubManager.addCommonStub(stub);
            return new ResponseEntity("OK", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/grpc/stub/general/service/{svcName}/method/{mtdName}")
    public ResponseEntity deleteCommonStub(@PathVariable String svcName, @PathVariable String mtdName) {
        try {
            if (StringUtils.isNotEmpty(svcName) && StringUtils.isNotEmpty(mtdName)) {
                stubManager.removeCommonStub(svcName, mtdName);
                return new ResponseEntity("OK", HttpStatus.OK);
            } else {
                return new ResponseEntity("Service name and method name cannot be empty.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/grpc/stub/general/service/{svcName}/method/{mtdName}/{stubName}")
    public ResponseEntity deleteCommonStub(@PathVariable String svcName, @PathVariable String mtdName, @PathVariable String stubName) {
        try {
            if (StringUtils.isNotEmpty(svcName) && StringUtils.isNotEmpty(mtdName) && StringUtils.isNotEmpty(stubName)) {
                stubManager.removeCommonStub(svcName, mtdName, stubName);
                return new ResponseEntity("OK", HttpStatus.OK);
            } else {
                return new ResponseEntity("Service name, method name and stub name cannot be empty.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/grpc/stub/general")
    public ResponseEntity deleteCommonStub() {
        try {
            stubManager.removeCommonStub();
            return new ResponseEntity("OK", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 保存请求数据
    @GetMapping("/grpc/calls/service/{svcName}/method/{methodName}")
    public ResponseEntity getGrpcCalls(@PathVariable String svcName, @PathVariable String methodName) {
        try {
            return new ResponseEntity(stubManager.getGrpcCalls(svcName, methodName), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/grpc/calls/service")
    public ResponseEntity clearGrpcCalls() {
        stubManager.clearGrpcCalls();
        return new ResponseEntity("OK", HttpStatus.OK);
    }
}
