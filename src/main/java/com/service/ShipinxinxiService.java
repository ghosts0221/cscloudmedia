package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.ShipinxinxiEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.ShipinxinxiVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.ShipinxinxiView;


/**
 * Multimedia
 *
 * @author 
 * @email 
 * @date 2023-11-25 09:37:48
 */
public interface ShipinxinxiService extends IService<ShipinxinxiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ShipinxinxiVO> selectListVO(Wrapper<ShipinxinxiEntity> wrapper);
   	
   	ShipinxinxiVO selectVO(@Param("ew") Wrapper<ShipinxinxiEntity> wrapper);
   	
   	List<ShipinxinxiView> selectListView(Wrapper<ShipinxinxiEntity> wrapper);
   	
   	ShipinxinxiView selectView(@Param("ew") Wrapper<ShipinxinxiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<ShipinxinxiEntity> wrapper);
   	

}

