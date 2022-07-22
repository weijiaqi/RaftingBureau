package com.drifting.bureau.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoreDetailsEntity {


    private MessageBean message;
    private RelevanceBean relevance;
    private List<MessagePathBean> message_path;

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public RelevanceBean getRelevance() {
        return relevance;
    }

    public void setRelevance(RelevanceBean relevance) {
        this.relevance = relevance;
    }

    public List<MessagePathBean> getMessage_path() {
        return message_path;
    }

    public void setMessage_path(List<MessagePathBean> message_path) {
        this.message_path = message_path;
    }

    public static class MessageBean {
        @SerializedName("Id")
        private Integer id;
        private Integer user_id;
        private String user_name;
        private String content;
        private String audio;
        private String vedio;
        private String image;
        private String album;
        private Integer type_id;
        private Integer explore_id;
        private Integer is_drifting;
        private Integer is_throw;
        private Integer total_attend;
        private Integer path_time;
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

        public String getAudio() {
            return audio;
        }

        public void setAudio(String audio) {
            this.audio = audio;
        }

        public String getVedio() {
            return vedio;
        }

        public void setVedio(String vedio) {
            this.vedio = vedio;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getAlbum() {
            return album;
        }

        public void setAlbum(String album) {
            this.album = album;
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

        public Integer getTotal_attend() {
            return total_attend;
        }

        public void setTotal_attend(Integer total_attend) {
            this.total_attend = total_attend;
        }

        public Integer getPath_time() {
            return path_time;
        }

        public void setPath_time(Integer path_time) {
            this.path_time = path_time;
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

    public static class RelevanceBean {
        private Integer attend;
        private String user_name;
        private String last_attend;
        private Integer is_comment;
        private Integer footprint_end;
        private Integer footprint_rest;

        public Integer getAttend() {
            return attend;
        }

        public void setAttend(Integer attend) {
            this.attend = attend;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getLast_attend() {
            return last_attend;
        }

        public void setLast_attend(String last_attend) {
            this.last_attend = last_attend;
        }

        public Integer getIs_comment() {
            return is_comment;
        }

        public void setIs_comment(Integer is_comment) {
            this.is_comment = is_comment;
        }

        public Integer getFootprint_end() {
            return footprint_end;
        }

        public void setFootprint_end(Integer footprint_end) {
            this.footprint_end = footprint_end;
        }

        public Integer getFootprint_rest() {
            return footprint_rest;
        }

        public void setFootprint_rest(Integer footprint_rest) {
            this.footprint_rest = footprint_rest;
        }
    }

    public static class MessagePathBean {
        private Integer user_id;
        private Integer comment_id;

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public Integer getComment_id() {
            return comment_id;
        }

        public void setComment_id(Integer comment_id) {
            this.comment_id = comment_id;
        }
    }

}