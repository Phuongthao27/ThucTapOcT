package com.globits.da.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public interface ExcelReaderService {
    void Import(MultipartFile file);
    Boolean hasExcelFomat(MultipartFile file);

}
