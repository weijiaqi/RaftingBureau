package com.drifting.bureau.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BarrageEntity {


        private List<CommentsBean> comments;
        private MessageBean message;
        private Integer my_attend;
        private Integer total_attend;

        public List<CommentsBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentsBean> comments) {
            this.comments = comments;
        }

        public MessageBean getMessage() {
            return message;
        }

        public void setMessage(MessageBean message) {
            this.message = message;
        }

        public Integer getMy_attend() {
            return my_attend;
        }

        public void setMy_attend(Integer my_attend) {
            this.my_attend = my_attend;
        }

        public Integer getTotal_attend() {
            return total_attend;
        }

        public void setTotal_attend(Integer total_attend) {
            this.total_attend = total_attend;
        }

        public static class MessageBean {
            @SerializedName("Id")
            private Integer id;
            private Integer user_id;
            private String user_name;
            private String content;
            private Integer type_id;
            private Integer explore_id;
            private Integer is_drifting;
            private Integer is_throw;
            private String image;
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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public Integer getType_id() {
                return type_id;
            }

            public void setType_id(Integer type_id) {
                this.type_id = type_id;
            }

            public Integer getExplore_id() {
                return explore_id;
            }

            public void setExplore_id(Integer explore_id) {
                this.explore_id = explore_id;
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

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
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

        public static class CommentsBean {
            private Integer user_id;
            private String user_name;
            private String content;
            private Integer type_id;
            private String image;

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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public Integer getType_id() {
                return type_id;
            }

            public void setType_id(Integer type_id) {
                this.type_id = type_id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }

}
