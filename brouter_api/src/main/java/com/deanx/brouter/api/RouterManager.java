package com.deanx.brouter.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;

import com.deanx.brouter.annotation.module.RouterBean;
import com.deanx.brouter.api.core.BRouterLoadGroup;
import com.deanx.brouter.api.core.BRouterLoadPath;
import com.deanx.brouter.api.core.Call;

import java.util.Map;


/**
 * 路由加载管理器
 */
public final class RouterManager {

    private static final String TAG = "RouterManager >>>";
    private static final String GROUP_FILE_PREFIX_NAME = ".BRouter$$Group$$";
    private static RouterManager instance;

    // 路由组名
    private String group;
    // 路由详细路径
    private String path;

    // Lru缓存，key:类名, value:路由组Group加载接口
    private LruCache<String, BRouterLoadGroup> groupCache;
    // Lru缓存，key:类名, value:路由组Group对应的详细Path加载接口
    private LruCache<String, BRouterLoadPath> pathCache;
    // APT生成的路由组Group源文件前缀名

    public static RouterManager getInstance() {
        if (instance == null) {
            synchronized (RouterManager.class) {
                if (instance == null) {
                    instance = new RouterManager();
                }
            }
        }
        return instance;
    }

    private RouterManager() {
        groupCache = new LruCache<>(100);
        pathCache = new LruCache<>(100);
    }

    public BundleManager build(String path) {
        // @BRouter注解中的path值，必须要以 / 开头（模仿阿里Arouter规范）
        if (TextUtils.isEmpty(path) || !path.startsWith("/")) {
            throw new IllegalArgumentException("未按规范配置，如：/app/MainActivity");
        }

        group = subFromPath2Group(path);

        // 检查后再赋值
        this.path = path;

        return new BundleManager();
    }

    private String subFromPath2Group(String path) {
        // 比如开发者代码为：path = "/MainActivity"，最后一个 / 符号必然在字符串第1位
        if (path.lastIndexOf("/") == 0) {
            // 架构师定义规范，让开发者遵循
            throw new IllegalArgumentException("@BRouter注解未按规范配置，如：/app/MainActivity");
        }

        // 从第一个 / 到第二个 / 中间截取，如：/app/MainActivity 截取出 app 作为group
        String finalGroup = path.substring(1, path.indexOf("/", 1));

        if (TextUtils.isEmpty(finalGroup)) {
            // 架构师定义规范，让开发者遵循
            throw new IllegalArgumentException("@BRouter注解未按规范配置，如：/app/MainActivity");
        }

        // 最终组名：app
        return finalGroup;
    }

    /**
     * 开始跳转
     *
     * @param context       上下文
     * @param bundleManager Bundle拼接参数管理类
     * @param code          这里的code，可能是requestCode，也可能是resultCode。取决于isResult
     * @return 普通跳转可以忽略，用于跨模块CALL接口
     */
    Object navigation(@NonNull Context context, BundleManager bundleManager, int code) {
        // 精华：阿里的路由path随意写，导致无法找到随意拼接APT生成的源文件，如：ARouter$$Group$$abc
        // 找不到，就加载私有目录下apk中的所有dex并遍历，获得所有包名为xxx的类。并开启了线程池工作
        // 这里的优化是：代码规范写法，准确定位ARouter$$Group$$app
        String groupClassName = context.getPackageName() + ".apt" + GROUP_FILE_PREFIX_NAME + group;
        Log.e(TAG, "groupClassName -> " + groupClassName);

        try {
            BRouterLoadGroup groupLoad = groupCache.get(group);
            if (groupLoad == null) {
                Class<?> clazz = Class.forName(groupClassName);
                groupLoad = (BRouterLoadGroup) clazz.newInstance();
                groupCache.put(group, groupLoad);
            }

            // 获取路由路径类ARouter$$Path$$app
            if (groupLoad.loadGroup().isEmpty()) {
                throw new RuntimeException("路由加载失败");
            }

            BRouterLoadPath pathLoad = pathCache.get(path);
            if (pathLoad == null) {
                Class<? extends BRouterLoadPath> clazz = groupLoad.loadGroup().get(group);
                if (clazz != null)
                    pathLoad = clazz.newInstance();
                if (pathLoad != null)
                    pathCache.put(path, pathLoad);
            }

            if (pathLoad != null) {
                // tempMap赋值
                Map<String, RouterBean> loadPathMap = pathLoad.loadPath();

                if (loadPathMap.isEmpty()) {
                    throw new RuntimeException("路由路径加载失败");
                }

                RouterBean routerBean = pathLoad.loadPath().get(path);
                if (routerBean != null) {
                    switch (routerBean.getType()) {
                        case ACTIVITY:
                            Intent intent = new Intent(context, routerBean.getClazz());
                            intent.putExtras(bundleManager.getBundle());

                            // startActivityForResult -> setResult
                            if (bundleManager.isResult()) {
                                ((Activity) context).setResult(code, intent);
                                ((Activity) context).finish();

                            } else if (code > 0) { // 跳转时是否回调
                                ((Activity) context).startActivityForResult(intent, code, bundleManager.getBundle());
                            } else {
                                context.startActivity(intent, bundleManager.getBundle());
                            }
                            break;
                        case CALL:
                            Class<?> clazz = routerBean.getClazz();
                            Call call = (Call) clazz.newInstance();
                            bundleManager.setCall(call);
                            return bundleManager.getCall();
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "路由加载失败：" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
