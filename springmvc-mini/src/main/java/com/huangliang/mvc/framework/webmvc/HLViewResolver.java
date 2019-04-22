package com.huangliang.mvc.framework.webmvc;

import java.io.File;
import java.util.Locale;

public class HLViewResolver {
    private final String DEFAULT_TEMPLATE_SUFFIX = ".html";
    private File templateRootDir;
    private String viewName;
    public HLViewResolver(String templateRoot){
        String templateRootPath =
                this.getClass().getClassLoader().getResource(templateRoot).getFile();
        this.templateRootDir = new File(templateRootPath);
    }
    public HLView resolveViewName(String viewName, Locale locale) throws Exception {
        this.viewName = viewName;
        if(null == viewName || "".equals(viewName.trim())){ return null;}
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFIX) ? viewName : (viewName +
                DEFAULT_TEMPLATE_SUFFIX);
        File templateFile = new File((templateRootDir.getPath() + "/" + viewName).replaceAll("/+",
                "/"));
        return new HLView(templateFile);
    }
}
