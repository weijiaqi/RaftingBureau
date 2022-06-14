package com.drifting.bureau.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionEntity {


        private Integer question_id;
        private Integer q_type;
        private String question;
        private Integer postion;


        @SerializedName("A")
        private String a;
        @SerializedName("B")
        private String b;

    public Integer getPostion() {
        return postion;
    }

    public void setPostion(Integer postion) {
        this.postion = postion;
    }

    public Integer getQuestion_id() {
            return question_id;
        }

        public void setQuestion_id(Integer question_id) {
            this.question_id = question_id;
        }

        public Integer getQ_type() {
            return q_type;
        }

        public void setQ_type(Integer q_type) {
            this.q_type = q_type;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

}
