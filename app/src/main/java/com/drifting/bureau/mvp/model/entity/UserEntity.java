package com.drifting.bureau.mvp.model.entity;

public class UserEntity {

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

    public static class PlanetBean {
        private Integer Id;
        private String name;
        private Integer temp_id;
        private Long user_id;
        private Integer attr_energy;
        private Integer attr_temperature;
        private Integer attr_flame;
        private Integer attr_wind;
        private Integer attr_sky;
        private Integer attr_ocean;
        private Integer attr_plant;
        private Integer attr_soil;
        private Integer explore;
        private String created_at;
        private String updated_at;

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

        public Long getUser_id() {
            return user_id;
        }

        public void setUser_id(Long user_id) {
            this.user_id = user_id;
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

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }

    public static class UserBean {
        private Long id;
        private String name;
        private String mobile;
        private String created_at;
        private String updated_at;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}

