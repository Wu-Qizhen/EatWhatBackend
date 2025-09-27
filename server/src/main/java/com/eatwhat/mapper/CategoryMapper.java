package com.eatwhat.mapper;

import com.github.pagehelper.Page;
import com.eatwhat.annotation.AutoFill;
import com.eatwhat.enumeration.OperationType;
import com.eatwhat.dto.CategoryPageQueryDTO;
import com.eatwhat.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 插入数据
     *
     * @param category
     */
    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into category(type, name, sort, status, create_time, update_time, create_user, update_user)" +
            " VALUES" +
            " (#{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Category category);

    /**
     * 分页查询
     *
     * @param categoryPageQueryDTO
     * @return
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 根据 ID 删除分类
     *
     * @param id
     */
    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);

    /**
     * 根据 ID 修改分类
     *
     * @param category
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Category category);

    /**
     * 根据类型查询分类
     *
     * @param type
     * @return
     */
    List<Category> list(Integer type);
}