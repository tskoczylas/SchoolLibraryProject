package com.tomsapp.Toms.V2.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Message {



     static public   Map<String,String> createMessageMap(String input){
        String[] split = input.split(";");
        Map<String,String> stringStringMap = new LinkedHashMap<>();
        if(split.length==4){
           stringStringMap.put("messageTitle",split[0] );
            stringStringMap.put("messageText",split[1] );
            stringStringMap.put("link",split[2]);
            stringStringMap.put("linkMes",split[3] );
        }
        return stringStringMap;
    }

    static public   Map<String,String> createMessageMap2Links(String input){
        String[] split = input.split(";");
        Map<String,String> stringStringMap = new LinkedHashMap<>();
        if(split.length==6){
            stringStringMap.put("messageTitle",split[0] );
            stringStringMap.put("messageText",split[1] );
            stringStringMap.put("link",split[2]);
            stringStringMap.put("linkMes",split[3] );
            stringStringMap.put("link2",split[4]);
            stringStringMap.put("linkMes2",split[5] );

        }
        return stringStringMap;
    }



}
