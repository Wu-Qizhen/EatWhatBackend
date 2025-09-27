package com.eatwhat.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.eatwhat.constant.MessageConstant;
import com.eatwhat.constant.PasswordConstant;
import com.eatwhat.constant.StatusConstant;
import com.eatwhat.dto.EmployeeDTO;
import com.eatwhat.dto.EmployeeLoginDTO;
import com.eatwhat.dto.EmployeePageQueryDTO;
import com.eatwhat.entity.Employee;
import com.eatwhat.exception.AccountLockedException;
import com.eatwhat.exception.AccountNotFoundException;
import com.eatwhat.exception.PasswordErrorException;
import com.eatwhat.mapper.EmployeeMapper;
import com.eatwhat.result.PageResult;
import com.eatwhat.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        Employee employee = employeeMapper.getByUsername(username);

        if (employee == null) {
            // 账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 密码比对
        // 进行加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            // 密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            // 账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        return employee;
    }

    /**
     * 新增员工
     *
     * @param employeeDTO
     */
    @Override
    public void save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setStatus(StatusConstant.ENABLE);
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        /* employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId()); */
        employeeMapper.insert(employee);
    }

    /**
     * 员工分页查询
     *
     * @param employeePageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        Page<Employee> employees = employeeMapper.pageQuery(employeePageQueryDTO);
        long total = employees.getTotal();
        List<Employee> result = employees.getResult();
        return new PageResult(total, result);
    }

    /**
     * 启用禁用员工账号
     *
     * @param status
     * @param id
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Employee build = Employee.builder().status(status).id(id).build();
        employeeMapper.update(build);
    }

    /**
     * 根据 ID 修改员工信息
     *
     * @param employeeDTO
     */
    @Override
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        /* employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(BaseContext.getCurrentId()); */
        employeeMapper.update(employee);
    }

    /**
     * 根据 ID 查询员工信息
     *
     * @param id
     * @return
     */
    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.getById(id);
        employee.setPassword("***");
        return employee;
    }
}