package com.cnstar.interfacetestmock.enumdb;

import java.util.Map;

public interface BaseDbEnum {
    String getName();

    Object getDefaultVal();

    Map<String, Object> wrapAllCols(Map<String, Object> testData);
}
