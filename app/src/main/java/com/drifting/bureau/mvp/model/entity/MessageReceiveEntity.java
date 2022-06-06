package com.drifting.bureau.mvp.model.entity;

public class MessageReceiveEntity {


        private Integer id;
        private Integer user_id;
        private String user_name;
        private Integer type_id;
        private Integer is_drifting;
        private Integer is_throw;
        private Integer created_at_int;
        private Integer updated_at_int;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public Integer getType_id() {
            return type_id;
        }

        public void setType_id(Integer type_id) {
            this.type_id = type_id;
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
