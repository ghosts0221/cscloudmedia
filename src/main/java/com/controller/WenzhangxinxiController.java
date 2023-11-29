package com.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.common.StorageSharedKeyCredential;
import com.entity.ConfigEntity;
import com.entity.EIException;
import com.service.ConfigService;
import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.WenzhangxinxiEntity;
import com.entity.view.WenzhangxinxiView;

import com.service.WenzhangxinxiService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;
import java.io.IOException;
import com.service.StoreupService;
import com.entity.StoreupEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文章信息
 * 后端接口
 * @author 
 * @email 
 * @date 2023-11-25 09:37:48
 */
@RestController
@RequestMapping("/wenzhangxinxi")
public class WenzhangxinxiController {
    @Autowired
    private WenzhangxinxiService wenzhangxinxiService;

    @Autowired
    private StoreupService storeupService;

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
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,WenzhangxinxiEntity wenzhangxinxi,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			wenzhangxinxi.setYonghuming((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<WenzhangxinxiEntity> ew = new EntityWrapper<WenzhangxinxiEntity>();
		PageUtils page = wenzhangxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, wenzhangxinxi), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,WenzhangxinxiEntity wenzhangxinxi, 
		HttpServletRequest request){
        EntityWrapper<WenzhangxinxiEntity> ew = new EntityWrapper<WenzhangxinxiEntity>();
		PageUtils page = wenzhangxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, wenzhangxinxi), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( WenzhangxinxiEntity wenzhangxinxi){
       	EntityWrapper<WenzhangxinxiEntity> ew = new EntityWrapper<WenzhangxinxiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( wenzhangxinxi, "wenzhangxinxi")); 
        return R.ok().put("data", wenzhangxinxiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(WenzhangxinxiEntity wenzhangxinxi){
        EntityWrapper< WenzhangxinxiEntity> ew = new EntityWrapper< WenzhangxinxiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( wenzhangxinxi, "wenzhangxinxi")); 
		WenzhangxinxiView wenzhangxinxiView =  wenzhangxinxiService.selectView(ew);
		return R.ok("查询文章信息成功").put("data", wenzhangxinxiView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        WenzhangxinxiEntity wenzhangxinxi = wenzhangxinxiService.selectById(id);
		wenzhangxinxi.setClicknum(wenzhangxinxi.getClicknum()+1);
		wenzhangxinxi.setClicktime(new Date());
		wenzhangxinxiService.updateById(wenzhangxinxi);
        return R.ok().put("data", wenzhangxinxi);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        WenzhangxinxiEntity wenzhangxinxi = wenzhangxinxiService.selectById(id);
		wenzhangxinxi.setClicknum(wenzhangxinxi.getClicknum()+1);
		wenzhangxinxi.setClicktime(new Date());
		wenzhangxinxiService.updateById(wenzhangxinxi);
        return R.ok().put("data", wenzhangxinxi);
    }
    


    /**
     * 赞或踩
     */
    @RequestMapping("/thumbsup/{id}")
    public R vote(@PathVariable("id") String id,String type){
        WenzhangxinxiEntity wenzhangxinxi = wenzhangxinxiService.selectById(id);
        if(type.equals("1")) {
        	wenzhangxinxi.setThumbsupnum(wenzhangxinxi.getThumbsupnum()+1);
        } else {
        	wenzhangxinxi.setCrazilynum(wenzhangxinxi.getCrazilynum()+1);
        }
        wenzhangxinxiService.updateById(wenzhangxinxi);
        return R.ok("投票成功");
    }

    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody WenzhangxinxiEntity wenzhangxinxi, HttpServletRequest request){
    	wenzhangxinxi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(wenzhangxinxi);
		String fileName = (String) request.getSession().getAttribute("file");
		wenzhangxinxi.setTupian(fileName);
		wenzhangxinxiService.insert(wenzhangxinxi);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody WenzhangxinxiEntity wenzhangxinxi, HttpServletRequest request){
    	wenzhangxinxi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(wenzhangxinxi);
		String fileName = (String) request.getSession().getAttribute("file");
		wenzhangxinxi.setTupian(fileName);
		wenzhangxinxiService.insert(wenzhangxinxi);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody WenzhangxinxiEntity wenzhangxinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(wenzhangxinxi);
		String fileName = (String) request.getSession().getAttribute("file");
		if (fileName != null && fileName.equals(wenzhangxinxi.getTupian())) {
			// 文件没有变化，按原本的图片执行
			wenzhangxinxiService.updateById(wenzhangxinxi);// 全部更新
		} else {
			wenzhangxinxi.setTupian(fileName);
			wenzhangxinxiService.updateById(wenzhangxinxi);// 全部更新
		}        return R.ok();
    }

	@RequestMapping("/upload")
	public R upload(@RequestParam("file") MultipartFile file, String type,HttpServletRequest request) throws Exception {

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

		// 使用 BlobContainerClient 创建 BlobClient
		BlobClient blobClient = containerClient.getBlobClient(fileName);

		InputStream targetStream = new ByteArrayInputStream(file.getBytes());
		blobClient.upload(targetStream, file.getSize(), true);

		BlobHttpHeaders headers = new BlobHttpHeaders();
		headers.setContentType(file.getContentType());
		blobClient.setHttpHeaders(headers);
		String fileUrl = "https://soupfish.blob.core.windows.net/soup/" + fileName;
		request.getSession().setAttribute("file",fileName);

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
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        wenzhangxinxiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<WenzhangxinxiEntity> wrapper = new EntityWrapper<WenzhangxinxiEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			wrapper.eq("yonghuming", (String)request.getSession().getAttribute("username"));
		}

		int count = wenzhangxinxiService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	
	/**
     * 前端智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,WenzhangxinxiEntity wenzhangxinxi, HttpServletRequest request,String pre){
        EntityWrapper<WenzhangxinxiEntity> ew = new EntityWrapper<WenzhangxinxiEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = entry.getKey();
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		params.put("sort", "clicknum");
        params.put("order", "desc");
		PageUtils page = wenzhangxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, wenzhangxinxi), params), params));
        return R.ok().put("data", page);
    }







}
