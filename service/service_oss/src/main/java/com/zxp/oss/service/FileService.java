package com.zxp.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String fileUpload(MultipartFile file);
}
