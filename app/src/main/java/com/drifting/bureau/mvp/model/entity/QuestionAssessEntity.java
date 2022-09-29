package com.drifting.bureau.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class QuestionAssessEntity   implements Serializable {

        @SerializedName("E")
        private Integer e;
        @SerializedName("F")
        private Integer f;
        @SerializedName("I")
        private Integer i;
        @SerializedName("J")
        private Integer j;
        @SerializedName("N")
        private Integer n;
        @SerializedName("P")
        private Integer p;
        @SerializedName("S")
        private Integer s;
        @SerializedName("T")
        private Integer t;
        private PlanetBean planet;
        private String user_name;

        public Integer getE() {
            return e;
        }

        public void setE(Integer e) {
            this.e = e;
        }

        public Integer getF() {
            return f;
        }

        public void setF(Integer f) {
            this.f = f;
        }

        public Integer getI() {
            return i;
        }

        public void setI(Integer i) {
            this.i = i;
        }

        public Integer getJ() {
            return j;
        }

        public void setJ(Integer j) {
            this.j = j;
        }

        public Integer getN() {
            return n;
        }

        public void setN(Integer n) {
            this.n = n;
        }

        public Integer getP() {
            return p;
        }

        public void setP(Integer p) {
            this.p = p;
        }

        public Integer getS() {
            return s;
        }

        public void setS(Integer s) {
            this.s = s;
        }

        public Integer getT() {
            return t;
        }

        public void setT(Integer t) {
            this.t = t;
        }

        public PlanetBean getPlanet() {
            return planet;
        }

        public void setPlanet(PlanetBean planet) {
            this.planet = planet;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public static class PlanetBean {
            private Integer pl_id;
            private String name;
            private Integer sort;
            private String image_url;
            private String icon_url;
            private String icon2_url;
            private String desc;
            private String intro_short;
            private String intro_short2;
            private String attr;
            private Integer people;
            private Integer status;
            private String ar_url;
            private String ar_url_ios;
            private String key_words;
            private String agree_with;
            private String agree_attr;
            private String conflict_with;
            private String conflict_attr;
            private String career;
            private String disposition;
            private String career_image;
            private  Integer agree_id;
            private  Integer conflict_id;


            public String getCareer_image() {
                return career_image;
            }

            public void setCareer_image(String career_image) {
                this.career_image = career_image;
            }

            public Integer getAgree_id() {
                return agree_id;
            }

            public void setAgree_id(Integer agree_id) {
                this.agree_id = agree_id;
            }

            public Integer getConflict_id() {
                return conflict_id;
            }

            public void setConflict_id(Integer conflict_id) {
                this.conflict_id = conflict_id;
            }

            public Integer getPl_id() {
                return pl_id;
            }

            public void setPl_id(Integer pl_id) {
                this.pl_id = pl_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Integer getSort() {
                return sort;
            }

            public void setSort(Integer sort) {
                this.sort = sort;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }

            public String getIcon_url() {
                return icon_url;
            }

            public void setIcon_url(String icon_url) {
                this.icon_url = icon_url;
            }

            public String getIcon2_url() {
                return icon2_url;
            }

            public void setIcon2_url(String icon2_url) {
                this.icon2_url = icon2_url;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getIntro_short() {
                return intro_short;
            }

            public void setIntro_short(String intro_short) {
                this.intro_short = intro_short;
            }

            public String getIntro_short2() {
                return intro_short2;
            }

            public void setIntro_short2(String intro_short2) {
                this.intro_short2 = intro_short2;
            }

            public String getAttr() {
                return attr;
            }

            public void setAttr(String attr) {
                this.attr = attr;
            }

            public Integer getPeople() {
                return people;
            }

            public void setPeople(Integer people) {
                this.people = people;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public String getAr_url() {
                return ar_url;
            }

            public void setAr_url(String ar_url) {
                this.ar_url = ar_url;
            }

            public String getAr_url_ios() {
                return ar_url_ios;
            }

            public void setAr_url_ios(String ar_url_ios) {
                this.ar_url_ios = ar_url_ios;
            }

            public String getKey_words() {
                return key_words;
            }

            public void setKey_words(String key_words) {
                this.key_words = key_words;
            }

            public String getAgree_with() {
                return agree_with;
            }

            public void setAgree_with(String agree_with) {
                this.agree_with = agree_with;
            }

            public String getAgree_attr() {
                return agree_attr;
            }

            public void setAgree_attr(String agree_attr) {
                this.agree_attr = agree_attr;
            }

            public String getConflict_with() {
                return conflict_with;
            }

            public void setConflict_with(String conflict_with) {
                this.conflict_with = conflict_with;
            }

            public String getConflict_attr() {
                return conflict_attr;
            }

            public void setConflict_attr(String conflict_attr) {
                this.conflict_attr = conflict_attr;
            }

            public String getCareer() {
                return career;
            }

            public void setCareer(String career) {
                this.career = career;
            }

            public String getDisposition() {
                return disposition;
            }

            public void setDisposition(String disposition) {
                this.disposition = disposition;
            }
        }

}
