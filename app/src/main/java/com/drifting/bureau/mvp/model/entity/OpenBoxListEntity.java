package com.drifting.bureau.mvp.model.entity;

import java.util.List;

public class OpenBoxListEntity {


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
        private Integer safe_box_open_record_id;
        private Integer type;
        private Integer box_id;
        private Integer user_id;
        private Integer create_time;
        private String people_name;
        private String mobile;
        private String address;
        private int is_fictitious;
        private String is_express;
        private String express_name;
        private String express_no;
        private String goods_name;
        private String image;
        private String small_image;

        public String getSmall_image() {
            return small_image;
        }

        public void setSmall_image(String small_image) {
            this.small_image = small_image;
        }

        public Integer getSafe_box_open_record_id() {
            return safe_box_open_record_id;
        }

        public void setSafe_box_open_record_id(Integer safe_box_open_record_id) {
            this.safe_box_open_record_id = safe_box_open_record_id;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getBox_id() {
            return box_id;
        }

        public void setBox_id(Integer box_id) {
            this.box_id = box_id;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public Integer getCreate_time() {
            return create_time;
        }

        public void setCreate_time(Integer create_time) {
            this.create_time = create_time;
        }

        public String getPeople_name() {
            return people_name;
        }

        public void setPeople_name(String people_name) {
            this.people_name = people_name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getIs_fictitious() {
            return is_fictitious;
        }

        public void setIs_fictitious(int is_fictitious) {
            this.is_fictitious = is_fictitious;
        }

        public String getIs_express() {
            return is_express;
        }

        public void setIs_express(String is_express) {
            this.is_express = is_express;
        }

        public String getExpress_name() {
            return express_name;
        }

        public void setExpress_name(String express_name) {
            this.express_name = express_name;
        }

        public String getExpress_no() {
            return express_no;
        }

        public void setExpress_no(String express_no) {
            this.express_no = express_no;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

}
