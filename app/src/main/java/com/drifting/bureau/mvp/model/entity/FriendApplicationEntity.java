package com.drifting.bureau.mvp.model.entity;

import java.util.List;

public class FriendApplicationEntity {
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
            private Integer apply_id;
            private Integer from_user_id;
            private Integer to_user_id;
            private String from_user_name;
            private Integer status;
            private String profile_photo;
            private Integer created_at_int;
            private String say_something;

            public Integer getApply_id() {
                return apply_id;
            }

            public void setApply_id(Integer apply_id) {
                this.apply_id = apply_id;
            }

            public Integer getFrom_user_id() {
                return from_user_id;
            }

            public void setFrom_user_id(Integer from_user_id) {
                this.from_user_id = from_user_id;
            }

            public Integer getTo_user_id() {
                return to_user_id;
            }

            public void setTo_user_id(Integer to_user_id) {
                this.to_user_id = to_user_id;
            }

            public String getFrom_user_name() {
                return from_user_name;
            }

            public void setFrom_user_name(String from_user_name) {
                this.from_user_name = from_user_name;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public String getProfile_photo() {
                return profile_photo;
            }

            public void setProfile_photo(String profile_photo) {
                this.profile_photo = profile_photo;
            }

            public Integer getCreated_at_int() {
                return created_at_int;
            }

            public void setCreated_at_int(Integer created_at_int) {
                this.created_at_int = created_at_int;
            }

            public String getSay_something() {
                return say_something;
            }

            public void setSay_something(String say_something) {
                this.say_something = say_something;
            }

    }
}
