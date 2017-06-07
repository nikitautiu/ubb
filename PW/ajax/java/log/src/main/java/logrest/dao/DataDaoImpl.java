package logrest.dao;

import java.util.List;

import logrest.model.Log;
import logrest.model.Severity;
import logrest.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

public class DataDaoImpl implements DataDao {

	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;

	@Override
	public boolean addEntity(Log log) throws Exception {

		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		session.save(log);
		tx.commit();
		session.close();

		return false;
	}

	@Override
	public Log getEntityById(long id) throws Exception {
		session = sessionFactory.openSession();
		Log log = (Log) session.load(Log.class,
				new Long(id));
		tx = session.getTransaction();
		session.beginTransaction();
		tx.commit();
		return log;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Log> getEntityList(String username, String severity, String type) throws Exception {
		session = sessionFactory.openSession();
		String queryString = "";
		if(username != null) {
			if(!queryString.isEmpty())
				queryString += " and ";
			queryString += "l.creator.username = :username";
		}
		if(severity != null) {
			if(!queryString.isEmpty())
				queryString += " and ";
			queryString += "l.severity = :severity";
		}
		if(type != null) {
			if(!queryString.isEmpty())
				queryString += " and ";
			queryString += "l.type = :type";
		}
		if(!queryString.isEmpty())
			queryString = " where " + queryString;
		Query query = session.createQuery("from Log as l " + queryString);
		if(username != null) {
			query.setParameter("username", username);
		}
		if(severity != null) {
			query.setParameter("severity", Severity.valueOf(severity));
		}
		if(type != null) {
			query.setParameter("type", type);
		}

		List<Log> logList = query.list();
		session.close();
		return logList;
	}
	
	@Override
	public boolean deleteEntity(long id)
			throws Exception {
		session = sessionFactory.openSession();
		Object o = session.load(Log.class, id);
		tx = session.getTransaction();
		session.beginTransaction();
		session.delete(o);
		tx.commit();
		return false;
	}

	@Override
	public User getUserByName(String username) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from User as u where u.username = :username")
				.setParameter("username", username);
		List<User> list = query.list();
		if (list == null || list.isEmpty()) {
			return null;
		}
		tx = session.getTransaction();
		tx.commit();
		return list.get(0);
	}

}
