package com.controller;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.azure.cosmos.*;
import com.azure.cosmos.models.PartitionKey;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.common.StorageSharedKeyCredential;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.ConfigEntity;
import com.entity.EIException;
import com.service.ConfigService;
import com.utils.R;

import javax.servlet.http.HttpServletRequest;

/**
 * 上传文件映射表
 */
@RestController
@RequestMapping("file")
@SuppressWarnings({"unchecked","rawtypes"})
public class FileController{
	@Autowired
    private ConfigService configService;

	// 替换为你的存储帐户名称
	private static final String AZURE_ACCOUNT_NAME = "soupfish";

	// 替换为你的存储帐户密钥
	private static final String AZURE_ACCOUNT_KEY = "vwmS6PRxFkcBmp6ObsAgO5jXSVJnPIoUsCoCh1pgZLXLx5LJLSJj0vJehRzXFK1p7v+9jOMELdhw+AStwFipfw==";

	private static final String AZURE_CONTAINER_NAME = "soup";

	private static final String COSMOS_DB_ENDPOINT = "your-cosmos-db-endpoint";
	private static final String COSMOS_DB_KEY = "your-cosmos-db-key";
	private static final String COSMOS_DB_DATABASE_NAME = "your-database-name";
	private static final String COSMOS_DB_CONTAINER_NAME = "your-container-name";
	/**
	 * 上传文件
	 */


	@RequestMapping("/upload")
	public R upload(@RequestParam("file") MultipartFile file, String type, HttpServletRequest request) throws Exception {

		if (file.isEmpty()) {
			throw new EIException("上传文件不能为空");
		}

		// 使用存储帐户名称和密钥创建 BlobServiceClient
		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
				.endpoint("https://" + AZURE_ACCOUNT_NAME + ".blob.core.windows.net")
				.credential(new StorageSharedKeyCredential(AZURE_ACCOUNT_NAME, AZURE_ACCOUNT_KEY))
				.buildClient();

		// 使用 BlobServiceClient 创建 BlobContainerClient
		BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(AZURE_CONTAINER_NAME);

		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
		String fileName = new Date().getTime() + "." + fileExt;
		if (isVideoFile(fileExt)){
			String videName = new Date().getTime()+"."+fileExt;
			request.getSession().setAttribute("video",videName);
		}else {
			request.getSession().setAttribute("file",fileName);
		}

		// 使用 BlobContainerClient 创建 BlobClient
		BlobClient blobClient = containerClient.getBlobClient(fileName);

		InputStream targetStream = new ByteArrayInputStream(file.getBytes());
		blobClient.upload(targetStream, file.getSize(), true);

		BlobHttpHeaders headers = new BlobHttpHeaders();
		headers.setContentType(file.getContentType());
		blobClient.setHttpHeaders(headers);
		String fileUrl = "https://soupfish.blob.core.windows.net/soup/" + fileName;

		if(StringUtils.isNotBlank(type) && type.equals("1")) {
			ConfigEntity configEntity = configService.selectOne(new EntityWrapper<ConfigEntity>().eq("name", "faceFile"));
			if(configEntity==null) {
				configEntity = new ConfigEntity();
				configEntity.setName("faceFile");
				configEntity.setValue(fileName);
			} else {
				configEntity.setValue(fileName);
			}
			configService.insertOrUpdate(configEntity);
		}

		return R.ok().put("file", fileName);
	}

	private boolean isVideoFile(String fileExt) {
		return fileExt != null && (fileExt.equalsIgnoreCase("mp4") || fileExt.equalsIgnoreCase("avi") || fileExt.equalsIgnoreCase("mov") || fileExt.equalsIgnoreCase("mkv")|| fileExt.equalsIgnoreCase("mp3"));
	}


//	private void saveMetadataToCosmosDB(String fileName, String contentType) {
//		try {
//			CosmosClientBuilder cosmosClientBuilder = new CosmosClientBuilder()
//					.endpoint(COSMOS_DB_ENDPOINT)
//					.key(COSMOS_DB_KEY)
//					.consistencyLevel(ConsistencyLevel.EVENTUAL)
//					.contentResponseOnWriteEnabled(true);
//
//			CosmosClient cosmosClient = cosmosClientBuilder.buildClient();
//			CosmosDatabase cosmosDatabase = cosmosClient.getDatabase(COSMOS_DB_DATABASE_NAME);
//			CosmosContainer cosmosContainer = cosmosDatabase.getContainer(COSMOS_DB_CONTAINER_NAME);
//
//			FileMetadataEntity metadataEntity = new FileMetadataEntity();
//			metadataEntity.setId(UUID.randomUUID().toString());
//			metadataEntity.setFileName(fileName);
//			metadataEntity.setFileType(contentType);
//			// 其他元数据字段根据需要设置
//
//			ItemRequestOptions options = new ItemRequestOptions();
//			options.partitionKey(new PartitionKey(metadataEntity.getId()));
//
//			cosmosContainer.createItem(metadataEntity, options, FileMetadataEntity.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//			// 处理异常，根据实际情况进行日志记录等操作
//		}
//	}
//
	/**
	 * 下载文件
	 */
	@IgnoreAuth
	@RequestMapping("/download")
	public ResponseEntity<byte[]> download(@RequestParam String fileName) {
		try {
			File path = new File(ResourceUtils.getURL("classpath:static").getPath());
			if(!path.exists()) {
			    path = new File("");
			}
			File upload = new File(path.getAbsolutePath(),"/upload/");
			if(!upload.exists()) {
			    upload.mkdirs();
			}
			File file = new File(upload.getAbsolutePath()+"/"+fileName);
			if(file.exists()){
				/*if(!fileService.canRead(file, SessionManager.getSessionUser())){
					getResponse().sendError(403);
				}*/
				HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);    
			    headers.setContentDispositionFormData("attachment", fileName);    
			    return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
