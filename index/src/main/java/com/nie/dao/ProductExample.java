package com.nie.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
//import java.sql.Date;
import java.util.List;

public class ProductExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProductExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andProductCodeIsNull() {
            addCriterion("product_code is null");
            return (Criteria) this;
        }

        public Criteria andProductCodeIsNotNull() {
            addCriterion("product_code is not null");
            return (Criteria) this;
        }

        public Criteria andProductCodeEqualTo(String value) {
            addCriterion("product_code =", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotEqualTo(String value) {
            addCriterion("product_code <>", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeGreaterThan(String value) {
            addCriterion("product_code >", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeGreaterThanOrEqualTo(String value) {
            addCriterion("product_code >=", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLessThan(String value) {
            addCriterion("product_code <", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLessThanOrEqualTo(String value) {
            addCriterion("product_code <=", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLike(String value) {
            addCriterion("product_code like", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotLike(String value) {
            addCriterion("product_code not like", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeIn(List<String> values) {
            addCriterion("product_code in", values, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotIn(List<String> values) {
            addCriterion("product_code not in", values, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeBetween(String value1, String value2) {
            addCriterion("product_code between", value1, value2, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotBetween(String value1, String value2) {
            addCriterion("product_code not between", value1, value2, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCnameIsNull() {
            addCriterion("product_cname is null");
            return (Criteria) this;
        }

        public Criteria andProductCnameIsNotNull() {
            addCriterion("product_cname is not null");
            return (Criteria) this;
        }

        public Criteria andProductCnameEqualTo(String value) {
            addCriterion("product_cname =", value, "productCname");
            return (Criteria) this;
        }

        public Criteria andProductCnameNotEqualTo(String value) {
            addCriterion("product_cname <>", value, "productCname");
            return (Criteria) this;
        }

        public Criteria andProductCnameGreaterThan(String value) {
            addCriterion("product_cname >", value, "productCname");
            return (Criteria) this;
        }

        public Criteria andProductCnameGreaterThanOrEqualTo(String value) {
            addCriterion("product_cname >=", value, "productCname");
            return (Criteria) this;
        }

        public Criteria andProductCnameLessThan(String value) {
            addCriterion("product_cname <", value, "productCname");
            return (Criteria) this;
        }

        public Criteria andProductCnameLessThanOrEqualTo(String value) {
            addCriterion("product_cname <=", value, "productCname");
            return (Criteria) this;
        }

        public Criteria andProductCnameLike(String value) {
            addCriterion("product_cname like", value, "productCname");
            return (Criteria) this;
        }

        public Criteria andProductCnameNotLike(String value) {
            addCriterion("product_cname not like", value, "productCname");
            return (Criteria) this;
        }

        public Criteria andProductCnameIn(List<String> values) {
            addCriterion("product_cname in", values, "productCname");
            return (Criteria) this;
        }

        public Criteria andProductCnameNotIn(List<String> values) {
            addCriterion("product_cname not in", values, "productCname");
            return (Criteria) this;
        }

        public Criteria andProductCnameBetween(String value1, String value2) {
            addCriterion("product_cname between", value1, value2, "productCname");
            return (Criteria) this;
        }

        public Criteria andProductCnameNotBetween(String value1, String value2) {
            addCriterion("product_cname not between", value1, value2, "productCname");
            return (Criteria) this;
        }

        public Criteria andProductEnameIsNull() {
            addCriterion("product_ename is null");
            return (Criteria) this;
        }

        public Criteria andProductEnameIsNotNull() {
            addCriterion("product_ename is not null");
            return (Criteria) this;
        }

        public Criteria andProductEnameEqualTo(String value) {
            addCriterion("product_ename =", value, "productEname");
            return (Criteria) this;
        }

        public Criteria andProductEnameNotEqualTo(String value) {
            addCriterion("product_ename <>", value, "productEname");
            return (Criteria) this;
        }

        public Criteria andProductEnameGreaterThan(String value) {
            addCriterion("product_ename >", value, "productEname");
            return (Criteria) this;
        }

        public Criteria andProductEnameGreaterThanOrEqualTo(String value) {
            addCriterion("product_ename >=", value, "productEname");
            return (Criteria) this;
        }

        public Criteria andProductEnameLessThan(String value) {
            addCriterion("product_ename <", value, "productEname");
            return (Criteria) this;
        }

        public Criteria andProductEnameLessThanOrEqualTo(String value) {
            addCriterion("product_ename <=", value, "productEname");
            return (Criteria) this;
        }

        public Criteria andProductEnameLike(String value) {
            addCriterion("product_ename like", value, "productEname");
            return (Criteria) this;
        }

        public Criteria andProductEnameNotLike(String value) {
            addCriterion("product_ename not like", value, "productEname");
            return (Criteria) this;
        }

        public Criteria andProductEnameIn(List<String> values) {
            addCriterion("product_ename in", values, "productEname");
            return (Criteria) this;
        }

        public Criteria andProductEnameNotIn(List<String> values) {
            addCriterion("product_ename not in", values, "productEname");
            return (Criteria) this;
        }

        public Criteria andProductEnameBetween(String value1, String value2) {
            addCriterion("product_ename between", value1, value2, "productEname");
            return (Criteria) this;
        }

        public Criteria andProductEnameNotBetween(String value1, String value2) {
            addCriterion("product_ename not between", value1, value2, "productEname");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIsNull() {
            addCriterion("category_id is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIsNotNull() {
            addCriterion("category_id is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryIdEqualTo(String value) {
            addCriterion("category_id =", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotEqualTo(String value) {
            addCriterion("category_id <>", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdGreaterThan(String value) {
            addCriterion("category_id >", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdGreaterThanOrEqualTo(String value) {
            addCriterion("category_id >=", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdLessThan(String value) {
            addCriterion("category_id <", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdLessThanOrEqualTo(String value) {
            addCriterion("category_id <=", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdLike(String value) {
            addCriterion("category_id like", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotLike(String value) {
            addCriterion("category_id not like", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIn(List<String> values) {
            addCriterion("category_id in", values, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotIn(List<String> values) {
            addCriterion("category_id not in", values, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdBetween(String value1, String value2) {
            addCriterion("category_id between", value1, value2, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotBetween(String value1, String value2) {
            addCriterion("category_id not between", value1, value2, "categoryId");
            return (Criteria) this;
        }

        public Criteria andDefaultPictureUrlIsNull() {
            addCriterion("default_picture_url is null");
            return (Criteria) this;
        }

        public Criteria andDefaultPictureUrlIsNotNull() {
            addCriterion("default_picture_url is not null");
            return (Criteria) this;
        }

        public Criteria andDefaultPictureUrlEqualTo(String value) {
            addCriterion("default_picture_url =", value, "defaultPictureUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultPictureUrlNotEqualTo(String value) {
            addCriterion("default_picture_url <>", value, "defaultPictureUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultPictureUrlGreaterThan(String value) {
            addCriterion("default_picture_url >", value, "defaultPictureUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultPictureUrlGreaterThanOrEqualTo(String value) {
            addCriterion("default_picture_url >=", value, "defaultPictureUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultPictureUrlLessThan(String value) {
            addCriterion("default_picture_url <", value, "defaultPictureUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultPictureUrlLessThanOrEqualTo(String value) {
            addCriterion("default_picture_url <=", value, "defaultPictureUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultPictureUrlLike(String value) {
            addCriterion("default_picture_url like", value, "defaultPictureUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultPictureUrlNotLike(String value) {
            addCriterion("default_picture_url not like", value, "defaultPictureUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultPictureUrlIn(List<String> values) {
            addCriterion("default_picture_url in", values, "defaultPictureUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultPictureUrlNotIn(List<String> values) {
            addCriterion("default_picture_url not in", values, "defaultPictureUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultPictureUrlBetween(String value1, String value2) {
            addCriterion("default_picture_url between", value1, value2, "defaultPictureUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultPictureUrlNotBetween(String value1, String value2) {
            addCriterion("default_picture_url not between", value1, value2, "defaultPictureUrl");
            return (Criteria) this;
        }

        public Criteria andNameSubtitleIsNull() {
            addCriterion("name_subtitle is null");
            return (Criteria) this;
        }

        public Criteria andNameSubtitleIsNotNull() {
            addCriterion("name_subtitle is not null");
            return (Criteria) this;
        }

        public Criteria andNameSubtitleEqualTo(String value) {
            addCriterion("name_subtitle =", value, "nameSubtitle");
            return (Criteria) this;
        }

        public Criteria andNameSubtitleNotEqualTo(String value) {
            addCriterion("name_subtitle <>", value, "nameSubtitle");
            return (Criteria) this;
        }

        public Criteria andNameSubtitleGreaterThan(String value) {
            addCriterion("name_subtitle >", value, "nameSubtitle");
            return (Criteria) this;
        }

        public Criteria andNameSubtitleGreaterThanOrEqualTo(String value) {
            addCriterion("name_subtitle >=", value, "nameSubtitle");
            return (Criteria) this;
        }

        public Criteria andNameSubtitleLessThan(String value) {
            addCriterion("name_subtitle <", value, "nameSubtitle");
            return (Criteria) this;
        }

        public Criteria andNameSubtitleLessThanOrEqualTo(String value) {
            addCriterion("name_subtitle <=", value, "nameSubtitle");
            return (Criteria) this;
        }

        public Criteria andNameSubtitleLike(String value) {
            addCriterion("name_subtitle like", value, "nameSubtitle");
            return (Criteria) this;
        }

        public Criteria andNameSubtitleNotLike(String value) {
            addCriterion("name_subtitle not like", value, "nameSubtitle");
            return (Criteria) this;
        }

        public Criteria andNameSubtitleIn(List<String> values) {
            addCriterion("name_subtitle in", values, "nameSubtitle");
            return (Criteria) this;
        }

        public Criteria andNameSubtitleNotIn(List<String> values) {
            addCriterion("name_subtitle not in", values, "nameSubtitle");
            return (Criteria) this;
        }

        public Criteria andNameSubtitleBetween(String value1, String value2) {
            addCriterion("name_subtitle between", value1, value2, "nameSubtitle");
            return (Criteria) this;
        }

        public Criteria andNameSubtitleNotBetween(String value1, String value2) {
            addCriterion("name_subtitle not between", value1, value2, "nameSubtitle");
            return (Criteria) this;
        }

        public Criteria andProductDescriptionIsNull() {
            addCriterion("product_description is null");
            return (Criteria) this;
        }

        public Criteria andProductDescriptionIsNotNull() {
            addCriterion("product_description is not null");
            return (Criteria) this;
        }

        public Criteria andProductDescriptionEqualTo(String value) {
            addCriterion("product_description =", value, "productDescription");
            return (Criteria) this;
        }

        public Criteria andProductDescriptionNotEqualTo(String value) {
            addCriterion("product_description <>", value, "productDescription");
            return (Criteria) this;
        }

        public Criteria andProductDescriptionGreaterThan(String value) {
            addCriterion("product_description >", value, "productDescription");
            return (Criteria) this;
        }

        public Criteria andProductDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("product_description >=", value, "productDescription");
            return (Criteria) this;
        }

        public Criteria andProductDescriptionLessThan(String value) {
            addCriterion("product_description <", value, "productDescription");
            return (Criteria) this;
        }

        public Criteria andProductDescriptionLessThanOrEqualTo(String value) {
            addCriterion("product_description <=", value, "productDescription");
            return (Criteria) this;
        }

        public Criteria andProductDescriptionLike(String value) {
            addCriterion("product_description like", value, "productDescription");
            return (Criteria) this;
        }

        public Criteria andProductDescriptionNotLike(String value) {
            addCriterion("product_description not like", value, "productDescription");
            return (Criteria) this;
        }

        public Criteria andProductDescriptionIn(List<String> values) {
            addCriterion("product_description in", values, "productDescription");
            return (Criteria) this;
        }

        public Criteria andProductDescriptionNotIn(List<String> values) {
            addCriterion("product_description not in", values, "productDescription");
            return (Criteria) this;
        }

        public Criteria andProductDescriptionBetween(String value1, String value2) {
            addCriterion("product_description between", value1, value2, "productDescription");
            return (Criteria) this;
        }

        public Criteria andProductDescriptionNotBetween(String value1, String value2) {
            addCriterion("product_description not between", value1, value2, "productDescription");
            return (Criteria) this;
        }

        public Criteria andInPriceIsNull() {
            addCriterion("in_price is null");
            return (Criteria) this;
        }

        public Criteria andInPriceIsNotNull() {
            addCriterion("in_price is not null");
            return (Criteria) this;
        }

        public Criteria andInPriceEqualTo(Double value) {
            addCriterion("in_price =", value, "inPrice");
            return (Criteria) this;
        }

        public Criteria andInPriceNotEqualTo(Double value) {
            addCriterion("in_price <>", value, "inPrice");
            return (Criteria) this;
        }

        public Criteria andInPriceGreaterThan(Double value) {
            addCriterion("in_price >", value, "inPrice");
            return (Criteria) this;
        }

        public Criteria andInPriceGreaterThanOrEqualTo(Double value) {
            addCriterion("in_price >=", value, "inPrice");
            return (Criteria) this;
        }

        public Criteria andInPriceLessThan(Double value) {
            addCriterion("in_price <", value, "inPrice");
            return (Criteria) this;
        }

        public Criteria andInPriceLessThanOrEqualTo(Double value) {
            addCriterion("in_price <=", value, "inPrice");
            return (Criteria) this;
        }

        public Criteria andInPriceIn(List<Double> values) {
            addCriterion("in_price in", values, "inPrice");
            return (Criteria) this;
        }

        public Criteria andInPriceNotIn(List<Double> values) {
            addCriterion("in_price not in", values, "inPrice");
            return (Criteria) this;
        }

        public Criteria andInPriceBetween(Double value1, Double value2) {
            addCriterion("in_price between", value1, value2, "inPrice");
            return (Criteria) this;
        }

        public Criteria andInPriceNotBetween(Double value1, Double value2) {
            addCriterion("in_price not between", value1, value2, "inPrice");
            return (Criteria) this;
        }

        public Criteria andProductBrandIdIsNull() {
            addCriterion("product_brand_id is null");
            return (Criteria) this;
        }

        public Criteria andProductBrandIdIsNotNull() {
            addCriterion("product_brand_id is not null");
            return (Criteria) this;
        }

        public Criteria andProductBrandIdEqualTo(String value) {
            addCriterion("product_brand_id =", value, "productBrandId");
            return (Criteria) this;
        }

        public Criteria andProductBrandIdNotEqualTo(String value) {
            addCriterion("product_brand_id <>", value, "productBrandId");
            return (Criteria) this;
        }

        public Criteria andProductBrandIdGreaterThan(String value) {
            addCriterion("product_brand_id >", value, "productBrandId");
            return (Criteria) this;
        }

        public Criteria andProductBrandIdGreaterThanOrEqualTo(String value) {
            addCriterion("product_brand_id >=", value, "productBrandId");
            return (Criteria) this;
        }

        public Criteria andProductBrandIdLessThan(String value) {
            addCriterion("product_brand_id <", value, "productBrandId");
            return (Criteria) this;
        }

        public Criteria andProductBrandIdLessThanOrEqualTo(String value) {
            addCriterion("product_brand_id <=", value, "productBrandId");
            return (Criteria) this;
        }

        public Criteria andProductBrandIdLike(String value) {
            addCriterion("product_brand_id like", value, "productBrandId");
            return (Criteria) this;
        }

        public Criteria andProductBrandIdNotLike(String value) {
            addCriterion("product_brand_id not like", value, "productBrandId");
            return (Criteria) this;
        }

        public Criteria andProductBrandIdIn(List<String> values) {
            addCriterion("product_brand_id in", values, "productBrandId");
            return (Criteria) this;
        }

        public Criteria andProductBrandIdNotIn(List<String> values) {
            addCriterion("product_brand_id not in", values, "productBrandId");
            return (Criteria) this;
        }

        public Criteria andProductBrandIdBetween(String value1, String value2) {
            addCriterion("product_brand_id between", value1, value2, "productBrandId");
            return (Criteria) this;
        }

        public Criteria andProductBrandIdNotBetween(String value1, String value2) {
            addCriterion("product_brand_id not between", value1, value2, "productBrandId");
            return (Criteria) this;
        }

        public Criteria andColorIsNull() {
            addCriterion("color is null");
            return (Criteria) this;
        }

        public Criteria andColorIsNotNull() {
            addCriterion("color is not null");
            return (Criteria) this;
        }

        public Criteria andColorEqualTo(String value) {
            addCriterion("color =", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorNotEqualTo(String value) {
            addCriterion("color <>", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorGreaterThan(String value) {
            addCriterion("color >", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorGreaterThanOrEqualTo(String value) {
            addCriterion("color >=", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorLessThan(String value) {
            addCriterion("color <", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorLessThanOrEqualTo(String value) {
            addCriterion("color <=", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorLike(String value) {
            addCriterion("color like", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorNotLike(String value) {
            addCriterion("color not like", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorIn(List<String> values) {
            addCriterion("color in", values, "color");
            return (Criteria) this;
        }

        public Criteria andColorNotIn(List<String> values) {
            addCriterion("color not in", values, "color");
            return (Criteria) this;
        }

        public Criteria andColorBetween(String value1, String value2) {
            addCriterion("color between", value1, value2, "color");
            return (Criteria) this;
        }

        public Criteria andColorNotBetween(String value1, String value2) {
            addCriterion("color not between", value1, value2, "color");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(Long value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(Long value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(Long value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(Long value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(Long value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(Long value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<Long> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<Long> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(Long value1, Long value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(Long value1, Long value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(Long value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(Long value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(Long value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(Long value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(Long value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(Long value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<Long> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<Long> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(Long value1, Long value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(Long value1, Long value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Timestamp value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Timestamp value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Timestamp value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Timestamp value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Timestamp value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Timestamp value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Timestamp> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Timestamp> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Timestamp value1, Timestamp value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Timestamp value1, Timestamp value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Timestamp value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Timestamp value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Timestamp value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Timestamp value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Timestamp value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Timestamp value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Timestamp> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Timestamp> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Timestamp value1, Timestamp value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Timestamp value1, Timestamp value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNull() {
            addCriterion("is_delete is null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNotNull() {
            addCriterion("is_delete is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteEqualTo(Integer value) {
            addCriterion("is_delete =", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotEqualTo(Integer value) {
            addCriterion("is_delete <>", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThan(Integer value) {
            addCriterion("is_delete >", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_delete >=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThan(Integer value) {
            addCriterion("is_delete <", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThanOrEqualTo(Integer value) {
            addCriterion("is_delete <=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIn(List<Integer> values) {
            addCriterion("is_delete in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotIn(List<Integer> values) {
            addCriterion("is_delete not in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteBetween(Integer value1, Integer value2) {
            addCriterion("is_delete between", value1, value2, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotBetween(Integer value1, Integer value2) {
            addCriterion("is_delete not between", value1, value2, "isDelete");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}