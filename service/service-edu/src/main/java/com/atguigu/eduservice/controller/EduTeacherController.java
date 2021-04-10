package com.atguigu.eduservice.controller;


import com.atguigu.CommonUtils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.customException.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-28
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    // 查询讲师表中所有数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("allTeacher")
    public R findAllTeacher() {
        // 调用service 方法
        List<EduTeacher> allTeacherList = teacherService.list(null);
        return R.ok().data("items", allTeacherList);
    }

    // 逻辑删除讲师
    @ApiOperation(value = "根据id逻辑删除特定讲师")
    @DeleteMapping("{id}")
    public R deleteTeacherById(@ApiParam(name = "id", value = "讲师id", required = true)
                               @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        return flag ? R.ok() : R.error();
    }

    // 分页查询讲师
    @ApiOperation(value = "分页查询讲师")
    //current 当前页，limit每页显示的个数
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit) {
        // 创建配置对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        try {
            int u = 10/0;
        } catch (Exception e) {
            throw new GuliException(20001, "执行了自定义异常处理...");
        }

        teacherService.page(pageTeacher, null);
        long teacherTotal = pageTeacher.getTotal();
        List<EduTeacher> teacherRecords = pageTeacher.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("total", teacherTotal);
        map.put("rows", teacherRecords);
        return R.ok().data(map);
    }

    // 条件查询带分页
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        // 创建配置对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        // 构造条件对象
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // 判断条件值是否为空，不为空拼接条件
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            // 构建条件
            wrapper.like("name", name);
        }
        if (Optional.ofNullable(level).orElse(0) != 0) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }

        teacherService.page(pageTeacher, wrapper);

        long teacherTotal = pageTeacher.getTotal();
        List<EduTeacher> teacherRecords = pageTeacher.getRecords();
        return R.ok().data("total",  teacherTotal).data("rows", teacherRecords);
    }

    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        return save ? R.ok() : R.error();
    }

    // 根据讲师id查询
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

    // 讲师修改
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        return flag ? R.ok() : R.error();
    }
}

