package com.huangliang.framework.webmvc;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class HLModelAndView {

    private String viewName;
    private Map<String,?> model;

    public HLModelAndView(String viewName){
        this(viewName,null);
    }

}
