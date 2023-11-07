package com.ohgiraffers.jpa.menu.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="Category")
@Table(name = "TBL_CATEGORY")
public class Category {

	@Id
	@Column(name = "CATEGORY_CODE")
	private int categoryCode;
	
	@Column(name = "CATEGORY_NAME")
	private String categoryName;
	
	@Column(name = "REF_CATEGORY_CODE", nullable = true)		// null값 들어올 수 있음!
	private Integer refCategoryCode;

	public Category() {}

	public Category(int categoryCode, String categoryName, Integer refCategoryCode) {
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
		this.refCategoryCode = refCategoryCode;
	}

	public int getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(int categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getRefCategoryCode() {
		return refCategoryCode;
	}

	public void setRefCategoryCode(Integer refCategoryCode) {
		this.refCategoryCode = refCategoryCode;
	}

	@Override
	public String toString() {
		return "Category{" +
				"categoryCode=" + categoryCode +
				", categoryName='" + categoryName + '\'' +
				", refCategoryCode=" + refCategoryCode +
				'}';
	}
}
