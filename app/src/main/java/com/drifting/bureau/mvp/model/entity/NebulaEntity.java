package com.drifting.bureau.mvp.model.entity;

import java.util.List;

public class NebulaEntity {


        private Integer limit;
        private Integer page;
        private Integer count;
        private List<ListBean> list;
        private Integer duration;

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

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public static class ListBean {
            private Integer msg_nebula_id;
            private Integer message_id;
            private String nebula;
            private String nebula_code;
            private Integer counter;
            private Integer nebula_x;
            private Integer nebula_y;
            private Integer created_at_int;
            private String meta_power;

            public String getMeta_power() {
                return meta_power;
            }

            public void setMeta_power(String meta_power) {
                this.meta_power = meta_power;
            }

            public String getNebula_code() {
                return nebula_code;
            }

            public void setNebula_code(String nebula_code) {
                this.nebula_code = nebula_code;
            }

            public Integer getMsg_nebula_id() {
                return msg_nebula_id;
            }

            public void setMsg_nebula_id(Integer msg_nebula_id) {
                this.msg_nebula_id = msg_nebula_id;
            }

            public Integer getMessage_id() {
                return message_id;
            }

            public void setMessage_id(Integer message_id) {
                this.message_id = message_id;
            }

            public String getNebula() {
                return nebula;
            }

            public void setNebula(String nebula) {
                this.nebula = nebula;
            }

            public Integer getCounter() {
                return counter;
            }

            public void setCounter(Integer counter) {
                this.counter = counter;
            }

            public Integer getNebula_x() {
                return nebula_x;
            }

            public void setNebula_x(Integer nebula_x) {
                this.nebula_x = nebula_x;
            }

            public Integer getNebula_y() {
                return nebula_y;
            }

            public void setNebula_y(Integer nebula_y) {
                this.nebula_y = nebula_y;
            }

            public Integer getCreated_at_int() {
                return created_at_int;
            }

            public void setCreated_at_int(Integer created_at_int) {
                this.created_at_int = created_at_int;
            }
        }


}
