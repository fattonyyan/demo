package com.yan.project.business.demo.entity;

import java.sql.Timestamp;

import com.yan.project.core.base.entity.BaseEntity;
import com.yan.project.core.persistence.annotation.Id;
import com.yan.project.core.persistence.annotation.Table;

@Table(name="demo", resultMap="id,name,password,age,gender,height,description,deleteFlag")
public class Demo extends BaseEntity {

	@Id
	private String id;
	
	private String name;
	
	private String password;
	
	private Integer age;
	
	private Integer gender;
	
	private Double height;
	
	private String description;
	
	private Timestamp createTime;
	
	private Timestamp modifyTime;
	
	private Integer deleteFlag;
	
	private String extend;
	
	public String toString(){
		String str = "["+"name:"+this.name+", age:"+this.age+"]";
		return str;
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}
}
