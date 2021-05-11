package com.zxp.eduservice.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class oneSubjectVo {
    private String id;
    private String title;

    private List<twoSubjectVo> children = new ArrayList<>();
}
