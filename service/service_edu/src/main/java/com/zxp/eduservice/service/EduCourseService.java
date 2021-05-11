package com.zxp.eduservice.service;

import com.zxp.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zxp.eduservice.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Zhaoxuepeng
 * @since 2021-04-28
 */
public interface EduCourseService extends IService<EduCourse> {
    public String saveCourseInfo(CourseInfoVo courseInfoVo);
    public CourseInfoVo getCourseInfoById(String courseid);
    public void updateCourseInfo(CourseInfoVo courseInfoVo);
}
