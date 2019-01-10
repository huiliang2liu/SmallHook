package com.xml;

import android.content.Context;

import com.utils.LogUtil;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * com.xh.xml author:liuhuiliang email:825378291@qq.com instruction: 2018-6-12
 * 下午5:48:21
 **/
public class ProvinceXml {
    private static final String TAG = "ProvinceXml";
    private List<Province> provinces;

     ProvinceXml(Context context) {
        // TODO Auto-generated constructor stub
        if (context == null) {
            LogUtil.e(TAG, "context is null");
            return;
        }
        InputStream is = null;
        try {
            is = context.getAssets().open("xml/province.xml");
            if (is == null) {
                LogUtil.e(TAG, "not found file province.xml");
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
            int provinceSize = list.getLength();
            provinces = new ArrayList<>();
            for (int i = 0; i < provinceSize; i++) {
                Node province = list.item(i);
                String acronym = province.getNodeName();
                if ("#text".equals(acronym))
                    continue;
                NamedNodeMap provinceMap = province.getAttributes();
                String provinceCode = provinceMap.getNamedItem("code")
                        .getTextContent().trim();
                String provinceBenelux = provinceMap.getNamedItem("benelux")
                        .getTextContent().trim();
                String provinceCapital = null;
                Node capitalNode = provinceMap.getNamedItem("capital");
                if (capitalNode != null)
                    provinceCapital = capitalNode.getTextContent().trim();
                NodeList cityList = province.getChildNodes();
                List<City> cities = null;
                if (cityList != null && cityList.getLength() > 0) {
                    int citySize = cityList.getLength();
                    cities = new ArrayList<>(citySize);
                    for (int j = 0; j < citySize; j++) {
                        Node cityNode = cityList.item(j);
                        String city = cityNode.getNodeName();
                        if ("#text".equals(city))
                            continue;
                        String cityCode = null;
                        Node cityCodeNode = cityNode.getAttributes()
                                .getNamedItem("code");
                        if (cityCodeNode != null)
                            cityCode = cityCodeNode.getTextContent().trim();
                        List<County> counties = null;
                        NodeList countyList = cityNode.getChildNodes();
                        if (countyList != null && countyList.getLength() > 0) {
                            int countySize = countyList.getLength();
                            counties = new ArrayList<>(countySize);
                            for (int k = 0; k < countySize; k++) {
                                Node countyNode = countyList.item(k);
                                String county = countyNode.getNodeName();
                                if ("#text".equals(county))
                                    continue;
                                String code = null;
                                Node countyCode = countyNode.getAttributes()
                                        .getNamedItem("code");
                                if (countyCode != null)
                                    code = countyCode.getTextContent().trim();
                                counties.add(new County(county, code));
                            }
                        }
                        cities.add(new City(city, cityCode, counties));
                    }
                }
                provinces.add(new Province(provinceBenelux, acronym,
                        provinceCode, provinceCapital, cities));
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (Exception e2) {
                    // TODO: handle exception
                }
        }
    }

    public List<City> citys(String province) {
        if (province == null || province.isEmpty() || provinces == null || provinces.isEmpty())
            return null;
        int len = provinces.size();
        for (int i = 0; i < len; i++) {
            Province mProvince = provinces.get(i);
            if (province.equals(mProvince.province))
                return mProvince.cities;
        }
        return null;
    }

    public String provinceCode(String province) {
        if (province == null || province.isEmpty() || provinces == null || provinces.isEmpty())
            return null;
        int len = provinces.size();
        for (int i = 0; i < len; i++) {
            Province mProvince = provinces.get(i);
            if (province.equals(mProvince.province))
                return mProvince.code;
        }
        return null;
    }

    public String provinceCapital(String province) {
        if (province == null || province.isEmpty() || provinces == null || provinces.isEmpty())
            return null;
        int len = provinces.size();
        for (int i = 0; i < len; i++) {
            Province mProvince = provinces.get(i);
            if (province.equals(mProvince.province))
                return mProvince.capital;
        }
        return null;
    }

    public String provinceAcronym(String province) {
        if (province == null || province.isEmpty() || provinces == null || provinces.isEmpty())
            return null;
        int len = provinces.size();
        for (int i = 0; i < len; i++) {
            Province mProvince = provinces.get(i);
            if (province.equals(mProvince.province))
                return mProvince.acronym;
        }
        return null;
    }

    public List<County> provinceCounties(String province) {
        if (province == null || province.isEmpty() || provinces == null || provinces.isEmpty())
            return null;
        int len = provinces.size();
        for (int i = 0; i < len; i++) {
            Province mProvince = provinces.get(i);
            if (province.equals(mProvince.province)) {
                List<City> cities = mProvince.cities;
                if (cities == null || cities.isEmpty())
                    return null;
                List<County> counties = new ArrayList<>();
                int citySize = cities.size();
                for (int j = 0; j < citySize; j++) {
                    List<County> list = cities.get(j).counties;
                    if (list == null || list.isEmpty())
                        continue;
                    counties.addAll(list);
                }
                return counties;
            }
        }
        return null;
    }

    public String cityProvince(String city) {
        if (city == null || city.isEmpty() || provinces == null || provinces.isEmpty())
            return null;
        int provinceSize = provinces.size();
        for (int i = 0; i < provinceSize; i++) {
            Province mProvince = provinces.get(i);
            List<City> cities = mProvince.cities;
            if (cities == null || cities.isEmpty())
                continue;
            int citySize = cities.size();
            for (int j = 0; j < citySize; j++) {
                City mCity = cities.get(j);
                if (city.equals(mCity.city))
                    return mProvince.province;
            }
        }
        return null;
    }

    public List<County> cityCounties(String city) {
        if (city == null || city.isEmpty() || provinces == null || provinces.isEmpty())
            return null;
        int provinceSize = provinces.size();
        for (int i = 0; i < provinceSize; i++) {
            Province mProvince = provinces.get(i);
            List<City> cities = mProvince.cities;
            if (cities == null || cities.isEmpty())
                continue;
            int citySize = cities.size();
            for (int j = 0; j < citySize; j++) {
                City mCity = cities.get(j);
                if (city.equals(mCity.city))
                    return mCity.counties;
            }
        }
        return null;
    }

    public String cityCode(String city) {
        if (city == null || city.isEmpty() || provinces == null || provinces.isEmpty())
            return null;
        int provinceSize = provinces.size();
        for (int i = 0; i < provinceSize; i++) {
            Province mProvince = provinces.get(i);
            List<City> cities = mProvince.cities;
            if (cities == null || cities.isEmpty())
                continue;
            int citySize = cities.size();
            for (int j = 0; j < citySize; j++) {
                City mCity = cities.get(j);
                if (city.equals(mCity.city))
                    return mProvince.code + mCity.code;
            }
        }
        return null;
    }

    public String countyProvince(String county) {
        if (county == null || county.isEmpty() || provinces == null || provinces.isEmpty())
            return null;
        int provinceSize = provinces.size();
        for (int i = 0; i < provinceSize; i++) {
            Province mProvince = provinces.get(i);
            List<City> cities = mProvince.cities;
            if (cities == null || cities.isEmpty())
                continue;
            int citySize = cities.size();
            for (int j = 0; j < citySize; j++) {
                City mCity = cities.get(j);
                List<County> counties = mCity.counties;
                if (counties == null || counties.isEmpty())
                    continue;
                int countySize = counties.size();
                for (int k = 0; k < countySize; k++) {
                    County mCounty = counties.get(k);
                    if (county.equals(mCounty.county))
                        return mProvince.province;
                }
            }
        }
        return null;
    }

    public String countyCity(String county) {
        if (county == null || county.isEmpty() || provinces == null || provinces.isEmpty())
            return null;
        int provinceSize = provinces.size();
        for (int i = 0; i < provinceSize; i++) {
            Province mProvince = provinces.get(i);
            List<City> cities = mProvince.cities;
            if (cities == null || cities.isEmpty())
                continue;
            int citySize = cities.size();
            for (int j = 0; j < citySize; j++) {
                City mCity = cities.get(j);
                List<County> counties = mCity.counties;
                if (counties == null || counties.isEmpty())
                    continue;
                int countySize = counties.size();
                for (int k = 0; k < countySize; k++) {
                    County mCounty = counties.get(k);
                    if (county.equals(mCounty.county))
                        return mCity.city;
                }
            }
        }
        return null;
    }

    public String countyCode(String county) {
        if (county == null || county.isEmpty() || provinces == null || provinces.isEmpty())
            return null;
        int provinceSize = provinces.size();
        for (int i = 0; i < provinceSize; i++) {
            Province mProvince = provinces.get(i);
            List<City> cities = mProvince.cities;
            if (cities == null || cities.isEmpty())
                continue;
            int citySize = cities.size();
            for (int j = 0; j < citySize; j++) {
                City mCity = cities.get(j);
                List<County> counties = mCity.counties;
                if (counties == null || counties.isEmpty())
                    continue;
                int countySize = counties.size();
                for (int k = 0; k < countySize; k++) {
                    County mCounty = counties.get(k);
                    if (county.equals(mCounty.county))
                        return mProvince.code + mCity.code + mCounty.code;
                }
            }
        }
        return null;
    }

    public String idCard2Place(String idCard) {
        if (idCard == null || idCard.isEmpty() || idCard.length() < 6
                || provinces == null || provinces.isEmpty())
            return null;
        if (idCard.startsWith("430419"))
            return "湖南省衡阳市耒阳市";
        int provinceSize = provinces.size();
        for (int i = 0; i < provinceSize; i++) {
            Province province = provinces.get(i);
            String place = "";
            if (idCard.substring(0, 2).equals(province.code)) {
                List<City> cities = province.cities;
                place += province.province;
                if (cities == null || cities.isEmpty())
                    return null;
                int citySize = cities.size();
                for (int j = 0; j < citySize; j++) {
                    City city = cities.get(j);
                    List<County> counties = city.counties;
                    if (counties == null || counties.isEmpty()) {
                        if (idCard.substring(2, 6).equals(city.code)) {
                            place += city.city;
                            break;
                        }
                    } else {
                        if (idCard.substring(2, 4).equals(city.code)) {
                            place += city.city;
                            int countySize = counties.size();
                            for (int k = 0; k < countySize; k++) {
                                County county = counties.get(k);
                                if (idCard.substring(4, 6).equals(county.code)) {
                                    place += county.county;
                                    break;
                                }
                            }
                        }
                    }
                }
                return place;
            }
        }
        return null;
    }

    public void print() {
        // TODO Auto-generated method stub
        if (provinces == null || provinces.isEmpty())
            return;
        int provinceSize = provinces.size();
        System.out.println("acronym\tcode\tbenelux\tcapital");
        for (int i = 0; i < provinceSize; i++) {
            Province province = provinces.get(i);
            System.out.println(province.acronym + "\t" + province.code + "\t"
                    + province.province + "\t" + province.capital);
            List<City> cities = province.cities;
            if (cities == null || cities.isEmpty())
                continue;
            int citySize = cities.size();
            System.out.println("\tcity\tcode");
            for (int j = 0; j < citySize; j++) {
                City city = cities.get(j);
                System.out.println("\t" + city.city + "\t" + city.code);
                List<County> counties = city.counties;
                if (counties == null || counties.isEmpty())
                    continue;
                int countySize = counties.size();
                System.out.println("\t\tcounty\tcode");
                for (int k = 0; k < countySize; k++) {
                    County county = counties.get(k);
                    System.out.println("\t\t" + county.county + "\t"
                            + county.code);
                }
            }
        }

    }

    public class Province {
        public Province(String province, String acronym, String code,
                        String capital, List<City> cities) {
            // TODO Auto-generated constructor stub
            this.province = province;
            this.acronym = acronym;
            this.code = code;
            this.capital = capital;
            this.cities = cities;
        }

        public String province;
        public String acronym;
        public String code;
        public String capital;
        public List<City> cities;
    }

    public class City {
        public City(String city, String code, List<County> counties) {
            // TODO Auto-generated constructor stub
            this.city = city;
            this.code = code;
            this.counties = counties;
        }

        public String city;
        public String code;
        public List<County> counties;
    }

    public class County {
        public County(String county, String code) {
            // TODO Auto-generated constructor stub
            this.county = county;
            this.code = code;
        }

        public String county;
        public String code;
    }
}
