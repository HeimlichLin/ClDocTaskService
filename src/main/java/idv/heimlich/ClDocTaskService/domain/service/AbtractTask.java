package idv.heimlich.ClDocTaskService.domain.service;

import idv.heimlich.ClDocTaskService.domain.service.impl.TaskLaunch;

/**
 * 週期性任務
 */
public abstract class AbtractTask implements Task {

	private String name;

	public AbtractTask() {
		super();
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public void execute() {
		TaskLaunch.getLaunch().startNow(this.name, new Task() {

			@Override
			public void execute() {
				AbtractTask.this.doMyWork();
			}
		});
	}

	protected abstract void doMyWork();

}
