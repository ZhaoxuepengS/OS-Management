package com.zxp.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxp.commonutils.R;
import com.zxp.eduservice.entity.EduTeacher;
import com.zxp.eduservice.entity.vo.TeacherQuery;
import com.zxp.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Zhaoxuepeng
 * @since 2020-12-07
 */
@Api(tags="讲师管理")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    //吧service注入
    @Autowired
    private EduTeacherService teacherService;

    //查询讲师表中所有数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){
        //调用service方法实现查询操作
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }
    //逻辑删除讲师
    @DeleteMapping("{id}")
    @ApiOperation(value = "逻辑删除讲师")
    public R removeTeacher(@ApiParam(name = "id",value = "讲师ID",required = true)
                               @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //分页查询讲师的方法
    //current代表当前页，limit代表每页显示记录数
    @ApiOperation(value = "分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@ApiParam(name = "current",value = "当前页数",required = true) @PathVariable Long current,
                             @ApiParam(name = "limit",value = "每页数据条数",required = true)  @PathVariable Long limit){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<EduTeacher>(current, limit);
        //调用方法时，底层封装，把分页所有数据封装到pageTeacher对象里面
        teacherService.page(pageTeacher,null);

        long total = pageTeacher.getTotal(); //总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//每页数据list集合
        Map map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);

        return R.ok().data(map);
    }

    //条件查询带分页的方法
    @ApiOperation(value = "分页带条件查询讲师")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "current",value = "当前页数",required = true) @PathVariable Long current,
                                  @ApiParam(name = "limit",value = "每页数据条数",required = true)  @PathVariable Long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<EduTeacher>(current,limit);
        //构建条件
        QueryWrapper wrapper = new QueryWrapper();
        //多条件组合查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if(!StringUtils.isEmpty(name)){
            //构建条件
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

        //排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询带分页功能
        teacherService.page(pageTeacher,wrapper);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();//每页数据list集合

        return R.ok().data("total",total).data("rows",records);
    }
    //添加讲师接口
    @ApiOperation("添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        return teacherService.save(eduTeacher) ? R.ok() : R.error();
    }

    //根据ID查询讲师
    @GetMapping("{id}")
    @ApiOperation(value = "根据ID查询讲师")
    public R findTeacherById(@ApiParam(name = "id",value = "讲师ID",required = true) @PathVariable String id){

        EduTeacher byId = teacherService.getById(id);
        return R.ok().data("item",byId);
    }

    //修改讲师
    @PutMapping("{id}")
    @ApiOperation("修改讲师")
    public R updateTeacher(@ApiParam(name = "id",value = "讲师ID",required = true) @PathVariable String id,
                           @ApiParam(name = "teacher", value = "讲师对象", required = true) @RequestBody EduTeacher eduTeacher){

        eduTeacher.setId(id);
        return teacherService.updateById(eduTeacher) ? R.ok() : R.error();
    }

}

