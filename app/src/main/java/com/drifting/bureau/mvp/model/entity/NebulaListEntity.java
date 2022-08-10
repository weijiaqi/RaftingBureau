package com.drifting.bureau.mvp.model.entity;

import java.util.List;

public class NebulaListEntity {

        private List<ListBean> list;
        private Integer unlock_total;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public Integer getUnlock_total() {
            return unlock_total;
        }

        public void setUnlock_total(Integer unlock_total) {
            this.unlock_total = unlock_total;
        }

        public static class ListBean {
            private Integer nebula_id;
            private String name_city;
            private String code_city;
            private String nebula;
            private String nebula_code;
            private Integer nebula_x;
            private Integer nebula_y;
            private Integer level;
            private Integer meta_power;
            private String intro;
            private Integer unlock;
            private Integer unlock_time_int;

            public String getNebula_code() {
                return nebula_code;
            }

            public void setNebula_code(String nebula_code) {
                this.nebula_code = nebula_code;
            }

            public Integer getNebula_id() {
                return nebula_id;
            }

            public void setNebula_id(Integer nebula_id) {
                this.nebula_id = nebula_id;
            }

            public String getName_city() {
                return name_city;
            }

            public void setName_city(String name_city) {
                this.name_city = name_city;
            }

            public String getCode_city() {
                return code_city;
            }

            public void setCode_city(String code_city) {
                this.code_city = code_city;
            }

            public String getNebula() {
                return nebula;
            }

            public void setNebula(String nebula) {
                this.nebula = nebula;
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

            public Integer getLevel() {
                return level;
            }

            public void setLevel(Integer level) {
                this.level = level;
            }

            public Integer getMeta_power() {
                return meta_power;
            }

            public void setMeta_power(Integer meta_power) {
                this.meta_power = meta_power;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public Integer getUnlock() {
                return unlock;
            }

            public void setUnlock(Integer unlock) {
                this.unlock = unlock;
            }

            public Integer getUnlock_time_int() {
                return unlock_time_int;
            }

            public void setUnlock_time_int(Integer unlock_time_int) {
                this.unlock_time_int = unlock_time_int;
            }
        }

}
