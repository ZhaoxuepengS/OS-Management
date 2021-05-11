package com.zxp.eduservice.service;

import com.zxp.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zxp.eduservice.entity.vo.oneSubjectVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Zhaoxuepeng
 * @since 2021-04-21
 */
public interface EduSubjectService extends IService<EduSubject> {
    public void saveSubject(MultipartFile multipartFile,EduSubjectService eduSubjectService);

    public List<oneSubjectVo> getAllSubject();
}
