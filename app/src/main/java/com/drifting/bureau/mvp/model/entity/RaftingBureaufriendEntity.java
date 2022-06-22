package com.drifting.bureau.mvp.model.entity;

import java.util.List;

public class RaftingBureaufriendEntity {


        private Integer apply;
        private List<FriendsBean> friends;

        public Integer getApply() {
            return apply;
        }

        public void setApply(Integer apply) {
            this.apply = apply;
        }

        public List<FriendsBean> getFriends() {
            return friends;
        }

        public void setFriends(List<FriendsBean> friends) {
            this.friends = friends;
        }

        public static class FriendsBean {
            private Integer f_id;
            private Integer user_id;
            private Integer friend_user_id;
            private String friend_user_name;
            private String friend_level_name;
            private String profile_photo;
            private String planet_name;
            private Integer created_at_int;

            public Integer getF_id() {
                return f_id;
            }

            public void setF_id(Integer f_id) {
                this.f_id = f_id;
            }

            public Integer getUser_id() {
                return user_id;
            }

            public void setUser_id(Integer user_id) {
                this.user_id = user_id;
            }

            public Integer getFriend_user_id() {
                return friend_user_id;
            }

            public void setFriend_user_id(Integer friend_user_id) {
                this.friend_user_id = friend_user_id;
            }

            public String getFriend_user_name() {
                return friend_user_name;
            }

            public void setFriend_user_name(String friend_user_name) {
                this.friend_user_name = friend_user_name;
            }

            public String getFriend_level_name() {
                return friend_level_name;
            }

            public void setFriend_level_name(String friend_level_name) {
                this.friend_level_name = friend_level_name;
            }

            public String getProfile_photo() {
                return profile_photo;
            }

            public void setProfile_photo(String profile_photo) {
                this.profile_photo = profile_photo;
            }

            public String getPlanet_name() {
                return planet_name;
            }

            public void setPlanet_name(String planet_name) {
                this.planet_name = planet_name;
            }

            public Integer getCreated_at_int() {
                return created_at_int;
            }

            public void setCreated_at_int(Integer created_at_int) {
                this.created_at_int = created_at_int;
            }
        }

}
