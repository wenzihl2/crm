package com.wzbuaa.crm.service.sso;

import java.util.Date;
import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.wzbuaa.crm.bean.Setting;
import com.wzbuaa.crm.domain.sso.user.S_userDomain;
import com.wzbuaa.crm.domain.sso.user.UserStatus;
import com.wzbuaa.crm.exception.user.UserBlockedException;
import com.wzbuaa.crm.exception.user.UserNotExistsException;
import com.wzbuaa.crm.exception.user.UserPasswordNotMatchException;
import com.wzbuaa.crm.repository.sso.UserRepository;
import com.wzbuaa.crm.service.BaseService;
import com.wzbuaa.crm.util.SettingUtils;
import com.wzbuaa.crm.util.UserLogUtils;

/**
 * Service实现类 - 管理员
 */
@Service
public class S_userService extends BaseService<S_userDomain, Long> {

	public static final int HASH_INTERATIONS = 1024;
	@Resource public PasswordService passwordService;
	@Resource public UserStatusHistoryService userStatusHistoryService;
	
	private UserRepository getUserRepository() {
        return (UserRepository) baseRepository;
    }
	
	public boolean isExistByUsername(String username) {
		S_userDomain admin = findByUsername(username);
		if (admin != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public S_userDomain findByUsername(String username) {
		return getUserRepository().findByUsername(username);
	}

    public S_userDomain save(S_userDomain user) {
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getUsername(), user.getPassword(), user.getSalt()));
        super.save(user);
        return user;
    }
	
	public S_userDomain changePassword(S_userDomain user, String newPassword) {
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getUsername(), newPassword, user.getSalt()));
        super.update(user);
        return user;
    }
	
	public S_userDomain changeStatus(S_userDomain opUser, S_userDomain user, UserStatus newStatus, String reason) {
        user.setStatus(newStatus);
        super.update(user);
        userStatusHistoryService.log(opUser, user, newStatus, reason);
        return user;
    }
	
	public S_userDomain login(String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            UserLogUtils.log(username, "loginError", "username is empty");
            throw new UserNotExistsException();
        }
        //密码如果不在指定范围内 肯定错误
        if (password.length() < S_userDomain.PASSWORD_MIN_LENGTH || password.length() > S_userDomain.PASSWORD_MAX_LENGTH) {
            UserLogUtils.log(username, "loginError", "password length error! password is between {} and {}",
                    S_userDomain.PASSWORD_MIN_LENGTH, S_userDomain.PASSWORD_MAX_LENGTH);
            throw new UserPasswordNotMatchException();
        }

        S_userDomain user = null;

        //此处需要走代理对象，目的是能走缓存切面
        S_userService proxyUserService = (S_userService) AopContext.currentProxy();
        if (maybeUsername(username)) {
            user = proxyUserService.findByUsername(username);
        }

        if (user == null) {
            UserLogUtils.log(username, "loginError", "user is not exists!");
            throw new UserNotExistsException();
        }

        passwordService.validate(user, password);
        

        // 解除管理员账户锁定
		Setting systemConfig = SettingUtils.get();
		if (user.getIsAccountLocked() == true) {
			if (systemConfig.getIsLoginFailureLock() == true) {
				int loginFailureLockTime = systemConfig.getLoginFailureLockTime();
				if (loginFailureLockTime != 0) {
					Date lockedDate = user.getLockedDate();
					Date nonLockedTime = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
					Date now = new Date();
					if (now.after(nonLockedTime)) {
						user.setLoginFailureCount(0);
						user.setIsAccountLocked(false);
						user.setLockedDate(null);
						this.update(user);
					}
				}
			} else {
				user.setLoginFailureCount(0);
				user.setIsAccountLocked(false);
				user.setLockedDate(null);
				this.update(user);
			}
		}

        if (user.getStatus() == UserStatus.blocked) {
            UserLogUtils.log(username, "loginError", "user is blocked!");
            throw new UserBlockedException(userStatusHistoryService.getLastReason(user));
        }

        UserLogUtils.log(username, "loginSuccess", "");
        return user;
    }
	
	private boolean maybeUsername(String username) {
        if (!username.matches(S_userDomain.USERNAME_PATTERN)) {
            return false;
        }
        //如果用户名不在指定范围内也是错误的
        if (username.length() < S_userDomain.USERNAME_MIN_LENGTH || username.length() > S_userDomain.USERNAME_MAX_LENGTH) {
            return false;
        }

        return true;
    }
	
}