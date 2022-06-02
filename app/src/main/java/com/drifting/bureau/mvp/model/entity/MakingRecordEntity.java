package com.drifting.bureau.mvp.model.entity;

import java.util.List;

public class MakingRecordEntity {

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
            private Integer buyer_id;
            private String buyer_name;
            private Integer explore_id;
            private Integer is_throw;
            private Integer made;
            private Integer made_at;
            private Integer message_id;
            private String money;
            private Integer path_time;
            private Integer space_id;
            private Integer space_order_id;

            public Integer getBuyer_id() {
                return buyer_id;
            }

            public void setBuyer_id(Integer buyer_id) {
                this.buyer_id = buyer_id;
            }

            public String getBuyer_name() {
                return buyer_name;
            }

            public void setBuyer_name(String buyer_name) {
                this.buyer_name = buyer_name;
            }

            public Integer getExplore_id() {
                return explore_id;
            }

            public void setExplore_id(Integer explore_id) {
                this.explore_id = explore_id;
            }

            public Integer getIs_throw() {
                return is_throw;
            }

            public void setIs_throw(Integer is_throw) {
                this.is_throw = is_throw;
            }

            public Integer getMade() {
                return made;
            }

            public void setMade(Integer made) {
                this.made = made;
            }

            public Integer getMade_at() {
                return made_at;
            }

            public void setMade_at(Integer made_at) {
                this.made_at = made_at;
            }

            public Integer getMessage_id() {
                return message_id;
            }

            public void setMessage_id(Integer message_id) {
                this.message_id = message_id;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public Integer getPath_time() {
                return path_time;
            }

            public void setPath_time(Integer path_time) {
                this.path_time = path_time;
            }

            public Integer getSpace_id() {
                return space_id;
            }

            public void setSpace_id(Integer space_id) {
                this.space_id = space_id;
            }

            public Integer getSpace_order_id() {
                return space_order_id;
            }

            public void setSpace_order_id(Integer space_order_id) {
                this.space_order_id = space_order_id;
            }
        }

}
