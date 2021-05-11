package com.zxp.eduservice.controller;


import com.zxp.commonutils.R;
import com.zxp.eduservice.entity.EduCourse;
import com.zxp.eduservice.entity.vo.CourseInfoVo;
import com.zxp.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Zhaoxuepeng
 * @since 2021-04-28
 */
@Api(tags="课程管理")
@RestController
@RequestMapping("/eduservice/edu-course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    EduCourseService eduCourseService;

    //添加课程基本信息的方法
    @ApiOperation(value = "添加课程信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        //返回添加之后的课程id，为了后面添加大纲使用
        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseID",id);
    }

    //根据课程id查询课程基本信息
    @ApiOperation(value = "根据课程id查询课程基本信息")
    @GetMapping("getCourseById/{courseId}")
    public R getCourseById(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfoById(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    @ApiOperation(value = "更新课程基本信息")
    @PostMapping("updateCourseInfo")
    //更新课程基本信息
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

}

