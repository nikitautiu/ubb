package logrest.services;

import java.util.List;

import logrest.model.Log;
import logrest.model.User;

public interface DataServices {
	public boolean addEntity(Log log) throws Exception;
	public Log getEntityById(long id) throws Exception;
	public List<Log> getEntityList(String username, String severity, String type) throws Exception;
	public boolean deleteEntity(long id) throws Exception;

	User getUserByName(String username);
}
