package com.zxp.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxp.eduservice.entity.EduChapter;
import com.zxp.eduservice.entity.EduVideo;
import com.zxp.eduservice.entity.chapter.ChapterVo;
import com.zxp.eduservice.entity.chapter.VideoVo;
import com.zxp.eduservice.mapper.EduChapterMapper;
import com.zxp.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxp.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Zhaoxuepeng
 * @since 2021-04-28
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    //课程大纲列表，根据课程id查询
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //最终要的到的数据列表
        ArrayList<ChapterVo> chapterVoFinalList = new ArrayList<>();

        //获取章节信息
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        wrapperChapter.orderByAsc("sort", "id");
        List<EduChapter> chapters = baseMapper.selectList(wrapperChapter);

        //获取里面所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        wrapperVideo.orderByAsc("sort", "id");
        List<EduVideo> videos = eduVideoService.list(wrapperVideo);
        //填充章节vo数据
        int count1 = chapters.size();
        for (int i = 0; i < count1; i++) {
            EduChapter chapter = chapters.get(i);
        //创建章节vo对象
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            chapterVoFinalList.add(chapterVo);
        //填充课时vo数据
            ArrayList<VideoVo> videoVoArrayList = new ArrayList<>();

            for (int j = 0; j < videos.size(); j++) {
                EduVideo video = videos.get(j);
                if(chapter.getId().equals(video.getChapterId())){
                    //创建课时vo对象
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVoArrayList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoArrayList);
        }
        return chapterVoFinalList;

    }
}
