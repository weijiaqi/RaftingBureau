package com.drifting.bureau.mvp.model.entity;

import java.util.List;

public class SysmessageMineEntity {


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
        private Integer sys_msg_id;
        private Integer user_id;

        private String title;
        private String content;
        private Integer is_read;
        private Integer created_at_int;
        private Integer updated_at_int;
        private Integer msy_type;
        private Integer msy_type_sub;

        private Integer message_id;

        public Integer getMessage_id() {
            return message_id;
        }

        public void setMessage_id(Integer message_id) {
            this.message_id = message_id;
        }

        public Integer getMsy_type() {
            return msy_type;
        }

        public void setMsy_type(Integer msy_type) {
            this.msy_type = msy_type;
        }

        public Integer getMsy_type_sub() {
            return msy_type_sub;
        }

        public void setMsy_type_sub(Integer msy_type_sub) {
            this.msy_type_sub = msy_type_sub;
        }

        public Integer getSys_msg_id() {
            return sys_msg_id;
        }

        public void setSys_msg_id(Integer sys_msg_id) {
            this.sys_msg_id = sys_msg_id;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Integer getIs_read() {
            return is_read;
        }

        public void setIs_read(Integer is_read) {
            this.is_read = is_read;
        }

        public Integer getCreated_at_int() {
            return created_at_int;
        }

        public void setCreated_at_int(Integer created_at_int) {
            this.created_at_int = created_at_int;
        }

        public Integer getUpdated_at_int() {
            return updated_at_int;
        }

        public void setUpdated_at_int(Integer updated_at_int) {
            this.updated_at_int = updated_at_int;
        }
    }

}
