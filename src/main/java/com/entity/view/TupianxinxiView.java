package com.entity.view;

import com.entity.TupianxinxiEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
 

/**
 * Image
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2023-11-25 09:37:48
 */
@TableName("tupianxinxi")
public class TupianxinxiView  extends TupianxinxiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public TupianxinxiView(){
	}
 
 	public TupianxinxiView(TupianxinxiEntity tupianxinxiEntity){
 	try {
			BeanUtils.copyProperties(this, tupianxinxiEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}
}
