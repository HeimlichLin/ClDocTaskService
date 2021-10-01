package idv.heimlich.ClDocTaskService.domain.controller;

import idv.heimlich.ClDocTaskService.domain.code.DocTasks;
import idv.heimlich.ClDocTaskService.domain.service.impl.PeriodTaskService;

/**
 * 文件類型服務
 */
public class ClDocTaskService {

	private static final String CL_DOC_TASK_SERVICE = "ClDocTaskService";

	public static void main(final String[] args) {

		PeriodTaskService service = new PeriodTaskService(CL_DOC_TASK_SERVICE);
		// 文件類型作業
		for (final DocTasks task : DocTasks.values()) {
			service.addPeriodTask(task.getProcNo(), task.getPeriod(),
					task.getTaskClass());
		}
		service.start();
		service = null;
	}
}
