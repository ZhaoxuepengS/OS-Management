package com.zxp.eduservice.service;

import com.zxp.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zxp.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Zhaoxuepeng
 * @since 2021-04-28
 */
public interface EduChapterService extends IService<EduChapter> {
    List<ChapterVo> getChapterVideoByCourseId(String courseId);
}
