package com.eatwhat.service;

import com.eatwhat.dto.DishDTO;
import com.eatwhat.dto.DishPageQueryDTO;
import com.eatwhat.entity.Dish;
import com.eatwhat.result.PageResult;
import com.eatwhat.vo.DishVO;

import java.util.List;

public interface DishService {

    /**
     * 新增菜品
     *
     * @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 批量删除菜品
     *
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据 ID 查询菜品
     *
     * @param id
     * @return
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * 修改菜品
     *
     * @param dishDTO
     */
    void updateWithFlavor(DishDTO dishDTO);

    /**
     * 菜品起售停售
     *
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 根据分类 ID 查询菜品
     *
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);

    /**
     * 条件查询菜品和口味
     *
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);
}