/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.api.base;

import cloud.shopfly.b2c.core.base.model.dto.FileDTO;
import cloud.shopfly.b2c.core.base.model.vo.FileVO;
import cloud.shopfly.b2c.core.base.service.FileManager;
import cloud.shopfly.b2c.core.system.SystemErrorCode;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;


/**
 * 存储方案控制器
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-22 10:54:47
 */
@RestController
@RequestMapping("/uploaders")
@Api(tags = "上传图片api")
public class FileBaseController {

    @Autowired
    private FileManager fileManager;


    @ApiOperation(value = "文件上传", response = FileVO.class)
    @ApiImplicitParam(name = "scene", value = "业务场景", allowableValues = "goods,shop,member,other", required = true, dataType = "String", paramType = "query")
    @PostMapping
    public FileVO list(MultipartFile file, String scene) throws IOException {
        if (file != null && file.getOriginalFilename() != null) {
            //文件类型
            String contentType= file.getContentType();
            //获取文件名称
            String ext = contentType.substring(contentType.lastIndexOf("/") + 1, contentType.length());
            if (!FileUtil.isAllowUpImg(ext)) {
                throw new ServiceException(SystemErrorCode.E901.code(), "File format not allowed to upload, please uploadgif,jpg,png,jpeg,mp4,movFormat file.");
            }
            FileDTO input = new FileDTO();
            input.setSize(file.getSize());
            input.setName(file.getOriginalFilename());
            input.setStream(file.getInputStream());
            // mov格式的contentType是video/quicktime
            input.setExt(ext.equals("quicktime")?"mov":ext);
            return this.fileManager.upload(input, scene);
        } else {
            throw new ResourceNotFoundException("No files");
        }
    }

    @ApiOperation(value = "文件删除")
    @ApiImplicitParam(name = "file_path", value = "文件路径", required = true, dataType = "String", paramType = "query")
    @DeleteMapping
    public String delete(@ApiIgnore String filePath) {
        this.fileManager.deleteFile(filePath);
        return null;
    }


}
