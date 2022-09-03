package com.drifting.bureau.mvp.model.entity;

import java.util.List;

public class DeliveryDetailsEntity {


        private Integer count;
        private Integer limit;
        private MessagePathBean message;
        private List<MessagePathBean> message_path;
        private Integer page;

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public MessagePathBean getMessage() {
            return message;
        }

        public void setMessage(MessagePathBean message) {
            this.message = message;
        }

        public List<MessagePathBean> getMessage_path() {
            return message_path;
        }

        public void setMessage_path(List<MessagePathBean> message_path) {
            this.message_path = message_path;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }



        public static class MessagePathBean {
            private String content;
            private String image;
            private Integer message_id;
            private Integer path_time;

            private String user_name;
            private String user_id;
            private String planet_level_name;
            private Integer friend_apply;
            private Integer type;
            private String level_name;
            private String mascot;
           private String audio;
           private String vedio;
           private String album;

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

            public String getAlbum() {
                return album;
            }

            public void setAlbum(String album) {
                this.album = album;
            }

            public String getMascot() {
                return mascot;
            }

            public void setMascot(String mascot) {
                this.mascot = mascot;
            }

            public String getLevel_name() {
                return level_name;
            }

            public void setLevel_name(String level_name) {
                this.level_name = level_name;
            }

            public Integer getType() {
                return type;
            }

            public void setType(Integer type) {
                this.type = type;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
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


            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getPlanet_level_name() {
                return planet_level_name;
            }

            public void setPlanet_level_name(String planet_level_name) {
                this.planet_level_name = planet_level_name;
            }

            public Integer getFriend_apply() {
                return friend_apply;
            }

            public void setFriend_apply(Integer friend_apply) {
                this.friend_apply = friend_apply;
            }
        }

}
