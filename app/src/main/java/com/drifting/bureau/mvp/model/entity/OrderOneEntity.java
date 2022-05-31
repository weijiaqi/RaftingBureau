package com.drifting.bureau.mvp.model.entity;

public class OrderOneEntity {

        private Integer order_sub_id;
        private Integer path_time;
        private Integer space_id;
        private Integer space_order_id;
        private Integer timeout;
        private Integer user_id;

        public Integer getOrder_sub_id() {
            return order_sub_id;
        }

        public void setOrder_sub_id(Integer order_sub_id) {
            this.order_sub_id = order_sub_id;
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

        public Integer getTimeout() {
            return timeout;
        }

        public void setTimeout(Integer timeout) {
            this.timeout = timeout;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

}
