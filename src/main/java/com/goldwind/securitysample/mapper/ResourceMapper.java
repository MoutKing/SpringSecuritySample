package com.goldwind.securitysample.mapper;

import com.goldwind.securitysample.domain.Resource;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface ResourceMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table resource
	 *
	 * @mbggenerated Fri Oct 12 16:58:31 CST 2018
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table resource
	 *
	 * @mbggenerated Fri Oct 12 16:58:31 CST 2018
	 */
	int insert(Resource record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table resource
	 *
	 * @mbggenerated Fri Oct 12 16:58:31 CST 2018
	 */
	Resource selectByPrimaryKey(Integer id);

	/**
	 * This method is generated by lisong
	 * @param resourceContent
	 * @return Resource instance
	 */
	Resource selectByResourceContent(String resourceContent);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table resource
	 *
	 * @mbggenerated Fri Oct 12 16:58:31 CST 2018
	 */
	List<Resource> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table resource
	 *
	 * @mbggenerated Fri Oct 12 16:58:31 CST 2018
	 */
	int updateByPrimaryKey(Resource record);
}