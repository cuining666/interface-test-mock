package com.cnstar.interfacetestmock.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JsonCompareUtil {
    private static final String PATH_SEPATATOR = "/";

    public static boolean compareJsonObject(JSONObject source, JSONObject target) {
        if (source == null && target == null) {
            return false;
        }
        List<String> leafPaths = getLeafPath(source);
        for (String path : leafPaths) {
            if (!JSONPath.contains(target, path)) {
                return false;
            } else {
                Object srcVal = JSONPath.eval(source, path);
                Object tarVal = JSONPath.eval(target, path);
                if (srcVal instanceof String && tarVal instanceof String) {
                    String srcValStr = (String) srcVal;
                    String tarValStr = (String) tarVal;
                    if (!tarValStr.matches(srcValStr)) {
                        return false;
                    }
                } else {
                    if (!srcVal.equals(tarVal)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean compareJsonObject(Map<String, String> source, Map<String, String> target) {
        if (source == null && target == null) {
            return false;
        }
        for (String srcKeys : source.keySet()) {
            if (!target.containsKey(srcKeys)) {
                return false;
            } else {
                if (!target.get(srcKeys).matches(source.get(srcKeys))) {
                    return false;
                }
            }
        }
        return true;
    }

    private static List<String> getLeafPath(JSONObject source) {
        // Get all json paths of the json object
        List<String> jsonPaths = new ArrayList<>(JSONPath.paths(source).keySet());
        // No-leaf paths to be removed
        List<String> nodePaths = new ArrayList<>();
        // Add root path
        nodePaths.add(PATH_SEPATATOR);
        // Add all no-leaf path
        for (String jsonPath : jsonPaths) {
            int lastSeparatorIdx = jsonPath.lastIndexOf(PATH_SEPATATOR);
            if (lastSeparatorIdx > 0) {
                nodePaths.add(jsonPath.substring(0, lastSeparatorIdx));
            }
        }
        // Remove all no-leaf paths
        jsonPaths.removeAll(nodePaths);
        List<String> leafPaths = new ArrayList<>();
        for (String jsonPath : jsonPaths) {
            leafPaths.add(jsonPath.replaceFirst(PATH_SEPATATOR, "\\$").replaceAll(PATH_SEPATATOR, "\\."));
        }
        // Sort
        Collections.sort(leafPaths);
        return leafPaths;
    }

}
