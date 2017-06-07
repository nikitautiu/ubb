package logrest.services;

import java.util.List;

import logrest.dao.DataDao;
import logrest.model.Log;
import logrest.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public class DataServicesImpl implements DataServices {

	@Autowired
	DataDao dataDao;
	
	@Override
	public boolean addEntity(Log log) throws Exception {
		return dataDao.addEntity(log);
	}

	@Override
	public Log getEntityById(long id) throws Exception {
		return dataDao.getEntityById(id);
	}

	@Override
	public List<Log> getEntityList(String username, String severity, String type) throws Exception {
		return dataDao.getEntityList(username, severity, type);
	}

	@Override
	public boolean deleteEntity(long id) throws Exception {
		return dataDao.deleteEntity(id);
	}

	@Override
	public User getUserByName(String username) {
		if(username == null)
			return null;
		return dataDao.getUserByName(username);
	}

}
