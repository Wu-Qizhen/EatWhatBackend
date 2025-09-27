package com.eatwhat.mapper;

import com.github.pagehelper.Page;
import com.eatwhat.annotation.AutoFill;
import com.eatwhat.dto.EmployeePageQueryDTO;
import com.eatwhat.entity.Employee;
import com.eatwhat.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     *
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 新增员工
     *
     * @param employee
     */
    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into employee(id, name, username, password, phone, sex, id_number, status, create_time, update_time, create_user, update_user) " +
            "values" +
            "(#{id}, #{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Employee employee);

    /**
     * 分页查询员工
     *
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 修改员工状态
     *
     * @param employee
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);

    /**
     * 根据 ID 查询员工
     *
     * @param id
     * @return
     */
    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);
}