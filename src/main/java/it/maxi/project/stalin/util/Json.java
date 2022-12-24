package it.maxi.project.stalin.util;

import java.util.Map;

public class Json {

    public static String of(Map<String, String> map) {
        StringBuilder jsonBuilder = new StringBuilder("{");
        map.forEach((k,v) -> jsonBuilder.append("\"").append(k).append("\":\"").append(v).append("\","));
        if (jsonBuilder.charAt(jsonBuilder.length()-1) == ',') {
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        }
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

}
