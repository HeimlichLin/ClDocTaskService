package idv.heimlich.BaseProject;

import org.slf4j.Logger;

import idv.heimlich.ClDocTaskService.common.db.IDBSession;
import idv.heimlich.ClDocTaskService.common.db.IDBSessionFactory;
import idv.heimlich.ClDocTaskService.common.db.impl.DBSessionFactoryImpl;
import idv.heimlich.ClDocTaskService.common.log.LogFactory;

public class Test {
	
	private static Logger LOGGER = LogFactory.getInstance();
	
	public static void main(String[] args) {
		LOGGER.debug("Test Start");
		IDBSessionFactory sessionFactory = new DBSessionFactoryImpl();
		IDBSession session = sessionFactory.getXdaoSession("");
		
	}

}
