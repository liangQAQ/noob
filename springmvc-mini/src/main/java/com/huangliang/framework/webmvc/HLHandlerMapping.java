package com.huangliang.framework.webmvc;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
public class HLHandlerMapping {
    private Object controller;
    private Method method;
    private Pattern pattern; //url 的封装
}
