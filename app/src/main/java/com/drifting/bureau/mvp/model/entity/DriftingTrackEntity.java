package com.drifting.bureau.mvp.model.entity;

import java.util.List;

public class DriftingTrackEntity {

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
            private Integer message_id;
            private Integer explore_id;
            private Integer user_id;
            private String user_name;
            private String content;
            private Integer type_id;
            private String image;
            private Integer is_drifting;
            private Integer is_throw;
            private Integer total_attend;
            private String created_at_int;
            private Integer created_by_me;
            private String who_hold;

            public String getWho_hold() {
                return who_hold;
            }

            public void setWho_hold(String who_hold) {
                this.who_hold = who_hold;
            }

            public Integer getMessage_id() {
                return message_id;
            }

            public void setMessage_id(Integer message_id) {
                this.message_id = message_id;
            }

            public Integer getExplore_id() {
                return explore_id;
            }

            public void setExplore_id(Integer explore_id) {
                this.explore_id = explore_id;
            }

            public Integer getUser_id() {
                return user_id;
            }

            public void setUser_id(Integer user_id) {
                this.user_id = user_id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public Integer getType_id() {
                return type_id;
            }

            public void setType_id(Integer type_id) {
                this.type_id = type_id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public Integer getIs_drifting() {
                return is_drifting;
            }

            public void setIs_drifting(Integer is_drifting) {
                this.is_drifting = is_drifting;
            }

            public Integer getIs_throw() {
                return is_throw;
            }

            public void setIs_throw(Integer is_throw) {
                this.is_throw = is_throw;
            }

            public Integer getTotal_attend() {
                return total_attend;
            }

            public void setTotal_attend(Integer total_attend) {
                this.total_attend = total_attend;
            }

            public String getCreated_at_int() {
                return created_at_int;
            }

            public void setCreated_at_int(String created_at_int) {
                this.created_at_int = created_at_int;
            }

            public Integer getCreated_by_me() {
                return created_by_me;
            }

            public void setCreated_by_me(Integer created_by_me) {
                this.created_by_me = created_by_me;
            }

    }
}
