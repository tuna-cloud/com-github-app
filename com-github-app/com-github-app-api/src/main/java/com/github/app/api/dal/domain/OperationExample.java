package com.github.app.api.dal.domain;

import java.util.ArrayList;
import java.util.List;

public class OperationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OperationExample() {
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

        public Criteria andNumberIsNull() {
            addCriterion("number is null");
            return (Criteria) this;
        }

        public Criteria andNumberIsNotNull() {
            addCriterion("number is not null");
            return (Criteria) this;
        }

        public Criteria andNumberEqualTo(Integer value) {
            addCriterion("number =", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotEqualTo(Integer value) {
            addCriterion("number <>", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThan(Integer value) {
            addCriterion("number >", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("number >=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThan(Integer value) {
            addCriterion("number <", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThanOrEqualTo(Integer value) {
            addCriterion("number <=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberIn(List<Integer> values) {
            addCriterion("number in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotIn(List<Integer> values) {
            addCriterion("number not in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberBetween(Integer value1, Integer value2) {
            addCriterion("number between", value1, value2, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("number not between", value1, value2, "number");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andFilterUrlIsNull() {
            addCriterion("filter_url is null");
            return (Criteria) this;
        }

        public Criteria andFilterUrlIsNotNull() {
            addCriterion("filter_url is not null");
            return (Criteria) this;
        }

        public Criteria andFilterUrlEqualTo(String value) {
            addCriterion("filter_url =", value, "filterUrl");
            return (Criteria) this;
        }

        public Criteria andFilterUrlNotEqualTo(String value) {
            addCriterion("filter_url <>", value, "filterUrl");
            return (Criteria) this;
        }

        public Criteria andFilterUrlGreaterThan(String value) {
            addCriterion("filter_url >", value, "filterUrl");
            return (Criteria) this;
        }

        public Criteria andFilterUrlGreaterThanOrEqualTo(String value) {
            addCriterion("filter_url >=", value, "filterUrl");
            return (Criteria) this;
        }

        public Criteria andFilterUrlLessThan(String value) {
            addCriterion("filter_url <", value, "filterUrl");
            return (Criteria) this;
        }

        public Criteria andFilterUrlLessThanOrEqualTo(String value) {
            addCriterion("filter_url <=", value, "filterUrl");
            return (Criteria) this;
        }

        public Criteria andFilterUrlLike(String value) {
            addCriterion("filter_url like", value, "filterUrl");
            return (Criteria) this;
        }

        public Criteria andFilterUrlNotLike(String value) {
            addCriterion("filter_url not like", value, "filterUrl");
            return (Criteria) this;
        }

        public Criteria andFilterUrlIn(List<String> values) {
            addCriterion("filter_url in", values, "filterUrl");
            return (Criteria) this;
        }

        public Criteria andFilterUrlNotIn(List<String> values) {
            addCriterion("filter_url not in", values, "filterUrl");
            return (Criteria) this;
        }

        public Criteria andFilterUrlBetween(String value1, String value2) {
            addCriterion("filter_url between", value1, value2, "filterUrl");
            return (Criteria) this;
        }

        public Criteria andFilterUrlNotBetween(String value1, String value2) {
            addCriterion("filter_url not between", value1, value2, "filterUrl");
            return (Criteria) this;
        }

        public Criteria andParentNumberIsNull() {
            addCriterion("parent_number is null");
            return (Criteria) this;
        }

        public Criteria andParentNumberIsNotNull() {
            addCriterion("parent_number is not null");
            return (Criteria) this;
        }

        public Criteria andParentNumberEqualTo(Integer value) {
            addCriterion("parent_number =", value, "parentNumber");
            return (Criteria) this;
        }

        public Criteria andParentNumberNotEqualTo(Integer value) {
            addCriterion("parent_number <>", value, "parentNumber");
            return (Criteria) this;
        }

        public Criteria andParentNumberGreaterThan(Integer value) {
            addCriterion("parent_number >", value, "parentNumber");
            return (Criteria) this;
        }

        public Criteria andParentNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("parent_number >=", value, "parentNumber");
            return (Criteria) this;
        }

        public Criteria andParentNumberLessThan(Integer value) {
            addCriterion("parent_number <", value, "parentNumber");
            return (Criteria) this;
        }

        public Criteria andParentNumberLessThanOrEqualTo(Integer value) {
            addCriterion("parent_number <=", value, "parentNumber");
            return (Criteria) this;
        }

        public Criteria andParentNumberIn(List<Integer> values) {
            addCriterion("parent_number in", values, "parentNumber");
            return (Criteria) this;
        }

        public Criteria andParentNumberNotIn(List<Integer> values) {
            addCriterion("parent_number not in", values, "parentNumber");
            return (Criteria) this;
        }

        public Criteria andParentNumberBetween(Integer value1, Integer value2) {
            addCriterion("parent_number between", value1, value2, "parentNumber");
            return (Criteria) this;
        }

        public Criteria andParentNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("parent_number not between", value1, value2, "parentNumber");
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