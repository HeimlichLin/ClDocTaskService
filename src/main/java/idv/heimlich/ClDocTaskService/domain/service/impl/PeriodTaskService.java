package idv.heimlich.ClDocTaskService.domain.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import idv.heimlich.ClDocTaskService.domain.service.AbtractTask;
import idv.heimlich.ClDocTaskService.domain.service.TaskContext;

/**
 * 週期性任務服務
 */
public class PeriodTaskService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static Logger logger = LoggerFactory
			.getLogger(PeriodTaskService.class);

	private final List<TaskHolder> tasks = new ArrayList<>();
	private ScheduledExecutorService service;
	private final long timeout = 60 * 4;

	public PeriodTaskService(final String serviceName) {
		this.serviceName = serviceName;
	}

	private final String serviceName;

	private class TaskHolder {
		String name;
		Long period;
		Class<? extends AbtractTask> taskClass;

		public TaskHolder(final String name, final Long period,
				final Class<? extends AbtractTask> taskClass) {
			super();
			this.name = name;
			this.period = period;
			this.taskClass = taskClass;
		}

	}

	/**
	 * 加入週期性任務
	 * 
	 * @param name
	 *            作業名稱
	 * 
	 * @param period
	 *            下次週期
	 * @param taskClass
	 * 
	 */
	public void addPeriodTask(final String name, final Long period,
			final Class<? extends AbtractTask> taskClass) {
		this.tasks.add(new TaskHolder(name, period, taskClass));
	}

	/**
	 * 檢查執行狀況
	 * 
	 * @param context
	 */
	private void checkStatus(final TaskContext context) {
		while (context.isContinueNotExit()) {
			context.pinRunning();
			try {
				logger.debug(this.serviceName + " is execute..");
				TimeUnit.SECONDS.sleep(10);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
		logger.info("call interrupt:" + this.serviceName);
		this.service.shutdownNow();

		try {
			logger.info("awaitTermination :" + this.serviceName);
			if (!this.service.awaitTermination(this.timeout,
					TimeUnit.SECONDS)) {
				logger.info("nono Termination wait");
			}
		} catch (final InterruptedException e) {
			logger.info("time out call shutdownNow  " + this.serviceName);
		}
		logger.info("stop PeriodTaskService:" + this.serviceName);
	}

	private void runTasks() {
		logger.info(this.serviceName + "啟動任務數量:" + this.tasks.size());
		this.service = Executors.newScheduledThreadPool(this.tasks.size());
		int delay = 0;// 延遲時間
		for (final TaskHolder task : this.tasks) {
			this.service.scheduleWithFixedDelay(new Runnable() {
				@Override
				public void run() {
					AbtractTask newInstance;
					try {
						logger.info(PeriodTaskService.this.serviceName + " 啟動"
								+ task.name);
						newInstance = task.taskClass.newInstance();
						newInstance.setName(task.name);
						newInstance.execute();
					} catch (final InstantiationException
							| IllegalAccessException e) {
						logger.error(PeriodTaskService.this.serviceName
								+ " newInstance error task" + task, e);
					}

				}
			}, delay++, task.period, TimeUnit.SECONDS);
		}
	}

	public void start() {
		final TaskContext context = TaskContextFactory
				.getContext(this.serviceName);
		try {
			context.pinRunning();
			this.runTasks();
			this.checkStatus(context);
		} finally {
			context.pingKilled();
		}

	}

}
