package idv.heimlich.ClDocTaskService.domain.service.impl;

import idv.heimlich.ClDocTaskService.domain.service.TaskContext;

public class TaskContextFactory {

	public static TaskContext getContext(final String procNo) {
		return new TaskFileContext(
				procNo.replaceAll("/", "_").replace("\\", "_"));
	}

}
