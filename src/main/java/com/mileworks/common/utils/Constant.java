package com.mileworks.common.utils;

/**
 * 常量
 * 
 * @author ©mileworks
 * @email borrip0419@gmail.com
 * @date 2016年11月15日 下午1:23:52
 */
public class Constant {
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;

	/**
	 * 菜单类型
	 * 
	 * @author ©mileworks
	 * @email borrip0419@gmail.com
	 * @date 2016年11月15日 下午1:24:29
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        private MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     * 
     * @author ©mileworks
     * @email borrip0419@gmail.com
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        private ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        private CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 登录状态
     */
    public enum LoginStatus{
    	
    	/**
    	 * 登出
    	 */
    	FORCEKICK("FORCEKICK") ,
    	
    	/**
    	 * 在线
    	 */
    	ONLINE("ONLINE") ,
    	
    	/**
    	 * 强迫踢出状态
    	 */
    	FORCEKICK_STATUS("KICKOUT_STATUS") ;
    	
    	private String value;

        private LoginStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    	
    }

}
