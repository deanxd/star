package com.deanx.compiler.utils;

/**
 * 常量类
 */
public class Constants {

    // 注解处理器中支持的注解类型
    public static final String AROUTER_ANNOTATION_TYPES = "com.deanx.brouter.annotation.BRouter";

    // 每个子模块的模块名
    public static final String MODULE_NAME = "moduleName";
    // 包名，用于存放APT生成的类文件
    public static final String APT_PACKAGE = "packageNameForAPT";


    // Activity全类名
    public static final String ACTIVITY = "android.app.Activity";
    // 包名前缀封装
    public static final String BASE_PACKAGE = "com.deanx.brouter.api";
    // 路由组Group加载接口
    public static final String AROUTE_GROUP = BASE_PACKAGE + ".core.BRouterLoadGroup";
    // 路由组Group对应的详细Path加载接口
    public static final String AROUTE_PATH = BASE_PACKAGE + ".core.BRouterLoadPath";
    // 跨模块业务，回调接口
    public static final String CALL = BASE_PACKAGE + ".core.Call";

    // RouterManager类名
    public static final String ROUTER_MANAGER = "RouterManager";

    // 路由组Group，参数名
    public static final String GROUP_PARAMETER_NAME = "groupMap";
    // 路由组Group，方法名
    public static final String GROUP_METHOD_NAME = "loadGroup";
    // 路由组Group对应的详细Path，参数名
    public static final String PATH_PARAMETER_NAME = "pathMap";
    // 路由组Group对应的详细Path，方法名
    public static final String PATH_METHOD_NAME = "loadPath";


    // APT生成的路由组Group源文件名
    public static final String GROUP_FILE_NAME = "BRouter$$Group$$";
    // APT生成的路由组Group对应的详细Path源文件名
    public static final String PATH_FILE_NAME = "BRouter$$Path$$";




    public static final String PARAMETER_ANNOTATION_TYPES = "com.deanx.brouter.annotation.Parameter";

    // 获取参数，加载接口
    public static final String PARAMETER_LOAD = BASE_PACKAGE + ".core.IParameterLoad";
    // 获取参数，方法名
    public static final String PARAMETER_NAMR = "target";
    // 获取参数，参数名
    public static final String PARAMETER_METHOD_NAME = "loadParameter";
    // String全类名
    public static final String STRING = "java.lang.String";
    // APT生成的获取参数类文件名
    public static final String PARAMETER_FILE_NAME = "$$Parameter";
}
