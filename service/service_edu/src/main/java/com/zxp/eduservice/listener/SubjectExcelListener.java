package com.zxp.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxp.eduservice.entity.EduSubject;
import com.zxp.eduservice.entity.excel.SubjectDate;
import com.zxp.eduservice.service.EduSubjectService;
import com.zxp.servicebase.exceptionHandler.GuliException;

public class SubjectExcelListener extends AnalysisEventListener<SubjectDate> {


    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }
    public SubjectExcelListener() {
    }

    public EduSubjectService eduSubjectService;


    @Override
    public void invoke(SubjectDate subjectDate, AnalysisContext analysisContext) {
        if (subjectDate == null){
            throw new GuliException(20001,"文件数据为空");
        }

        //判断一级分类是否重复
        EduSubject existOneSubject = this.existOneSubject(eduSubjectService,subjectDate.getOneSubjectName());
        if(existOneSubject == null){
            existOneSubject = new EduSubject();
            existOneSubject.setTitle(subjectDate.getOneSubjectName());
            existOneSubject.setParentId("0");
            eduSubjectService.save(existOneSubject);
        }

        //获取一级分类生成的id
        String pid = existOneSubject.getId();

        //判断二级分类是否重复
        EduSubject existTwoSubject = this.existTwoSubject(eduSubjectService,subjectDate.getTwoSubjectName(), pid);
        if(existTwoSubject == null) {
            existTwoSubject = new EduSubject();
            existTwoSubject.setTitle(subjectDate.getTwoSubjectName());
            existTwoSubject.setParentId(pid);
            eduSubjectService.save(existTwoSubject);
        }

    }

    //判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject one = eduSubjectService.getOne(wrapper);
        return one;
    }

    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject two = eduSubjectService.getOne(wrapper);
        return two;
    }

    //判断二级分类不能重复添加

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
