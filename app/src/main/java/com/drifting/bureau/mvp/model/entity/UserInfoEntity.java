package com.drifting.bureau.mvp.model.entity;

import java.io.Serializable;

public class UserInfoEntity implements Serializable {

        private PlanetBean planet;
        private UserBean user;

        public PlanetBean getPlanet() {
            return planet;
        }

        public void setPlanet(PlanetBean planet) {
            this.planet = planet;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class PlanetBean  implements Serializable  {
            private Integer Id;
            private String name;
            private Integer temp_id;
            private Integer user_id;
            private Integer level;
            private Integer attr_energy;
            private Integer attr_temperature;
            private Integer attr_flame;
            private Integer attr_wind;
            private Integer attr_sky;
            private Integer attr_ocean;
            private Integer attr_plant;
            private Integer attr_soil;
            private Integer explore;
            private Integer schedule;
            private Integer created_at_int;
            private Integer updated_at_int;
            private String ar_url;

            public String getAr_url() {
                return ar_url;
            }

            public void setAr_url(String ar_url) {
                this.ar_url = ar_url;
            }

            public Integer getId() {
                return Id;
            }

            public void setId(Integer id) {
                Id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Integer getTemp_id() {
                return temp_id;
            }

            public void setTemp_id(Integer temp_id) {
                this.temp_id = temp_id;
            }

            public Integer getUser_id() {
                return user_id;
            }

            public void setUser_id(Integer user_id) {
                this.user_id = user_id;
            }

            public Integer getLevel() {
                return level;
            }

            public void setLevel(Integer level) {
                this.level = level;
            }

            public Integer getAttr_energy() {
                return attr_energy;
            }

            public void setAttr_energy(Integer attr_energy) {
                this.attr_energy = attr_energy;
            }

            public Integer getAttr_temperature() {
                return attr_temperature;
            }

            public void setAttr_temperature(Integer attr_temperature) {
                this.attr_temperature = attr_temperature;
            }

            public Integer getAttr_flame() {
                return attr_flame;
            }

            public void setAttr_flame(Integer attr_flame) {
                this.attr_flame = attr_flame;
            }

            public Integer getAttr_wind() {
                return attr_wind;
            }

            public void setAttr_wind(Integer attr_wind) {
                this.attr_wind = attr_wind;
            }

            public Integer getAttr_sky() {
                return attr_sky;
            }

            public void setAttr_sky(Integer attr_sky) {
                this.attr_sky = attr_sky;
            }

            public Integer getAttr_ocean() {
                return attr_ocean;
            }

            public void setAttr_ocean(Integer attr_ocean) {
                this.attr_ocean = attr_ocean;
            }

            public Integer getAttr_plant() {
                return attr_plant;
            }

            public void setAttr_plant(Integer attr_plant) {
                this.attr_plant = attr_plant;
            }

            public Integer getAttr_soil() {
                return attr_soil;
            }

            public void setAttr_soil(Integer attr_soil) {
                this.attr_soil = attr_soil;
            }

            public Integer getExplore() {
                return explore;
            }

            public void setExplore(Integer explore) {
                this.explore = explore;
            }

            public Integer getSchedule() {
                return schedule;
            }

            public void setSchedule(Integer schedule) {
                this.schedule = schedule;
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

        public static class UserBean   implements Serializable{
            private Integer Id;
            private String name;
            private String mobile;
            private Integer level;
            private String level_name;
            private Integer explore_num;
            private Integer created_at_int;
            private Integer updated_at_int;
            private String share_code;
            private String mascot;

            public String getMascot() {
                return mascot;
            }

            public void setMascot(String mascot) {
                this.mascot = mascot;
            }

            public String getShare_code() {
                return share_code;
            }

            public void setShare_code(String share_code) {
                this.share_code = share_code;
            }

            public Integer getId() {
                return Id;
            }

            public void setId(Integer id) {
                Id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public Integer getLevel() {
                return level;
            }

            public void setLevel(Integer level) {
                this.level = level;
            }

            public String getLevel_name() {
                return level_name;
            }

            public void setLevel_name(String level_name) {
                this.level_name = level_name;
            }

            public Integer getExplore_num() {
                return explore_num;
            }

            public void setExplore_num(Integer explore_num) {
                this.explore_num = explore_num;
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
