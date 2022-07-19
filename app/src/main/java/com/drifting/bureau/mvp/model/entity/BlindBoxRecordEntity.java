package com.drifting.bureau.mvp.model.entity;

import java.util.List;

public class BlindBoxRecordEntity {


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
            private Integer open_log_id;
            private Integer user_id;
            private String image;
            private String object_name;
            private Integer created_at_int;

            public Integer getOpen_log_id() {
                return open_log_id;
            }

            public void setOpen_log_id(Integer open_log_id) {
                this.open_log_id = open_log_id;
            }

            public Integer getUser_id() {
                return user_id;
            }

            public void setUser_id(Integer user_id) {
                this.user_id = user_id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getObject_name() {
                return object_name;
            }

            public void setObject_name(String object_name) {
                this.object_name = object_name;
            }

            public Integer getCreated_at_int() {
                return created_at_int;
            }

            public void setCreated_at_int(Integer created_at_int) {
                this.created_at_int = created_at_int;
            }
        }

}
