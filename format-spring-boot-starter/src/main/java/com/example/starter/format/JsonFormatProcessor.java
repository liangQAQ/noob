package com.example.starter.format;

import com.alibaba.fastjson.JSON;

public class JsonFormatProcessor implements FormatProcessor {

    public String format(Object obj) {
        return "json format:"+JSON.toJSONString(obj);
    }

}
