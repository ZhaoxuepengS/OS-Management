package com.zxp.eduservice.controller;


import com.zxp.commonutils.R;
import com.zxp.eduservice.entity.EduSubject;
import com.zxp.eduservice.entity.vo.oneSubjectVo;
import com.zxp.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Zhaoxuepeng
 * @since 2021-04-21
 */
@Api(tags="课程分类管理")
@RestController
@RequestMapping("/eduservice/edu-subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    //获取到上传过来的文件，读取文件内容
    @ApiOperation(value = "添加课程分类")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        //System.out.println("收到文件:"+file.getName()+" -----文件大小："+file.getSize());
        eduSubjectService.saveSubject(file,eduSubjectService);

        return R.ok();
    }

    //课程分类列表（树形）
    @ApiOperation(value = "课程分类列表")
    @GetMapping("getAllSubjct")
    public R getAllSubjct(){

        List<oneSubjectVo> list = eduSubjectService.getAllSubject();
        return R.ok().data("list",list);
    }
}

