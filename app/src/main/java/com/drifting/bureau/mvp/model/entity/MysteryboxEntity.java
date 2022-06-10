package com.drifting.bureau.mvp.model.entity;

import java.util.List;

public class MysteryboxEntity {



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
            private Integer event_id;
            private Integer user_id;
            private Integer e_type;
            private String event;
            private Integer created_at_int;

            public Integer getEvent_id() {
                return event_id;
            }

            public void setEvent_id(Integer event_id) {
                this.event_id = event_id;
            }

            public Integer getUser_id() {
                return user_id;
            }

            public void setUser_id(Integer user_id) {
                this.user_id = user_id;
            }

            public Integer getE_type() {
                return e_type;
            }

            public void setE_type(Integer e_type) {
                this.e_type = e_type;
            }

            public String getEvent() {
                return event;
            }

            public void setEvent(String event) {
                this.event = event;
            }

            public Integer getCreated_at_int() {
                return created_at_int;
            }

            public void setCreated_at_int(Integer created_at_int) {
                this.created_at_int = created_at_int;
            }
        }

}
