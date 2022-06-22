package com.drifting.bureau.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

public class MessageContentEntity {

    private MessageBean message;
    private MessagePathBean message_path;
    private AttendInfoBean attend_info;
    private SummaryBean summary;
    private Integer unlock;

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public MessagePathBean getMessage_path() {
        return message_path;
    }

    public void setMessage_path(MessagePathBean message_path) {
        this.message_path = message_path;
    }

    public SummaryBean getSummary() {
        return summary;
    }

    public void setSummary(SummaryBean summary) {
        this.summary = summary;
    }

    public Integer getUnlock() {
        return unlock;
    }

    public void setUnlock(Integer unlock) {
        this.unlock = unlock;
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
        private Integer total_attend;
        private Integer created_at_int;

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

        public Integer getTotal_attend() {
            return total_attend;
        }

        public void setTotal_attend(Integer total_attend) {
            this.total_attend = total_attend;
        }

        public Integer getCreated_at_int() {
            return created_at_int;
        }

        public void setCreated_at_int(Integer created_at_int) {
            this.created_at_int = created_at_int;
        }
    }

    public static class MessagePathBean {
        @SerializedName("Id")
        private Integer id;
        private Integer message_id;
        private Integer path_time;
        private Integer first_n;
        private Integer created_at_int;
        private Integer footprint;
        private Integer drift_end;
        private Integer drift_rest;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getMessage_id() {
            return message_id;
        }

        public void setMessage_id(Integer message_id) {
            this.message_id = message_id;
        }

        public Integer getPath_time() {
            return path_time;
        }

        public void setPath_time(Integer path_time) {
            this.path_time = path_time;
        }

        public Integer getFirst_n() {
            return first_n;
        }

        public void setFirst_n(Integer first_n) {
            this.first_n = first_n;
        }

        public Integer getCreated_at_int() {
            return created_at_int;
        }

        public void setCreated_at_int(Integer created_at_int) {
            this.created_at_int = created_at_int;
        }

        public Integer getFootprint() {
            return footprint;
        }

        public void setFootprint(Integer footprint) {
            this.footprint = footprint;
        }

        public Integer getDrift_end() {
            return drift_end;
        }

        public void setDrift_end(Integer drift_end) {
            this.drift_end = drift_end;
        }

        public Integer getDrift_rest() {
            return drift_rest;
        }

        public void setDrift_rest(Integer drift_rest) {
            this.drift_rest = drift_rest;
        }
    }

    public AttendInfoBean getAttend_info() {
        return attend_info;
    }

    public void setAttend_info(AttendInfoBean attend_info) {
        this.attend_info = attend_info;
    }

    public static class AttendInfoBean {
        private Integer attend;
        private Integer footprint_end;
        private Integer footprint_rest;

        public Integer getAttend() {
            return attend;
        }

        public void setAttend(Integer attend) {
            this.attend = attend;
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


    public static class SummaryBean {
        private Integer player;

        public Integer getPlayer() {
            return player;
        }

        public void setPlayer(Integer player) {
            this.player = player;
        }
    }

}
