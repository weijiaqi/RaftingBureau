package com.drifting.bureau.mvp.model.entity;

import java.util.List;

public class MySpaceStationEntity {

        private Integer score;
        private Integer space_level;
        private String space_level_name;
        private Integer space_top_level;
        private Integer top_score;
        private Integer user_id;
        private List<AllRightsBean> all_rights;
        private List<OwnRightsBean> own_rights;

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public Integer getSpace_level() {
            return space_level;
        }

        public void setSpace_level(Integer space_level) {
            this.space_level = space_level;
        }

        public String getSpace_level_name() {
            return space_level_name;
        }

        public void setSpace_level_name(String space_level_name) {
            this.space_level_name = space_level_name;
        }

        public Integer getSpace_top_level() {
            return space_top_level;
        }

        public void setSpace_top_level(Integer space_top_level) {
            this.space_top_level = space_top_level;
        }

        public Integer getTop_score() {
            return top_score;
        }

        public void setTop_score(Integer top_score) {
            this.top_score = top_score;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public List<AllRightsBean> getAll_rights() {
            return all_rights;
        }

        public void setAll_rights(List<AllRightsBean> all_rights) {
            this.all_rights = all_rights;
        }

        public List<OwnRightsBean> getOwn_rights() {
            return own_rights;
        }

        public void setOwn_rights(List<OwnRightsBean> own_rights) {
            this.own_rights = own_rights;
        }

        public static class AllRightsBean {
            private Integer rights_id;
            private String name;
            private String rights;
            private String image_url;

            public Integer getRights_id() {
                return rights_id;
            }

            public void setRights_id(Integer rights_id) {
                this.rights_id = rights_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRights() {
                return rights;
            }

            public void setRights(String rights) {
                this.rights = rights;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }
        }

        public static class OwnRightsBean {
            private Integer rights_id;
            private String name;
            private String rights;
            private String image_url;

            public Integer getRights_id() {
                return rights_id;
            }

            public void setRights_id(Integer rights_id) {
                this.rights_id = rights_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRights() {
                return rights;
            }

            public void setRights(String rights) {
                this.rights = rights;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }

    }
}
