package com.beemall.user.service.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.beemall.entity.MQMessage;
import com.beemall.pojo.TbUserExample;
import com.beemall.user.service.UserService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.beemall.entity.ResponseData;
import com.beemall.entity.ResponseDataUtil;
import com.beemall.mapper.TbUserMapper;
import com.beemall.pojo.TbUser;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.Destination;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	@Value("${aliSms.regTemplateCode}")
	private String regTemplateCode;

	@Value("${aliSms.signName}")
	private String signName;

	@Value("${spring.activemq.queue}")
	private String queue;

	@Autowired
	private JmsMessagingTemplate jmsTemplate;
	/**
	 * 查询全部
	 */
	@Override
	public List<TbUser> findAll() {
		return userMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public ResponseData findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		PageInfo<TbUser> pageInfo=   new PageInfo<>( userMapper.selectByExample(null));
		return ResponseDataUtil.buildSuccess(pageInfo);
	}

	/**
	 * 增加
	 */
	@Override
	public ResponseData add(TbUser user) {
		user.setCreated(new Date());
		user.setUpdated(new Date());
		user.setSourceType("1");//注册来源
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));//密码加密
		userMapper.insert(user);	
		return ResponseDataUtil.buildSuccess();		
	}

	
	/**
	 * 修改
	 */
	@Override
	public ResponseData update(TbUser user){
		userMapper.updateByPrimaryKey(user);
		return ResponseDataUtil.buildSuccess();
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbUser findOne(Long id){
		return userMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public ResponseData delete(Long[] ids) {
		for(Long id:ids){
			userMapper.deleteByPrimaryKey(id);
		}	
		return ResponseDataUtil.buildSuccess();
	}
	
	
		@Override
	public ResponseData findPageByExample(TbUser user, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbUserExample example=new TbUserExample();
		TbUserExample.Criteria criteria = example.createCriteria();
		
		if(user!=null){			
						if(user.getUsername()!=null && user.getUsername().length()>0){
				criteria.andUsernameLike("%"+user.getUsername()+"%");
			}
			if(user.getPassword()!=null && user.getPassword().length()>0){
				criteria.andPasswordLike("%"+user.getPassword()+"%");
			}
			if(user.getPhone()!=null && user.getPhone().length()>0){
				criteria.andPhoneLike("%"+user.getPhone()+"%");
			}
			if(user.getEmail()!=null && user.getEmail().length()>0){
				criteria.andEmailLike("%"+user.getEmail()+"%");
			}
			if(user.getSourceType()!=null && user.getSourceType().length()>0){
				criteria.andSourceTypeLike("%"+user.getSourceType()+"%");
			}
			if(user.getNickName()!=null && user.getNickName().length()>0){
				criteria.andNickNameLike("%"+user.getNickName()+"%");
			}
			if(user.getName()!=null && user.getName().length()>0){
				criteria.andNameLike("%"+user.getName()+"%");
			}
			if(user.getStatus()!=null && user.getStatus().length()>0){
				criteria.andStatusLike("%"+user.getStatus()+"%");
			}
			if(user.getHeadPic()!=null && user.getHeadPic().length()>0){
				criteria.andHeadPicLike("%"+user.getHeadPic()+"%");
			}
			if(user.getQq()!=null && user.getQq().length()>0){
				criteria.andQqLike("%"+user.getQq()+"%");
			}
			if(user.getIsMobileCheck()!=null && user.getIsMobileCheck().length()>0){
				criteria.andIsMobileCheckLike("%"+user.getIsMobileCheck()+"%");
			}
			if(user.getIsEmailCheck()!=null && user.getIsEmailCheck().length()>0){
				criteria.andIsEmailCheckLike("%"+user.getIsEmailCheck()+"%");
			}
			if(user.getSex()!=null && user.getSex().length()>0){
				criteria.andSexLike("%"+user.getSex()+"%");
			}
	
		}
		
		PageInfo<TbUser> pageInfo=   new PageInfo<>( userMapper.selectByExample(example));	
		return ResponseDataUtil.buildSuccess(pageInfo);
	}

	@Override
	public void createSmsCode(String phone) {
		//创建6位随机数
		String code = (long)(Math.random() * 1000000) + "";
		//将验证码存入redis中
		redisTemplate.boundHashOps("smsCode").put(phone, code);
		//消息推送给短信服务
		Map<String, String> smsMap = new HashMap<>();
		smsMap.put("signName",signName);
		smsMap.put("regTemplateCode", regTemplateCode);
		smsMap.put("mobile", phone);
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("code", code);
		smsMap.put("param", JSON.toJSONString(paramMap));
		smsMap.put("method","sendRegisterMsg");
		Destination destination = new ActiveMQQueue(queue);
		jmsTemplate.convertAndSend(destination, smsMap);
	}

	@Override
	public boolean checkSmsCode(String phone, String code) {
		String codeInRedis = (String) redisTemplate.boundHashOps("smsCode").get(phone);
		if(codeInRedis == null){
			return false;
		}
		if(!codeInRedis.equals(code)){
			return false;
		}
		return true;
	}

}
