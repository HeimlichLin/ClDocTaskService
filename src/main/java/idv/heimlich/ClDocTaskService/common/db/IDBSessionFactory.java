package idv.heimlich.ClDocTaskService.common.db;

public interface IDBSessionFactory {

	IDBSession getXdaoSession(String conn);

}
