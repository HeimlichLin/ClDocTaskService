package idv.heimlich.ClDocTaskService.domain.code;

import idv.heimlich.ClDocTaskService.domain.service.AbtractTask;

/****
 * 訊息類型任務
 */
public enum DocTasks {

	// clfetchL6(ClfetchL6Task.class, 60 * 3l), // 解析移倉訊息
	// clprocL6(ClprocL6Task.class, 60 * 10l);//// 處理移倉訊息
//		clprocF3(ClprocF3Task.class, 60 * 3l),// 建立料清表(PFTZB)

	;//

	final String procNo;
	final Long period;
	final Class<? extends AbtractTask> taskClass;

	/**
	 *
	 * @param procNo
	 * @param period
	 * @param taskClass
	 */
	private DocTasks(final Class<? extends AbtractTask> taskClass,
			final Long period) {

		this.taskClass = taskClass;
		this.period = period;
		this.procNo = this.name();

	}

	public String getProcNo() {
		return this.procNo;
	}

	public Long getPeriod() {
		return this.period;
	}

	public Class<? extends AbtractTask> getTaskClass() {
		return this.taskClass;
	}

}
