package com.drifting.bureau.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

public class QuestionAssessEntity {


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
            private String image_url;
            private String icon_url;
            private String desc;
            private String attr;
            private Integer people;

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

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
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

    }
}
