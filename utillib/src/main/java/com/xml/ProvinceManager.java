package com.xml;

import android.content.Context;

import java.util.List;

/**
 * com.xml
 * 2019/1/2 11:50
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class ProvinceManager {
    private static final String TAG = "ProvinceManager";
    private static ProvinceManager mManager;
    private ProvinceXml mProvinceXml;
    private RegionXml mRegionXml;

    public static ProvinceManager manager(Context context) {
        if (mManager == null) {
            synchronized (TAG) {
                if (mManager == null)
                    new ProvinceManager(context);
            }
        }
        return mManager;
    }

    private ProvinceManager(Context context) {
        mProvinceXml = new ProvinceXml(context);
        mRegionXml = new RegionXml(context);
    }

    /**
     * 2019/1/2 11:59
     * annotation：获取省中的城市
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public List<ProvinceXml.City> citys(String province) {
        return mProvinceXml.citys(province);
    }

    /**
     * 2019/1/2 11:59
     * annotation：获取省的代码
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public String provinceCode(String province) {
        return mProvinceXml.provinceCode(province);
    }

    /**
     * 2019/1/2 11:59
     * annotation：获取省的省会
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public String provinceCapital(String province) {
        return mProvinceXml.provinceCapital(province);
    }

    /**
     * 2019/1/2 12:00
     * annotation：获取省会的首字母
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public String provinceAcronym(String province) {
        return mProvinceXml.provinceAcronym(province);
    }

    /**
     * 2019/1/2 12:01
     * annotation：获取省中的县
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public List<ProvinceXml.County> provinceCounties(String province) {
        return mProvinceXml.provinceCounties(province);
    }

    /**
     * 2019/1/2 12:01
     * annotation：获取城市所在的省
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public String cityProvince(String city) {
        return mProvinceXml.cityProvince(city);
    }

    /**
     * 2019/1/2 12:02
     * annotation：获取城市的县
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public List<ProvinceXml.County> cityCounties(String city) {
        return mProvinceXml.cityCounties(city);
    }

    /**
     * 2019/1/2 12:02
     * annotation：获取城市代码
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public String cityCode(String city) {
        return mProvinceXml.cityCode(city);
    }

    /**
     * 2019/1/2 12:02
     * annotation：获取县所在的省
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public String countyProvince(String county) {
        return mProvinceXml.countyProvince(county);
    }

    /**
     * 2019/1/2 12:02
     * annotation：获取县所在的城市
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public String countyCity(String county) {
        return mProvinceXml.countyCity(county);
    }

    /**
     * 2019/1/2 12:02
     * annotation：获取县代码
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public String countyCode(String county) {
        return mProvinceXml.cityCode(county);
    }

    /**
     * 2019/1/2 12:03
     * annotation：获取省份证所在的地区
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public String idCard2Place(String idCard) {
        return mProvinceXml.idCard2Place(idCard);
    }

    /**
     * 2019/1/2 12:03
     * annotation：获取地区中的省
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public List<RegionXml.Province> region2provinces(String region) {
        return mRegionXml.region2provinces(region);
    }

    /**
     * 2019/1/2 12:04
     * annotation：获取省所在的地区
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public String province2region(String province) {
        return mRegionXml.province2region(province);
    }

    /**
     * 2019/1/2 12:04
     * annotation：获取省的首字母
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public String province2acronym(String province) {
        return mRegionXml.province2acronym(province);
    }

    /**
     * 2019/1/2 12:04
     * annotation：获取首字母的省所在的地区
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public String acronym2region(String acronym) {
        return mRegionXml.acronym2region(acronym);
    }

    /**
     * 2019/1/2 12:05
     * annotation：获取首字母的省
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public String acronym2province(String acronym) {
        return mRegionXml.acronym2province(acronym);
    }
}
