package com.nie.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KeywordDictExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public KeywordDictExample() {
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

        public Criteria andKeywordIsNull() {
            addCriterion("keyword is null");
            return (Criteria) this;
        }

        public Criteria andKeywordIsNotNull() {
            addCriterion("keyword is not null");
            return (Criteria) this;
        }

        public Criteria andKeywordEqualTo(String value) {
            addCriterion("keyword =", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordNotEqualTo(String value) {
            addCriterion("keyword <>", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordGreaterThan(String value) {
            addCriterion("keyword >", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordGreaterThanOrEqualTo(String value) {
            addCriterion("keyword >=", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordLessThan(String value) {
            addCriterion("keyword <", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordLessThanOrEqualTo(String value) {
            addCriterion("keyword <=", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordLike(String value) {
            addCriterion("keyword like", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordNotLike(String value) {
            addCriterion("keyword not like", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordIn(List<String> values) {
            addCriterion("keyword in", values, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordNotIn(List<String> values) {
            addCriterion("keyword not in", values, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordBetween(String value1, String value2) {
            addCriterion("keyword between", value1, value2, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordNotBetween(String value1, String value2) {
            addCriterion("keyword not between", value1, value2, "keyword");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andSynonymIsNull() {
            addCriterion("synonym is null");
            return (Criteria) this;
        }

        public Criteria andSynonymIsNotNull() {
            addCriterion("synonym is not null");
            return (Criteria) this;
        }

        public Criteria andSynonymEqualTo(String value) {
            addCriterion("synonym =", value, "synonym");
            return (Criteria) this;
        }

        public Criteria andSynonymNotEqualTo(String value) {
            addCriterion("synonym <>", value, "synonym");
            return (Criteria) this;
        }

        public Criteria andSynonymGreaterThan(String value) {
            addCriterion("synonym >", value, "synonym");
            return (Criteria) this;
        }

        public Criteria andSynonymGreaterThanOrEqualTo(String value) {
            addCriterion("synonym >=", value, "synonym");
            return (Criteria) this;
        }

        public Criteria andSynonymLessThan(String value) {
            addCriterion("synonym <", value, "synonym");
            return (Criteria) this;
        }

        public Criteria andSynonymLessThanOrEqualTo(String value) {
            addCriterion("synonym <=", value, "synonym");
            return (Criteria) this;
        }

        public Criteria andSynonymLike(String value) {
            addCriterion("synonym like", value, "synonym");
            return (Criteria) this;
        }

        public Criteria andSynonymNotLike(String value) {
            addCriterion("synonym not like", value, "synonym");
            return (Criteria) this;
        }

        public Criteria andSynonymIn(List<String> values) {
            addCriterion("synonym in", values, "synonym");
            return (Criteria) this;
        }

        public Criteria andSynonymNotIn(List<String> values) {
            addCriterion("synonym not in", values, "synonym");
            return (Criteria) this;
        }

        public Criteria andSynonymBetween(String value1, String value2) {
            addCriterion("synonym between", value1, value2, "synonym");
            return (Criteria) this;
        }

        public Criteria andSynonymNotBetween(String value1, String value2) {
            addCriterion("synonym not between", value1, value2, "synonym");
            return (Criteria) this;
        }

        public Criteria andExtentedIsNull() {
            addCriterion("extented is null");
            return (Criteria) this;
        }

        public Criteria andExtentedIsNotNull() {
            addCriterion("extented is not null");
            return (Criteria) this;
        }

        public Criteria andExtentedEqualTo(String value) {
            addCriterion("extented =", value, "extented");
            return (Criteria) this;
        }

        public Criteria andExtentedNotEqualTo(String value) {
            addCriterion("extented <>", value, "extented");
            return (Criteria) this;
        }

        public Criteria andExtentedGreaterThan(String value) {
            addCriterion("extented >", value, "extented");
            return (Criteria) this;
        }

        public Criteria andExtentedGreaterThanOrEqualTo(String value) {
            addCriterion("extented >=", value, "extented");
            return (Criteria) this;
        }

        public Criteria andExtentedLessThan(String value) {
            addCriterion("extented <", value, "extented");
            return (Criteria) this;
        }

        public Criteria andExtentedLessThanOrEqualTo(String value) {
            addCriterion("extented <=", value, "extented");
            return (Criteria) this;
        }

        public Criteria andExtentedLike(String value) {
            addCriterion("extented like", value, "extented");
            return (Criteria) this;
        }

        public Criteria andExtentedNotLike(String value) {
            addCriterion("extented not like", value, "extented");
            return (Criteria) this;
        }

        public Criteria andExtentedIn(List<String> values) {
            addCriterion("extented in", values, "extented");
            return (Criteria) this;
        }

        public Criteria andExtentedNotIn(List<String> values) {
            addCriterion("extented not in", values, "extented");
            return (Criteria) this;
        }

        public Criteria andExtentedBetween(String value1, String value2) {
            addCriterion("extented between", value1, value2, "extented");
            return (Criteria) this;
        }

        public Criteria andExtentedNotBetween(String value1, String value2) {
            addCriterion("extented not between", value1, value2, "extented");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdIsNull() {
            addCriterion("last_update_id is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdIsNotNull() {
            addCriterion("last_update_id is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdEqualTo(BigDecimal value) {
            addCriterion("last_update_id =", value, "lastUpdateId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdNotEqualTo(BigDecimal value) {
            addCriterion("last_update_id <>", value, "lastUpdateId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdGreaterThan(BigDecimal value) {
            addCriterion("last_update_id >", value, "lastUpdateId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("last_update_id >=", value, "lastUpdateId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdLessThan(BigDecimal value) {
            addCriterion("last_update_id <", value, "lastUpdateId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("last_update_id <=", value, "lastUpdateId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdIn(List<BigDecimal> values) {
            addCriterion("last_update_id in", values, "lastUpdateId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdNotIn(List<BigDecimal> values) {
            addCriterion("last_update_id not in", values, "lastUpdateId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("last_update_id between", value1, value2, "lastUpdateId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("last_update_id not between", value1, value2, "lastUpdateId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIsNull() {
            addCriterion("last_update_time is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIsNotNull() {
            addCriterion("last_update_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeEqualTo(Date value) {
            addCriterion("last_update_time =", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotEqualTo(Date value) {
            addCriterion("last_update_time <>", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeGreaterThan(Date value) {
            addCriterion("last_update_time >", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_update_time >=", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeLessThan(Date value) {
            addCriterion("last_update_time <", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_update_time <=", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIn(List<Date> values) {
            addCriterion("last_update_time in", values, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotIn(List<Date> values) {
            addCriterion("last_update_time not in", values, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("last_update_time between", value1, value2, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_update_time not between", value1, value2, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andDataFromIsNull() {
            addCriterion("data_from is null");
            return (Criteria) this;
        }

        public Criteria andDataFromIsNotNull() {
            addCriterion("data_from is not null");
            return (Criteria) this;
        }

        public Criteria andDataFromEqualTo(String value) {
            addCriterion("data_from =", value, "dataFrom");
            return (Criteria) this;
        }

        public Criteria andDataFromNotEqualTo(String value) {
            addCriterion("data_from <>", value, "dataFrom");
            return (Criteria) this;
        }

        public Criteria andDataFromGreaterThan(String value) {
            addCriterion("data_from >", value, "dataFrom");
            return (Criteria) this;
        }

        public Criteria andDataFromGreaterThanOrEqualTo(String value) {
            addCriterion("data_from >=", value, "dataFrom");
            return (Criteria) this;
        }

        public Criteria andDataFromLessThan(String value) {
            addCriterion("data_from <", value, "dataFrom");
            return (Criteria) this;
        }

        public Criteria andDataFromLessThanOrEqualTo(String value) {
            addCriterion("data_from <=", value, "dataFrom");
            return (Criteria) this;
        }

        public Criteria andDataFromLike(String value) {
            addCriterion("data_from like", value, "dataFrom");
            return (Criteria) this;
        }

        public Criteria andDataFromNotLike(String value) {
            addCriterion("data_from not like", value, "dataFrom");
            return (Criteria) this;
        }

        public Criteria andDataFromIn(List<String> values) {
            addCriterion("data_from in", values, "dataFrom");
            return (Criteria) this;
        }

        public Criteria andDataFromNotIn(List<String> values) {
            addCriterion("data_from not in", values, "dataFrom");
            return (Criteria) this;
        }

        public Criteria andDataFromBetween(String value1, String value2) {
            addCriterion("data_from between", value1, value2, "dataFrom");
            return (Criteria) this;
        }

        public Criteria andDataFromNotBetween(String value1, String value2) {
            addCriterion("data_from not between", value1, value2, "dataFrom");
            return (Criteria) this;
        }

        public Criteria andLastUpdateNameIsNull() {
            addCriterion("last_update_name is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateNameIsNotNull() {
            addCriterion("last_update_name is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateNameEqualTo(String value) {
            addCriterion("last_update_name =", value, "lastUpdateName");
            return (Criteria) this;
        }

        public Criteria andLastUpdateNameNotEqualTo(String value) {
            addCriterion("last_update_name <>", value, "lastUpdateName");
            return (Criteria) this;
        }

        public Criteria andLastUpdateNameGreaterThan(String value) {
            addCriterion("last_update_name >", value, "lastUpdateName");
            return (Criteria) this;
        }

        public Criteria andLastUpdateNameGreaterThanOrEqualTo(String value) {
            addCriterion("last_update_name >=", value, "lastUpdateName");
            return (Criteria) this;
        }

        public Criteria andLastUpdateNameLessThan(String value) {
            addCriterion("last_update_name <", value, "lastUpdateName");
            return (Criteria) this;
        }

        public Criteria andLastUpdateNameLessThanOrEqualTo(String value) {
            addCriterion("last_update_name <=", value, "lastUpdateName");
            return (Criteria) this;
        }

        public Criteria andLastUpdateNameLike(String value) {
            addCriterion("last_update_name like", value, "lastUpdateName");
            return (Criteria) this;
        }

        public Criteria andLastUpdateNameNotLike(String value) {
            addCriterion("last_update_name not like", value, "lastUpdateName");
            return (Criteria) this;
        }

        public Criteria andLastUpdateNameIn(List<String> values) {
            addCriterion("last_update_name in", values, "lastUpdateName");
            return (Criteria) this;
        }

        public Criteria andLastUpdateNameNotIn(List<String> values) {
            addCriterion("last_update_name not in", values, "lastUpdateName");
            return (Criteria) this;
        }

        public Criteria andLastUpdateNameBetween(String value1, String value2) {
            addCriterion("last_update_name between", value1, value2, "lastUpdateName");
            return (Criteria) this;
        }

        public Criteria andLastUpdateNameNotBetween(String value1, String value2) {
            addCriterion("last_update_name not between", value1, value2, "lastUpdateName");
            return (Criteria) this;
        }

        public Criteria andIsCheckIsNull() {
            addCriterion("is_check is null");
            return (Criteria) this;
        }

        public Criteria andIsCheckIsNotNull() {
            addCriterion("is_check is not null");
            return (Criteria) this;
        }

        public Criteria andIsCheckEqualTo(Integer value) {
            addCriterion("is_check =", value, "isCheck");
            return (Criteria) this;
        }

        public Criteria andIsCheckNotEqualTo(Integer value) {
            addCriterion("is_check <>", value, "isCheck");
            return (Criteria) this;
        }

        public Criteria andIsCheckGreaterThan(Integer value) {
            addCriterion("is_check >", value, "isCheck");
            return (Criteria) this;
        }

        public Criteria andIsCheckGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_check >=", value, "isCheck");
            return (Criteria) this;
        }

        public Criteria andIsCheckLessThan(Integer value) {
            addCriterion("is_check <", value, "isCheck");
            return (Criteria) this;
        }

        public Criteria andIsCheckLessThanOrEqualTo(Integer value) {
            addCriterion("is_check <=", value, "isCheck");
            return (Criteria) this;
        }

        public Criteria andIsCheckIn(List<Integer> values) {
            addCriterion("is_check in", values, "isCheck");
            return (Criteria) this;
        }

        public Criteria andIsCheckNotIn(List<Integer> values) {
            addCriterion("is_check not in", values, "isCheck");
            return (Criteria) this;
        }

        public Criteria andIsCheckBetween(Integer value1, Integer value2) {
            addCriterion("is_check between", value1, value2, "isCheck");
            return (Criteria) this;
        }

        public Criteria andIsCheckNotBetween(Integer value1, Integer value2) {
            addCriterion("is_check not between", value1, value2, "isCheck");
            return (Criteria) this;
        }

        public Criteria andRelativedIsNull() {
            addCriterion("relatived is null");
            return (Criteria) this;
        }

        public Criteria andRelativedIsNotNull() {
            addCriterion("relatived is not null");
            return (Criteria) this;
        }

        public Criteria andRelativedEqualTo(String value) {
            addCriterion("relatived =", value, "relatived");
            return (Criteria) this;
        }

        public Criteria andRelativedNotEqualTo(String value) {
            addCriterion("relatived <>", value, "relatived");
            return (Criteria) this;
        }

        public Criteria andRelativedGreaterThan(String value) {
            addCriterion("relatived >", value, "relatived");
            return (Criteria) this;
        }

        public Criteria andRelativedGreaterThanOrEqualTo(String value) {
            addCriterion("relatived >=", value, "relatived");
            return (Criteria) this;
        }

        public Criteria andRelativedLessThan(String value) {
            addCriterion("relatived <", value, "relatived");
            return (Criteria) this;
        }

        public Criteria andRelativedLessThanOrEqualTo(String value) {
            addCriterion("relatived <=", value, "relatived");
            return (Criteria) this;
        }

        public Criteria andRelativedLike(String value) {
            addCriterion("relatived like", value, "relatived");
            return (Criteria) this;
        }

        public Criteria andRelativedNotLike(String value) {
            addCriterion("relatived not like", value, "relatived");
            return (Criteria) this;
        }

        public Criteria andRelativedIn(List<String> values) {
            addCriterion("relatived in", values, "relatived");
            return (Criteria) this;
        }

        public Criteria andRelativedNotIn(List<String> values) {
            addCriterion("relatived not in", values, "relatived");
            return (Criteria) this;
        }

        public Criteria andRelativedBetween(String value1, String value2) {
            addCriterion("relatived between", value1, value2, "relatived");
            return (Criteria) this;
        }

        public Criteria andRelativedNotBetween(String value1, String value2) {
            addCriterion("relatived not between", value1, value2, "relatived");
            return (Criteria) this;
        }

        public Criteria andIncompatibleIsNull() {
            addCriterion("incompatible is null");
            return (Criteria) this;
        }

        public Criteria andIncompatibleIsNotNull() {
            addCriterion("incompatible is not null");
            return (Criteria) this;
        }

        public Criteria andIncompatibleEqualTo(String value) {
            addCriterion("incompatible =", value, "incompatible");
            return (Criteria) this;
        }

        public Criteria andIncompatibleNotEqualTo(String value) {
            addCriterion("incompatible <>", value, "incompatible");
            return (Criteria) this;
        }

        public Criteria andIncompatibleGreaterThan(String value) {
            addCriterion("incompatible >", value, "incompatible");
            return (Criteria) this;
        }

        public Criteria andIncompatibleGreaterThanOrEqualTo(String value) {
            addCriterion("incompatible >=", value, "incompatible");
            return (Criteria) this;
        }

        public Criteria andIncompatibleLessThan(String value) {
            addCriterion("incompatible <", value, "incompatible");
            return (Criteria) this;
        }

        public Criteria andIncompatibleLessThanOrEqualTo(String value) {
            addCriterion("incompatible <=", value, "incompatible");
            return (Criteria) this;
        }

        public Criteria andIncompatibleLike(String value) {
            addCriterion("incompatible like", value, "incompatible");
            return (Criteria) this;
        }

        public Criteria andIncompatibleNotLike(String value) {
            addCriterion("incompatible not like", value, "incompatible");
            return (Criteria) this;
        }

        public Criteria andIncompatibleIn(List<String> values) {
            addCriterion("incompatible in", values, "incompatible");
            return (Criteria) this;
        }

        public Criteria andIncompatibleNotIn(List<String> values) {
            addCriterion("incompatible not in", values, "incompatible");
            return (Criteria) this;
        }

        public Criteria andIncompatibleBetween(String value1, String value2) {
            addCriterion("incompatible between", value1, value2, "incompatible");
            return (Criteria) this;
        }

        public Criteria andIncompatibleNotBetween(String value1, String value2) {
            addCriterion("incompatible not between", value1, value2, "incompatible");
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