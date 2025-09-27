package com.eatwhat.mapper;

import com.github.pagehelper.Page;
import com.eatwhat.annotation.AutoFill;
import com.eatwhat.dto.DishPageQueryDTO;
import com.eatwhat.entity.Dish;
import com.eatwhat.enumeration.OperationType;
import com.eatwhat.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper {

    /**
     * 根据分类 ID 查询菜品数量
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 新增菜品
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 根据菜品分类 ID 查询菜品
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据 ID 查询菜品
     */
    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    /**
     * 根据 ID 删除菜品
     */
    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);

    /**
     * 根据 ID 批量删除菜品
     */
    void deleteByIds(List<Long> ids);

    /**
     * 修改菜品
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

    /**
     * 根据套餐 ID 查询菜品
     */
    @Select("select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = #{setmealId}")
    List<Dish> getBySetmealId(Long setmealId);

    /**
     * 动态条件查询菜品
     */
    List<Dish> list(Dish dish);

    /**
     * 根据条件统计菜品数量
     */
    Integer countByMap(Map<String, Object> map);
}