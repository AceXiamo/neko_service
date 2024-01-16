package com.xiamo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.xiamo.common.AjaxResult;
import com.xiamo.utils.BanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * The type File controller.
 *
 * @Author: AceXiamo
 * @ClassName: FileController
 * @Date: 2023 /3/4 18:56
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    /**
     * The constant ROOT_PATH.
     */
    private static final String ROOT_PATH = System.getProperty("user.dir");

    /**
     * Upload ajax result.
     *
     * @param file the file
     * @return the ajax result
     */
    @PostMapping("upload")
    public AjaxResult upload(MultipartFile file) {
        BanUtil.isBan();
        StringBuilder builder = new StringBuilder();
        builder.append("/file/")
                .append(IdUtil.objectId())
                .append(".")
                .append(getSuffix(file.getOriginalFilename()));
        String path = builder.toString();
        try {
            FileUtil.writeBytes(file.getBytes(), ROOT_PATH + path);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return AjaxResult.success(path);
    }

    /**
     * Gets suffix.
     *
     * @param originalName the original name
     * @return the suffix
     */
    private String getSuffix(String originalName) {
        if (StrUtil.isNotEmpty(originalName)) {
            String[] arr = originalName.split("\\.");
            return arr[arr.length - 1];
        }
        return "jpg";
    }

    /**
     * View.
     *
     * @param response the response
     * @param folder   the folder
     * @param filename the filename
     */
    @GetMapping("/view/{folder}/{filename}")
    public void view(HttpServletResponse response,
                     @PathVariable(name = "folder") String folder,
                     @PathVariable(name = "filename") String filename) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(ROOT_PATH)
                    .append("/")
                    .append(folder)
                    .append("/")
                    .append(filename);
            String path = builder.toString();
            Assert.isTrue(FileUtil.exist(path), "呃呃呃，路径有误");
            response.setContentType("image/jpeg");
            response.getOutputStream().write(FileUtil.readBytes(path));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
