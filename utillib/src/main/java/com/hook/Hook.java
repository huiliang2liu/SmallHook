package com.hook;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;

import com.load.ApkResources;
import com.reflect.FieldManager;
import com.utils.ContentManager;
import com.utils.LogUtil;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * com.hook
 * 2018/9/20 17:10
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class Hook {
    private final static String TAG = "Hook";
//    final static String MY_PACKAGE = "my_package";
    final static String MY_ACTIVITY_XML = "my_activity_xml";
//    final static String MY_REPLACE = "my_replace";
//    final static String MY_THEME = "my_theme";
    final static String MY_ACTIVITY_FLAGS = "my_flags";
    final static String MY_ACTIVITY_META = "my_meta";
    final static String MY_ACTIVITY_SOFT_MODE = "my_soft_mode";
    final static String MY_ACTIVITY_OPTIONS = "my_options";
        final static String MY_ACTIVITY_CONFIG="my_config";
    List<String> startServers;
    String hookPackage;
    String hookServer;
    String hookActivity;
    protected List<String> registerActivities;
    protected List<String> registerServices;
    private Map<String, ParasXml.ActivityXml> activityMap;
    private static final Class<ContextWrapper> CONTEXT_WRAPPER_CLASS = ContextWrapper.class;
    private static final Class<ContextThemeWrapper> CONTEXT_THEME_WRAPPER_CLASS = ContextThemeWrapper.class;
    private static Field mBase;
    private static Field mTheme;
    private static Field mInflater;
    private static Field mResourcesField;
    private Map<String, android.app.Application> applicationMap = new HashMap<>();
    private static Field mApplicationActivity = FieldManager.field(Activity.class, "mApplication");
    private static Field mApplicationService = FieldManager.field(Service.class, "mApplication");
    private ClassActivityThread activityThread;


    static {
        mBase = FieldManager.field(CONTEXT_WRAPPER_CLASS, "mBase");
        mTheme = FieldManager.field(CONTEXT_THEME_WRAPPER_CLASS, "mTheme");
        mInflater = FieldManager.field(CONTEXT_THEME_WRAPPER_CLASS, "mInflater");
        mResourcesField = FieldManager.field(CONTEXT_THEME_WRAPPER_CLASS, "mResources");
    }

    /**
     * 获取占坑元素，0代表activity,1代表service
     *
     * @param className
     * @return
     */
   public ParasXml.ActivityXml pit(String className) {
        ParasXml.ActivityXml activityXml = null;
        if (activityMap.containsKey(className))
            activityXml = activityMap.get(className);
        return activityXml;
    }

    public void startActivity(Context context, Intent intent) {
        LogUtil.i(TAG, "startActivity");
        replaceActivity(intent);
        if (!(context instanceof Activity))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void startActivityForResult(Activity activity, Intent intent, int requestCode) {
        LogUtil.i(TAG, "startActivityForResult");
        replaceActivity(intent);
        activity.startActivityForResult(intent, requestCode);
    }

    private void replaceActivity(Intent intent) {
        if (intent == null)
            return;
        ComponentName componentName = intent.getComponent();
        if (componentName == null)
            return;
        String className = componentName.getClassName();
        if (className == null || className.isEmpty())
            return;
        if (registerActivities.indexOf(className) < 0) {
            ParasXml.ActivityXml activityXml = pit(className);
            if (activityXml != null) {
                replace(intent, activityXml.pit);
//                intent.putExtra(Hook.MY_PACKAGE, activityXml.packageName);
//                intent.putExtra(Hook.MY_ACTIVITY, activityXml.className);
//                intent.putExtra(Hook.MY_REPLACE, activityXml.replace);
//                intent.putExtra(Hook.MY_THEME, activityXml.theme);
                ApkResources resources = ContentManager.getManager().name2resources(activityXml.packageName);
                if (resources != null) {
                    ActivityInfo[] infos = resources.mPackageInfo.activities;
                    for (ActivityInfo info : infos) {
                        if (info.name.equals(activityXml.className)) {
                            intent.putExtra(Hook.MY_ACTIVITY_FLAGS, info.flags);
                            intent.putExtra(Hook.MY_ACTIVITY_META, info.metaData);
                            intent.putExtra(Hook.MY_ACTIVITY_SOFT_MODE, info.softInputMode);
                            intent.putExtra(Hook.MY_ACTIVITY_OPTIONS, info.uiOptions);
                            break;
                        }
                    }
                }
                intent.setExtrasClassLoader(ParasXml.ActivityXml.class.getClassLoader());
                intent.putExtra(Hook.MY_ACTIVITY_XML, activityXml);
            }

        }
    }

    public void startService(Context activity, Intent intent) {
        LogUtil.i(TAG, "startService");
        replaceService(intent);
        activity.startService(intent);
    }

    public boolean bindService(Context activity, Intent intent, ServiceConnection conn, int flags) {
        LogUtil.i(TAG, "bindService");
        replaceService(intent);
        return activity.bindService(intent, conn, flags);
    }

    private void replaceService(Intent intent) {
        if (intent == null)
            return;
        ComponentName componentName = intent.getComponent();
        if (componentName == null)
            return;
        String className = componentName.getClassName();
        if (className == null || className.isEmpty())
            return;
        if (registerServices.indexOf(className) < 0) {
            ParasXml.ActivityXml activityXml = pit(className);
            if (activityXml != null) {
                startServers.add(className);
                replace(intent, activityXml.pit);
            }

        }
    }

    private void replace(Intent intent, String className) {
        ComponentName componentName = new ComponentName(hookPackage, className);
        intent.setComponent(componentName);
    }

    public void setHookServer(String hookServer) {
        this.hookServer = hookServer;
    }

    public void setHookActivity(String hookActivity) {
        this.hookActivity = hookActivity;
    }

    public void setBase(Context context, String name) {
        if (!(context instanceof ContextWrapper))
            return;
        ApkResources resources = ContentManager.getManager().name2resources(name);
        if (resources != null) {
            PackageContext packageContext = new PackageContext((Context) FieldManager.getField(context, mBase), resources);
            FieldManager.setField(context, mBase, packageContext);
            if (applicationMap.containsKey(name)) {
                android.app.Application application = applicationMap.get(name);
                LogUtil.i(TAG, "替换application " + application.getClass().getName());
                packageContext.setApplication(applicationMap.get(name));
                if (context instanceof Activity)
                    FieldManager.setField(context, mApplicationActivity, application);
                else if (context instanceof Service)
                    FieldManager.setField(context, mApplicationService, application);
            } else
                LogUtil.i(TAG, "activity " + name + " 没有找到对应的application");
            FieldManager.setField(context, mTheme, resources.mTheme);
            FieldManager.setField(context, mResourcesField, resources.mResources);
            FieldManager.setField(context, mInflater, LayoutInflater.from(context));
        } else
            LogUtil.i(TAG, "没有找到资源包");
        String className = context.getClass().getName();
        if (!activityMap.containsKey(className))
            return;
        ParasXml.ActivityXml activityXml = activityMap.get(className);
        if (activityXml != null && activityXml.theme != null && !activityXml.theme.isEmpty()) {
            Activity activity = (Activity) context;
            activity.setTheme(context.getResources().getIdentifier(activityXml.theme, "style", activity.getPackageName()));
        }
    }

    public void setBase(Context context) {
        if (context == null)
            return;
        String className = context.getClass().getName();
        if (!activityMap.containsKey(className)) {
            LogUtil.i(TAG, "非配置文件中的类");
            return;
        }
        ParasXml.ActivityXml activityXml = activityMap.get(className);
        if (activityXml == null) {
            LogUtil.i(TAG, "置文件中的类,但是为空");
            return;
        }
        setBase(context, activityXml.packageName);
    }

    public ApkResources resources(String packageName) {
        return ContentManager.getManager().name2resources(packageName);
    }


    public Hook(Context context) {
        // TODO Auto-generated constructor stub
        startServers = new Vector<>(10);
        PackageManager packageManager = context.getPackageManager();
        registerActivities = new ArrayList<>();
        registerServices = new ArrayList<>();
        activityMap = new HashMap<>();
        hookPackage = context.getPackageName();
        try {
            PackageInfo info = packageManager
                    .getPackageInfo(hookPackage, PackageManager.GET_ACTIVITIES
                            | PackageManager.GET_SERVICES);
            ActivityInfo[] activityInfos = info.activities;
            if (activityInfos != null && activityInfos.length > 0) {
                for (int i = 0; i < activityInfos.length; i++) {
                    registerActivities.add(activityInfos[i].name);
                }
            }
            ServiceInfo[] serviceInfos = info.services;
            if (serviceInfos != null && serviceInfos.length > 0) {
                for (int i = 0; i < serviceInfos.length; i++) {
                    String service_name = serviceInfos[i].name;
                    registerServices.add(service_name);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        activityThread = new ClassActivityThread(context.getClassLoader(), this);
    }

    public void paras(InputStream inputStream) {
        ParasXml parasXml = new ParasXml(inputStream);
        String packageName = parasXml.packageName;
        android.app.Application application = ContentManager.getManager().getApplication();
        if (packageName != null && !packageName.isEmpty() && !packageName.equals(application.getPackageName())) {
            if (application instanceof Application) {
                if (!applicationMap.containsKey(packageName)) {
                    Application app = (Application) application;
                    android.app.Application application1 = app.addApplication(parasXml.application.application);
                    if (application1 != null) {
                        application = application1;
                        applicationMap.put(packageName, application1);
                    }
                }
            }
        }
        activityMap.putAll(parasXml.activityMap);
        createProvider(application, parasXml);
    }

    private void createProvider(android.app.Application application, ParasXml parasXml) {
        LogUtil.i(TAG, "createProvider");
        List<ProviderInfo> packageInfos = parasXml.providerInfos;
        if (packageInfos == null || packageInfos.isEmpty()) {
            LogUtil.i(TAG, "没有provider要启动");
            return;
        }
        ApplicationInfo applicationInfo = application.getApplicationInfo();
        for (ProviderInfo info : packageInfos) {
            info.applicationInfo = applicationInfo;
            info.packageName = applicationInfo.packageName;
        }

        activityThread.installContentProviders(application, packageInfos);
    }

    public boolean isXml(String className) {
        return activityMap.containsKey(className);
    }

}
