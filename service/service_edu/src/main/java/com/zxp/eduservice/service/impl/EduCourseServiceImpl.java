package com.zxp.eduservice.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.zxp.eduservice.entity.EduCourse;
import com.zxp.eduservice.entity.EduCourseDescription;
import com.zxp.eduservice.entity.vo.CourseInfoVo;
import com.zxp.eduservice.mapper.EduCourseMapper;
import com.zxp.eduservice.service.EduCourseDescriptionService;
import com.zxp.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxp.servicebase.exceptionHandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Zhaoxuepeng
 * @since 2021-04-28
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1.向课程表添加课程
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);

        if (insert==0){
            //添加失败
            throw new GuliException(20001,"添加课程失败");
        }
        //获取添加课程的id
        String cid = eduCourse.getId();
        //2.向课程简介表添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);

        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfoById(String courseid) {
        //1、查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseid);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //2、查询描述
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseid);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1.更新课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if(i==0){
            throw new GuliException(20001,"修改课程信失败");
        }
        //2.更新课程描述表
        String cid = courseInfoVo.getId();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(cid);
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        boolean b = eduCourseDescriptionService.updateById(eduCourseDescription);
        if(!b){
            throw new GuliException(20001,"修改课程信失败");
        }
    }


}
