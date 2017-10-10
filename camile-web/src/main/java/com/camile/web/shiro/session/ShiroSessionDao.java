package com.camile.web.shiro.session;

import com.camile.common.util.RedisUtil;
import com.camile.common.util.SerializableUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.*;

/**
 * Created by lizhihui on 01/10/2017.
 */
public class ShiroSessionDao extends CachingSessionDAO {

    // 会话key
    private final static String CAMILE_SHIRO_SESSION_ID = "camile-shiro-session-id";
    // 全局会话key
    private final static String CAMILE_SERVER_SESSION_ID = "camile-server-session-id";
    // 全局会话列表key
    private final static String CAMILE_SERVER_SESSION_IDS = "camile-server-session-ids";
    // code key
    private final static String CAMILE_SERVER_CODE = "camile-server-code";
    // 局部会话key
    private final static String CAMILE_CLIENT_SESSION_ID = "camile-client-session-id";
    // 单点同一个code所有局部会话key
    private final static String CAMILE_CLIENT_SESSION_IDS = "camile-client-session-ids";

    private static Logger _log = LoggerFactory.getLogger(ShiroSessionDao.class);

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);

        RedisUtil.set(CAMILE_SHIRO_SESSION_ID + "_" + sessionId, SerializableUtil.serialize(session), (int) session.getTimeout() / 1000);

        _log.debug("doCreate >>>>> sessionId={}", sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        String session = RedisUtil.get(CAMILE_SHIRO_SESSION_ID + "_" + sessionId);
        _log.debug("doReadSession >>>>> sessionId={}", sessionId);
        return SerializableUtil.deserialize(session);
    }

    @Override
    protected void doUpdate(Session session) {
        // 如果会话过期/停止 没必要再更新了
        if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) return;

        ShiroSession shiroSession = (ShiroSession) session;
        ShiroSession cachedSession = (ShiroSession) doReadSession(shiroSession.getId());

        if (null != cachedSession){
            shiroSession.setStatus(cachedSession.getStatus());
            shiroSession.setAttribute("FORCE_LOGOUT", cachedSession.getAttribute("FORCE_LOGOUT"));
        }

        RedisUtil.set(CAMILE_SHIRO_SESSION_ID + "_" + session.getId(), SerializableUtil.serialize(session), (int) (session.getTimeout() / 1000));
        _log.debug("doUpdate >>>>> sessionId={}", session.getId());
    }

    @Override
    protected void doDelete(Session session) {
        String sessionId = session.getId().toString();
        String code = RedisUtil.get(CAMILE_SHIRO_SESSION_ID + "_" + sessionId);
        RedisUtil.remove(CAMILE_SERVER_SESSION_ID + "_" + sessionId);
        RedisUtil.remove(CAMILE_SERVER_CODE + "_" + code);
        Jedis jedis = RedisUtil.getJedis();
        Set<String> clientSessionIds = jedis.smembers(CAMILE_CLIENT_SESSION_IDS + "_" + sessionId);

        for (String clientSessionId : clientSessionIds) {
            jedis.del(CAMILE_CLIENT_SESSION_ID + "_" + clientSessionId);
            jedis.srem(CAMILE_CLIENT_SESSION_IDS + "_" + code, clientSessionId);
        }
        jedis.close();
        _log.debug("当前code={}，对应的注册系统个数：{}个", code, jedis.scard(CAMILE_CLIENT_SESSION_IDS + "_" + code));
        RedisUtil.lrem(CAMILE_SERVER_SESSION_IDS, 1, sessionId);
        RedisUtil.remove(CAMILE_SHIRO_SESSION_ID + "_" + sessionId);
        _log.debug("doUpdate >>>>> sessionId={}", sessionId);
    }

    /**
     * 获取会话列表
     * @param offset
     * @param limit
     * @return
     */
    public Map<String, Object> getActiveSessions(int offset, int limit) {
        Map<String, Object> sessions = new HashMap<String, Object>();
        Jedis jedis = RedisUtil.getJedis();
        // 获取在线会话总数
        long total = jedis.llen(CAMILE_SERVER_SESSION_IDS);
        // 获取当前页会话详情
        List<String> ids = jedis.lrange(CAMILE_SERVER_SESSION_IDS, offset, (offset + limit - 1));
        List<Session> rows = new ArrayList<>();
        for (String id : ids) {
            String session = RedisUtil.get(CAMILE_SHIRO_SESSION_ID + "_" + id);
            // 过滤redis过期session
            if (null == session) {
                RedisUtil.lrem(CAMILE_SERVER_SESSION_IDS, 1, id);
                total = total - 1;
                continue;
            }
            rows.add(SerializableUtil.deserialize(session));
        }
        jedis.close();
        sessions.put("total", total);
        sessions.put("rows", rows);
        return sessions;
    }

    /**
     * 强制退出
     * @param ids
     * @return
     */
    public int forceout(String ids) {
        String[] sessionIds = ids.split(",");
        for (String sessionId : sessionIds) {
            // 会话增加强制退出属性标识，当此会话访问系统时，判断有该标识，则退出登录
            String session = RedisUtil.get(CAMILE_SHIRO_SESSION_ID + "_" + sessionId);
            ShiroSession shiroSession = (ShiroSession) SerializableUtil.deserialize(session);
            shiroSession.setStatus(ShiroSession.OnlineStatus.force_logout);
            shiroSession.setAttribute("FORCE_LOGOUT", "FORCE_LOGOUT");
            RedisUtil.set(CAMILE_SHIRO_SESSION_ID + "_" + sessionId, SerializableUtil.serialize(shiroSession), (int) shiroSession.getTimeout() / 1000);
        }
        return sessionIds.length;
    }

    /**
     * 更改在线状态
     *
     * @param sessionId
     * @param onlineStatus
     */
    public void updateStatus(Serializable sessionId, ShiroSession.OnlineStatus onlineStatus) {
        ShiroSession session = (ShiroSession) doReadSession(sessionId);

        if (null == session) return;

        session.setStatus(onlineStatus);
        RedisUtil.set(CAMILE_SHIRO_SESSION_ID + "_" + session.getId(), SerializableUtil.serialize(session), (int) session.getTimeout() / 1000);
    }
}
