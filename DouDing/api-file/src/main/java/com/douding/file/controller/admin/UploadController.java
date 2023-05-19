package com.douding.file.controller.admin;

import com.douding.server.domain.Test;
import com.douding.server.dto.FileDto;
import com.douding.server.dto.ResponseDto;
import com.douding.server.enums.FileUseEnum;
import com.douding.server.service.FileService;
import com.douding.server.service.TestService;
import com.douding.server.util.Base64ToMultipartFile;
import com.douding.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

/*
    返回json 应用@RestController
    返回页面  用用@Controller
 */
@RequestMapping("/admin/file")
@RestController
public class UploadController {

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);
    public  static final String BUSINESS_NAME ="文件上传";
    @Resource
    private TestService testService;

    @Value("${file.path}")
    private String FILE_PATH;

    @Value("${file.domain}")
    private String FILE_DOMAIN;

    @Resource
    private FileService fileService;

    @RequestMapping("/upload")
    public ResponseDto upload(MultipartFile file) throws Exception {
// 检查上传的文件是否为空
        if (file.isEmpty()) {
            return null;
        }
        try {
            // 获取上传文件的原始文件名
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            // 设置文件保存路径（这里保存在当前项目的根目录下）
            String uploadDir = FILE_PATH;
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // 创建目标文件对象
            File destFile = new File(uploadDir + originalFilename);
            // 将上传文件保存到本地
            file.transferTo(destFile);
            ResponseDto<String> stringResponseDto = new ResponseDto<>();
            // 返回上传成功的消息
            stringResponseDto.setContent(FILE_DOMAIN+originalFilename);
            return stringResponseDto;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    //合并分片
    public void merge(FileDto fileDto) throws Exception {
        LOG.info("合并分片开始");

    }

    @GetMapping("/check/{key}")
    public ResponseDto check(@RequestParam("file") MultipartFile file,
                             @PathVariable String key) throws Exception {
        LOG.info("检查上传分片开始：{}", key);
        File destFile = new File(FILE_PATH);
        RandomAccessFile raf = new RandomAccessFile(destFile, "rw");
        // 计算分片大小
        // 定位到分片位置
        return null;
    }

}//end class
