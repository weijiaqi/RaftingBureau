package com.drifting.bureau.mvp.model.entity;

public class OpenBoxEntity {


        private Integer id;
        private String name;
        private Double pro;
        private Integer stock;
        private Integer is_default;
        private Integer is_fictitious;
        private String fictitious_const;
        private String image;
        private Integer safe_box_open_record_id;
        private  String small_image;

    public String getSmall_image() {
        return small_image;
    }

    public void setSmall_image(String small_image) {
        this.small_image = small_image;
    }

    public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getPro() {
            return pro;
        }

        public void setPro(Double pro) {
            this.pro = pro;
        }

        public Integer getStock() {
            return stock;
        }

        public void setStock(Integer stock) {
            this.stock = stock;
        }

        public Integer getIs_default() {
            return is_default;
        }

        public void setIs_default(Integer is_default) {
            this.is_default = is_default;
        }

        public Integer getIs_fictitious() {
            return is_fictitious;
        }

        public void setIs_fictitious(Integer is_fictitious) {
            this.is_fictitious = is_fictitious;
        }

        public String getFictitious_const() {
            return fictitious_const;
        }

        public void setFictitious_const(String fictitious_const) {
            this.fictitious_const = fictitious_const;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getSafe_box_open_record_id() {
            return safe_box_open_record_id;
        }

        public void setSafe_box_open_record_id(Integer safe_box_open_record_id) {
            this.safe_box_open_record_id = safe_box_open_record_id;
        }

}
