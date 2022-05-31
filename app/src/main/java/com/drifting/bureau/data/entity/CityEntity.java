package com.drifting.bureau.data.entity;

import com.drifting.bureau.util.DigitUtil;

import java.io.Serializable;
import java.util.List;

public class  CityEntity implements Serializable {
    private List<AreaListBean> areaList;

    public List<AreaListBean> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<AreaListBean> areaList) {
        this.areaList = areaList;
    }

    public static class AreaListBean implements Comparable{
        private int code;
        private String name;
        private List<CitylistBeanX> citylist;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<CitylistBeanX> getCitylist() {
            return citylist;
        }

        public void setCitylist(List<CitylistBeanX> citylist) {
            this.citylist = citylist;
        }

        @Override
        public int compareTo(Object o) {
            AreaListBean areaModel = (AreaListBean) o;
            return DigitUtil.getPinYinFirst(this.getName()).compareTo(DigitUtil.getPinYinFirst(areaModel.getName()));

        }

        public static class CitylistBeanX {

            private int code;
            private String name;
            private List<CitylistBean> citylist;

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<CitylistBean> getCitylist() {
                return citylist;
            }

            public void setCitylist(List<CitylistBean> citylist) {
                this.citylist = citylist;
            }

            public static class CitylistBean {

                private int code;
                private String name;

                public int getCode() {
                    return code;
                }

                public void setCode(int code) {
                    this.code = code;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
