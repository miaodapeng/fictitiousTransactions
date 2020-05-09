package com.vedeng.common.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.vedeng.common.util.CollectionSerializer;

public class RedisSessionDAO extends AbstractSessionDAO {

	private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);
	/**
	 * shiro-redis的session对象前缀
	 */
	//private RedisManager redisManager;
	@Resource(name="redisTemplate")
	private RedisTemplate<String, Object> redisTemplate;
//	@Resource(name="redisTemplate")
//	private ValueOperations<String, Object> valueOperations;
	private int expire;
	/**
	 * The Redis key prefix for the sessions 
	 */
	private String keyPrefix = "shiro_redis_session:";
	
    @PostConstruct  
    public void init(){  
       // logger.info("注入催收的序列/反序列类");
        CollectionSerializer<Serializable> collectionSerializer=CollectionSerializer.getInstance();  
        redisTemplate.setDefaultSerializer(collectionSerializer);  
        //redisTemplate默认采用的其实是valueSerializer，就算是采用其他ops也一样，这是一个坑。  
        redisTemplate.setValueSerializer(collectionSerializer);  
    }
	
	@Override
	public void update(Session session) throws UnknownSessionException {
		this.saveSession(session);
	}
	
	/**
	 * save session
	 * @param session
	 * @throws UnknownSessionException
	 */
	private void saveSession(Session session) throws UnknownSessionException{
		if(session == null || session.getId() == null){
			logger.error("session or session id is null");
			return;
		}

		String key = session.getId().toString();
		session.setTimeout(expire);		
		try {
			redisTemplate.opsForValue().set(keyPrefix+key, session, expire, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			logger.error("save session error:"+session.getId(), e);
		}

	}

	@Override
	public void delete(Session session) {
		//logger.info("start delete session id:"+session.getId());
		if(session == null || session.getId() == null){
			logger.error("session or session id is null");
			return;
		}
		redisTemplate.delete(keyPrefix+session.getId().toString());
		//logger.info("end delete session id:"+session.getId());

	}

	@Override
	public Collection<Session> getActiveSessions() {
		Set<Session> sessions = new HashSet<Session>();
		Set<String> keys = redisTemplate.keys(this.keyPrefix + "*");
		if(keys != null && keys.size()>0){
			for(String key:keys){
				Session s = (Session)redisTemplate.opsForValue().get(keyPrefix+key);
				sessions.add(s);
			}
		}
		
		return sessions;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
		//logger.info("end do create session id :" + session.getId());
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		if(sessionId == null){
			logger.error("session id is null");
			return null;
		}
		//Session s = (Session)redisTemplate.opsForValue().get(keyPrefix+sessionId);
		Session s = (Session) JedisUtils.getObject(keyPrefix+sessionId);

		if (s == null) {
			//logger.info("do read session null sessionId:"+sessionId);
		}
		return s;
	}
	
	/**
	 * Returns the Redis session keys
	 * prefix.
	 * @return The prefix
	 */
	public String getKeyPrefix() {
		return keyPrefix;
	}

	/**
	 * Sets the Redis sessions key 
	 * prefix.
	 * @param keyPrefix The prefix
	 */
	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
		//valueOperations = redisTemplate.opsForValue();
	}

	public int getExpire() {
		return expire;
	}

	public  void setExpire(int expire) {
		this.expire = expire;
	}
	
	
}
