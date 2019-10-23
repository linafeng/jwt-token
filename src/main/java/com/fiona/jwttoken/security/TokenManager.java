package com.fiona.jwttoken.security;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟缓存而已
 */

public class TokenManager {
    private static Map<String, String> tokenMap = new HashMap<>();

    public static Map<String, String> getTokenMap() {
        return tokenMap;
    }

    public static void putToken(String cacheId, String secrecToken) {
        tokenMap.put(cacheId, secrecToken);
    }


}
