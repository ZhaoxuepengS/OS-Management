package com.zxp.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.zxp.eduservice.entity.EduSubject;
import com.zxp.eduservice.entity.excel.SubjectDate;
import com.zxp.eduservice.entity.vo.oneSubjectVo;
import com.zxp.eduservice.entity.vo.twoSubjectVo;
import com.zxp.eduservice.listener.SubjectExcelListener;
import com.zxp.eduservice.mapper.EduSubjectMapper;
import com.zxp.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Zhaoxuepeng
 * @since 2021-04-21
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile multipartFile,EduSubjectService eduSubjectService) {
        try {

            EasyExcel.read(multipartFile.getInputStream(), SubjectDate.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();

        }catch (Exception e){

        }

    }

    @Override
    public List<oneSubjectVo> getAllSubject() {
        //查询所有1级分类
        QueryWrapper wrapperOne = new QueryWrapper();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        //查询所有2级分类
        QueryWrapper wrapperTwo = new QueryWrapper();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //创建最终的list存储封装数据
        List<oneSubjectVo> finalSubjectList = new ArrayList<>();

        //封装1级分类
        for (int i = 0; i < oneSubjectList.size(); i++) {

            oneSubjectVo oneSubject = new oneSubjectVo();
//            oneSubject.setId(oneSubjectList.get(i).getId(););
//            oneSubject.setTitle(oneSubjectList.get(i).getTitle());
//            finalSubjectList.add(oneSubject);
            //直接把前面对象的属性取出放倒后面对象中去
            BeanUtils.copyProperties(oneSubjectList.get(i),oneSubject);

            //封装2级分类
            List<twoSubjectVo> twoSubjectVoList = new ArrayList<>();
            for (int j = 0; j < twoSubjectList.size(); j++) {
                if(oneSubject.getId().equals(twoSubjectList.get(j).getParentId())){
                    twoSubjectVo twoSubject = new twoSubjectVo();
                    BeanUtils.copyProperties(twoSubjectList.get(j),twoSubject);
                    twoSubjectVoList.add(twoSubject);
                }
            }

            //把一级下面的所有二级分类放入
            oneSubject.setChildren(twoSubjectVoList);
            finalSubjectList.add(oneSubject);
        }


        return finalSubjectList;
    }
}
