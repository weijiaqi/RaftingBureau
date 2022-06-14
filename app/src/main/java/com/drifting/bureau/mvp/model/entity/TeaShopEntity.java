package com.drifting.bureau.mvp.model.entity;

import java.util.List;

public class TeaShopEntity {


        private Integer limit;
        private Integer page;
        private Integer count;
        private List<ListBean> list;

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private Integer business_id;
            private String image;
            private String title;
            private String business_logo;
            private String business_name;
            private Integer status;
            private Integer sort;
            private String introduce;
            private Integer is_new;
            private String video;
            private Integer is_delete;
            private String address;
            private String lng;
            private String lat;
            private String tel;
            private Integer open_status;
            private String opening;
            private String opening_end;
            private Integer distance;

            public Integer getBusiness_id() {
                return business_id;
            }

            public void setBusiness_id(Integer business_id) {
                this.business_id = business_id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getBusiness_logo() {
                return business_logo;
            }

            public void setBusiness_logo(String business_logo) {
                this.business_logo = business_logo;
            }

            public String getBusiness_name() {
                return business_name;
            }

            public void setBusiness_name(String business_name) {
                this.business_name = business_name;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public Integer getSort() {
                return sort;
            }

            public void setSort(Integer sort) {
                this.sort = sort;
            }

            public String getIntroduce() {
                return introduce;
            }

            public void setIntroduce(String introduce) {
                this.introduce = introduce;
            }

            public Integer getIs_new() {
                return is_new;
            }

            public void setIs_new(Integer is_new) {
                this.is_new = is_new;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public Integer getIs_delete() {
                return is_delete;
            }

            public void setIs_delete(Integer is_delete) {
                this.is_delete = is_delete;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public Integer getOpen_status() {
                return open_status;
            }

            public void setOpen_status(Integer open_status) {
                this.open_status = open_status;
            }

            public String getOpening() {
                return opening;
            }

            public void setOpening(String opening) {
                this.opening = opening;
            }

            public String getOpening_end() {
                return opening_end;
            }

            public void setOpening_end(String opening_end) {
                this.opening_end = opening_end;
            }

            public Integer getDistance() {
                return distance;
            }

            public void setDistance(Integer distance) {
                this.distance = distance;
            }

    }
}
