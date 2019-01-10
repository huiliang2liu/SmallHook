package com.xml;

import android.content.Context;

import com.utils.LogUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * com.xh.xml author:liuhuiliang email:825378291@qq.com instruction: 2018-6-12
 * 下午4:08:47
 **/
public class RegionXml {
    private static final String TAG = "RegionXml";
    private Map<String, List<Province>> map;

    RegionXml(Context context) {
        // TODO Auto-generated constructor stub
        if (context == null) {
            LogUtil.e(TAG, "context is null");
            return;
        }
        InputStream is = null;
        try {
            is = context.getAssets().open("xml/region.xml");
            if (is == null) {
                LogUtil.e(TAG, "not found file region.xml");
                return;
            }
            DocumentBuilder documentBuilder = DocumentBuilderFactory
                    .newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(is);
            NodeList list = document.getChildNodes();
            if (list == null || list.getLength() <= 0) {
                LogUtil.e(TAG, "NodeList is empty");
                return;
            }
            list = list.item(0).getChildNodes();
            if (list == null || list.getLength() <= 0) {
                LogUtil.e(TAG, "NodeList child is empty");
                return;
            }
            map = new HashMap<>(40, 0.9f);
            int len = list.getLength();
            for (int i = 0; i < len; i++) {
                Node node = list.item(i);
                NodeList provinceList = node.getChildNodes();
                if (provinceList == null || provinceList.getLength() <= 0) {
                    LogUtil.e(TAG, "province  is empty province is " + node.getNodeName());
                    continue;
                }
                String region = node.getNodeName().trim();
                if ("#text".equals(region))
                    continue;
                int size = provinceList.getLength();
                List<Province> provinces = new ArrayList<>();
                for (int j = 0; j < size; j++) {
                    node = provinceList.item(j);
                    String province = node.getNodeName().trim();
                    if ("#text".equals(province))
                        continue;
                    String acronym = node.getAttributes()
                            .getNamedItem("acronym").getTextContent().trim();
                    provinces.add(new Province(province, acronym));
                }
                map.put(region, provinces);
            }

        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (Exception e2) {
                    // TODO: handle exception
                }
        }
    }

    public void print() {
        if (map == null || map.size() <= 0) {
            LogUtil.e(TAG, "is null");
            return;
        }
        Iterator<String> set = map.keySet().iterator();
        while (set.hasNext()) {
            String key = set.next();
            List<Province> provinces = map.get(key);
            int size = provinces.size();

            for (int i = 0; i < size; i++) {
                System.out.print(key);
                System.out.print("\t");
                Province province = provinces.get(i);
                System.out.print(province.province);
                System.out.print("\t");
                System.out.println(province.acronym);
            }
            System.out.println();
        }
    }

    public List<Province> region2provinces(String region) {
        if (region == null || region.isEmpty())
            return null;
        String mRegion = region;
        if (mRegion.length() == 2)
            mRegion += "地区";
        if (mRegion.length() < 4)
            return null;
        return map.get(mRegion);
    }

    public String province2region(String province) {
        if (province == null || province.isEmpty())
            return null;
        if (map == null || map.size() <= 0)
            return null;
        Iterator<String> keyIterator = map.keySet().iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            List<Province> provinces = map.get(key);
            if (provinces == null || provinces.isEmpty())
                continue;
            int size = provinces.size();
            for (int i = 0; i < size; i++) {
                Province province2 = provinces.get(i);
                if (province.equals(province2.province))
                    return key;
            }
        }
        return null;
    }

    public String province2acronym(String province) {
        if (province == null || province.isEmpty())
            return null;
        if (map == null || map.isEmpty())
            return null;
        Iterator<String> keyIterator = map.keySet().iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            List<Province> provinces = map.get(key);
            if (provinces == null || provinces.isEmpty())
                continue;
            int size = provinces.size();
            for (int i = 0; i < size; i++) {
                Province province2 = provinces.get(i);
                if (province.equals(province2.province))
                    return province2.acronym;
            }
        }
        return null;
    }

    public String acronym2region(String acronym) {
        if (acronym == null || acronym.isEmpty())
            return null;
        if (map == null || map.isEmpty())
            return null;
        Iterator<String> keyIterator = map.keySet().iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            List<Province> provinces = map.get(key);
            if (provinces == null || provinces.isEmpty())
                continue;
            int size = provinces.size();
            for (int i = 0; i < size; i++) {
                Province province2 = provinces.get(i);
                if (acronym.equals(province2.acronym))
                    return key;
            }
        }
        return null;
    }

    public String acronym2province(String acronym) {
        if (acronym == null || acronym.isEmpty())
            return null;
        if (map == null || map.isEmpty())
            return null;
        Iterator<String> keyIterator = map.keySet().iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            List<Province> provinces = map.get(key);
            if (provinces == null || provinces.isEmpty())
                continue;
            int size = provinces.size();
            for (int i = 0; i < size; i++) {
                Province province2 = provinces.get(i);
                if (acronym.equals(province2.acronym))
                    return province2.province;
            }
        }
        return null;
    }

    public class Province {
        public String province;// 名字
        public String acronym;// 简称

        private Province(String name, String acronym) {
            // TODO Auto-generated constructor stub
            this.province = name;
            this.acronym = acronym;
        }
    }
}
