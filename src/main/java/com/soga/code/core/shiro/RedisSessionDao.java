package com.soga.code.core.shiro;

import com.soga.code.common.redis.RedisClient;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * 
 * 版权：融贯资讯 <br/>
 * 作者：huaxing.wang@rogrand.com <br/>
 * 生成日期：2016年12月26日 <br/>
 * 描述：〈shiro框架session共享持久层〉
 */
@Component
public class RedisSessionDao extends AbstractSessionDAO {
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RedisClient redisClient;
//	@Autowired
//	private RedisClientTest redisClientTest;
	

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		log.info("创建seesion,id=[{}]", session.getId().toString());
		try {
			redisClient.set(sessionId.toString(), session);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return sessionId;
	}

	@Override
	public void update(Session session) throws UnknownSessionException {
		log.info("更新seesion,id=[{}]", session.getId().toString());
		try {
			redisClient.replace(session.getId().toString(), session);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Session session) {
		log.info("删除seesion,id=[{}]", session.getId().toString());
		try {
			redisClient.delete(session.getId().toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("错误:删除seesion,id=[{}]", session.getId().toString());
		}
	}

	@Override
	public Collection<Session> getActiveSessions() {
		log.info("获取存活的session");
		return Collections.emptySet();
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		Session session = null;
		try {
			session = (Session) redisClient.get(sessionId.toString());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return session;
	}
}
