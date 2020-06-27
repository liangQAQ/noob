package com.example.starter.format;

public class StringFormatProcessor implements FormatProcessor {

    public String format(Object obj) {
        return "string format:"+obj.toString();
    }
}
