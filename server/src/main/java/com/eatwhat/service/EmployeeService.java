package com.eatwhat.service;

import com.eatwhat.dto.EmployeeDTO;
import com.eatwhat.dto.EmployeeLoginDTO;
import com.eatwhat.dto.EmployeePageQueryDTO;
import com.eatwhat.entity.Employee;
import com.eatwhat.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 保存员工
     *
     * @param employeeDTO
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 分页查询员工列表
     *
     * @param employeePageQueryDTO
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用禁用员工账号
     *
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 修改员工信息
     *
     * @param employeeDTO
     */
    void update(EmployeeDTO employeeDTO);

    /**
     * 根据 ID 查询员工
     *
     * @param id
     * @return
     */
    Employee getById(Long id);
}