package com.goldwind.securitysample.domain;

public class Resource {
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the resourceUrl
	 */
	public String getResourceUrl() {
		return resourceUrl;
	}

	/**
	 * @param resourceUrl the resourceUrl to set
	 */
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database column
	 *
	 * @mbggenerated Fri Oct 12 16:58:31 CST 2018
	 */
	private Integer id;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database column
	 *
	 * @mbggenerated Fri Oct 12 16:58:31 CST 2018
	 */
	private String resourceName;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database column
	 *
	 * @mbggenerated Fri Oct 12 16:58:31 CST 2018
	 */
	private String resourceUrl;

	private String method;
	private String description;
}