package com.xly.mall.common.base.db;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/*   ApplicationListener<ContextRefreshedEvent>

内置事件：ContextRefreshedEvent
ApplicationContext 被初始化或刷新时，该事件被发布。
这也可以在 ConfigurableApplicationContext接口中使用 refresh() 方法来发生。
此处的初始化是指：所有的Bean被成功装载，后处理Bean被检测并激活，所有Singleton Bean 被预实例化，ApplicationContext容器已就绪可用

*/
@Service("seInitializeService")
public class InitializeService implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired(required = false)
	private DynamicCreateDataSourceManager dynamicCreateDataSourceManager;

	private volatile boolean initialFlag;

	@Override
	public synchronized void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null && !initialFlag) {
			initialFlag = true;

			// 系统动态source初始化
			initDynamicSource();

			// 清理动态源
			clearDynamicSources();
		}
	}

	/**
	 * 清理动态源
	 */
	public static void clearDynamicSources() {
		DataSourceSwitcher.clearDataSourceType();
//		ScheduleSwitcher.clearScheduleContextType();
	}

	/**
	 * 初始化系统数据源，包括数据库，缓存
	 * 
	 * @param
	 */
	private void initDynamicSource() {
		if (dynamicCreateDataSourceManager != null) {
			dynamicCreateDataSourceManager.initCreateDataSource();
		}
	}

}
