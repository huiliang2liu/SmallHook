package com.hook;

import android.content.pm.ProviderInfo;
import android.os.Parcel;
import android.os.Parcelable;

import com.utils.LogUtil;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * com.hook
 * 2018/10/24 16:48
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class ParasXml{
    private final static String TAG = "ParasXml";
    protected ApplicationXml application;
    protected Map<String, ActivityXml> activityMap;
    protected List<ProviderInfo> providerInfos;
    public String packageName;

    ParasXml(String packageName) {
        this.packageName = packageName;
    }

    ParasXml(InputStream is) {
        activityMap = new HashMap<>();
        providerInfos = new ArrayList<>();
        paras(is);
    }


    public ActivityXml activity(String activity) {
        if (activityMap.containsKey(activity))
            return activityMap.get(activity);
        return null;
    }

    public ActivityXml action(String action) {
        if (action != null && !action.isEmpty()) {
            for (ActivityXml activityXml : activityMap.values()) {
                if (action.equals(activityXml.action))
                    return activityXml;
            }
        }
        return null;
    }


    public void merge(ParasXml parasXml) {
        if (parasXml == null)
            return;
        activityMap.putAll(parasXml.activityMap);
    }

    private void paras(InputStream inputStream) {
        if (inputStream == null)
            return;
        try {
            LogUtil.i(TAG, "解析配置文件");
            DocumentBuilder documentBuilder = DocumentBuilderFactory
                    .newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            NodeList list = document.getChildNodes();
            Node node = list.item(0);
            if ("application".equals(node.getNodeName()))
                parasApplication(node);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parasApplication(Node node) {
        NamedNodeMap map = node.getAttributes();
        application = new ApplicationXml();
        application.application = parasAtt(map, "class");
        LogUtil.i(TAG, "application class is " + application.application);
        packageName = parasAtt(map, "package");
        application.packageName = packageName;
        application.pit = parasAtt(map, "pit");
        application.theme = parasAtt(map, "theme");
        NodeList list = node.getChildNodes();
        if (list != null && list.getLength() > 0) {
            for (int i = 0; i < list.getLength(); i++) {
                Node childNode = list.item(i);
                if ("activity".equals(childNode.getNodeName())) {
                    ActivityXml activityXml = parasActivity(childNode, application);
                    if (activityXml != null)
                        activityMap.put(activityXml.className, activityXml);
                } else if ("service".equals(childNode.getNodeName())) {
                    ActivityXml serviceXml = parasService(childNode, application);
                    if (serviceXml != null)
                        activityMap.put(serviceXml.className, serviceXml);

                } else if ("provider".equals(childNode.getNodeName())) {
                    ProviderXml providerXml = parasProvider(childNode);
                    if (providerXml != null) {
                        providerInfos.add(providerXml2providerInfo(providerXml));
                    }
                }
            }
        }
    }

    private ProviderInfo providerXml2providerInfo(ProviderXml providerXml) {
        ProviderInfo providerInfo = new ProviderInfo();
        providerInfo.authority = providerXml.authority;
        providerInfo.name = providerXml.className;
        providerInfo.exported = providerXml.exported;
        providerInfo.grantUriPermissions = providerXml.grantUriPermissions;
        return providerInfo;
    }

    private ActivityXml parasActivity(Node valueNode, ApplicationXml xml) {
        NamedNodeMap map = valueNode.getAttributes();
        if (map.getLength() <= 0)
            return null;
        String activityName = parasAtt(map, "class");
        if (activityName == null || activityName.isEmpty()) {
            LogUtil.i(TAG, "没有启动的activity");
            return null;
        }
        LogUtil.i(TAG, "activity class is " + activityName);
        ActivityXml activityXml = new ActivityXml();
        activityXml.className = activityName;
        activityXml.packageName = parasAtt(map, "package");
        if (activityXml.packageName == null || activityXml.packageName.isEmpty())
            activityXml.packageName = xml.packageName;
        activityXml.replace = parasAtt(map, "replace");
        activityXml.application = parasAtt(map, "application");
        if (activityXml.application == null || activityXml.application.isEmpty())
            activityXml.application = xml.application;
        activityXml.pit = parasAtt(map, "pit");
        if (activityXml.pit == null || activityXml.pit.isEmpty())
            activityXml.pit = xml.pit;
        activityXml.theme = parasAtt(map, "theme");
        if (activityXml.theme == null || activityXml.theme.isEmpty())
            activityXml.theme = xml.theme;
        activityXml.action = parasAtt(map, "action");
        activityXml.layout = parasAtt(map, "layout");
        activityXml.statusBarColor = parasAtt(map, "statusBarColor");
        return activityXml;
    }


    private ActivityXml parasService(Node valueNode, ApplicationXml xml) {
        NamedNodeMap map = valueNode.getAttributes();
        if (map.getLength() <= 0)
            return null;
        String activityName = parasAtt(map, "class");
        if (activityName == null || activityName.isEmpty()) {
            LogUtil.i(TAG, "没有启动的service");
            return null;
        }
        LogUtil.i(TAG, "service class is " + activityName);
        ActivityXml serviceXml = new ActivityXml();
        serviceXml.className = activityName;
        serviceXml.packageName = parasAtt(map, "package");
        if (serviceXml.packageName == null || serviceXml.packageName.isEmpty())
            serviceXml.packageName = xml.packageName;
        serviceXml.replace = parasAtt(map, "replace");
        serviceXml.application = parasAtt(map, "application");
        if (serviceXml.application == null || serviceXml.application.isEmpty())
            serviceXml.application = xml.application;
        serviceXml.pit = parasAtt(map, "pit");
        if (serviceXml.pit == null || serviceXml.pit.isEmpty())
            serviceXml.pit = xml.pit;
        serviceXml.action = parasAtt(map, "action");
        return serviceXml;
    }


    private ProviderXml parasProvider(Node valueNode) {
        NamedNodeMap map = valueNode.getAttributes();
        if (map.getLength() <= 0)
            return null;
        String activityName = parasAtt(map, "class");
        if (activityName == null || activityName.isEmpty()) {
            LogUtil.i(TAG, "没有启动的provider");
            return null;
        }
        LogUtil.i(TAG, "provider class is " + activityName);
        ProviderXml providerXml = new ProviderXml();
        providerXml.className = activityName;
        providerXml.authority = parasAtt(map, "authority");
        providerXml.exported = Boolean.valueOf(parasAtt(map, "exported"));
        providerXml.grantUriPermissions = Boolean.valueOf(parasAtt(map, "grantUriPermissions"));
        return providerXml;
    }

    private String parasAtt(NamedNodeMap map, String attName) {
        Node node = map.getNamedItem(attName);
        if (node == null)
            return null;
        return node.getTextContent();
    }

    public static class ApplicationXml {
        String packageName;
        String application;
        String pit;
        String theme;
    }

    public static class ActivityXml implements Parcelable{
        public String className;
        public String packageName;
        public String action;
        public String replace;
        public String application;
        public String pit;
        public String theme;
        public String layout;
        public String statusBarColor;
        public static final Parcelable.Creator<ActivityXml> CREATOR
                = new Parcelable.Creator<ActivityXml>() {
            public ActivityXml createFromParcel(Parcel in) {
                return new ActivityXml(in);
            }

            public ActivityXml[] newArray(int size) {
                return new ActivityXml[size];
            }
        };
        private ActivityXml(){

        }
        private ActivityXml(Parcel in){
            className=in.readString();
            packageName=in.readString();
            action=in.readString();
            replace=in.readString();
            application=in.readString();
            pit=in.readString();
            theme=in.readString();
        }
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(className);
            dest.writeString(packageName);
            dest.writeString(action);
            dest.writeString(replace);
            dest.writeString(application);
            dest.writeString(pit);
            dest.writeString(theme);
        }
    }


    public static class ProviderXml {
        String className;
        String authority;
        boolean exported;
        boolean grantUriPermissions;
    }
}
