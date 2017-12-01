package com.mileworks.modules.job.dao;

import com.mileworks.modules.sys.dao.BaseDao;
import com.mileworks.modules.job.entity.ScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志
 * 
 * @author ©mileworks
 * @email borrip0419@gmail.com
 * @date 2016年12月1日 下午10:30:02
 */
@Mapper
public interface ScheduleJobLogDao extends BaseDao<ScheduleJobLogEntity> {
	
}
