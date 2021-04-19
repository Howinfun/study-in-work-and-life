package com.winfun.log.test.controller;

import com.shopline.common.response.ApiResponse;
import com.shopline.resource.common.dto.request.BatchUploadImageByUrlReqDto;
import com.shopline.resource.common.dto.request.DownloadReqDto;
import com.shopline.resource.common.dto.request.UploadFileReqDto;
import com.shopline.resource.common.dto.request.UploadImageByUrlReqDto;
import com.shopline.resource.common.dto.request.UploadImageReqDto;
import com.shopline.resource.common.dto.response.BatchUploadImageByUrlItemRespDto;
import com.shopline.resource.common.dto.response.DownloadRespDto;
import com.shopline.resource.common.dto.response.UploadFileRespDto;
import com.shopline.resource.common.dto.response.UploadImageRespDto;
import com.shopline.resource.common.enums.ImageDomainType;
import com.shopline.resource.sdk.service.ResourceSdkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author winfun
 * @date 2021/3/12 5:14 下午
 **/
@Slf4j
@RequestMapping("/resource")
@RestController
public class UploadTestController {

    @Autowired
    private ResourceSdkService resourceSdkService;

    /**
     * 上传文件
     *
     * @param multipartFile 文件
     * @return 上传结果
     */
    @PostMapping("/file/upload")
    public ApiResponse<UploadFileRespDto> upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        /* 获取信息 */
        String originalFilename = multipartFile.getOriginalFilename();
        log.info("[前端上传文件] 开始, 原始文件名: {}", originalFilename);
        UploadFileReqDto reqDto = new UploadFileReqDto();
        byte[] fileBytes = multipartFile.getBytes();
        reqDto.setFileBytes(fileBytes);
        reqDto.setFileName(originalFilename);
        UploadFileRespDto respDto = this.resourceSdkService.uploadFile(reqDto);
        return ApiResponse.success(respDto);
    }

    @PostMapping("/file/download")
    public ApiResponse<DownloadRespDto> downloadFile(@RequestBody DownloadReqDto reqDto){

        DownloadRespDto downloadRespDto = this.resourceSdkService.downloadFile(reqDto);
        return ApiResponse.success(downloadRespDto);
    }

    @PostMapping("/image/upload")
    public ApiResponse<UploadImageRespDto> uploadImage(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        UploadImageReqDto reqDto = new UploadImageReqDto();
        String originalFilename = multipartFile.getOriginalFilename();
        log.info("[前端上传文件] 开始, 原始文件名: {}", originalFilename);
        byte[] fileBytes = multipartFile.getBytes();
        reqDto.setFileBytes(fileBytes);
        reqDto.setImageDomainType(ImageDomainType.STORE);
        reqDto.setMerchantId(3300003215L);
        reqDto.setStoreId(1000020L);
        return ApiResponse.success(this.resourceSdkService.uploadImage(reqDto));
    }

    @PostMapping("/image/upload/url")
    public ApiResponse<UploadImageRespDto> uploadImageByUrl(@RequestBody UploadImageByUrlReqDto reqDto){
        reqDto.setImageDomainType(ImageDomainType.STORE);
        reqDto.setMerchantId(3300003215L);
        reqDto.setStoreId(1000020L);
        return ApiResponse.success(this.resourceSdkService.uploadImageByUrl(reqDto));
    }

    @PostMapping("/image/upload/url/batch")
    public ApiResponse<List<BatchUploadImageByUrlItemRespDto>> uploadImageByUrlBatch(@RequestBody BatchUploadImageByUrlReqDto reqDto){
        reqDto.setImageDomainType(ImageDomainType.STORE);
        reqDto.setMerchantId(3300003215L);
        reqDto.setStoreId(1000020L);
        return ApiResponse.success(this.resourceSdkService.batchUploadImageByUrl(reqDto));
    }

    @PostMapping("/image/upload/url/batch/async")
    public ApiResponse<List<BatchUploadImageByUrlItemRespDto>> uploadImageByUrlBatchAsync(@RequestBody BatchUploadImageByUrlReqDto reqDto){
        reqDto.setImageDomainType(ImageDomainType.STORE);
        reqDto.setMerchantId(3300003215L);
        reqDto.setStoreId(1000020L);
        return ApiResponse.success(this.resourceSdkService.batchUploadImageByUrlAsyn(reqDto));
    }

}
