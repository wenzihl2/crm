package com.wzbuaa.crm.service.sso;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.wzbuaa.crm.domain.sso.user.S_userDomain;
import com.wzbuaa.crm.domain.sso.user.UserStatus;
import com.wzbuaa.crm.domain.sso.user.UserStatusHistory;
import com.wzbuaa.crm.service.BaseService;

import framework.entity.search.Searchable;

@Service
public class UserStatusHistoryService extends BaseService<UserStatusHistory, Long> {

    public void log(S_userDomain opUser, S_userDomain user, UserStatus newStatus, String reason) {
        UserStatusHistory history = new UserStatusHistory();
        history.setUser(user);
        history.setOpUser(opUser);
        history.setOpDate(new Date());
        history.setStatus(newStatus);
        history.setReason(reason);
        save(history);
    }

    public UserStatusHistory findLastHistory(final S_userDomain user) {
        Searchable searchable = Searchable.newSearchable()
                .addSearchParam("user_eq", user)
                .addSort(Sort.Direction.DESC, "opDate")
                .setPage(0, 1);

        Page<UserStatusHistory> page = baseRepository.findAll(searchable);

        if (page.hasContent()) {
            return page.getContent().get(0);
        }
        return null;
    }

    public String getLastReason(S_userDomain user) {
        UserStatusHistory history = findLastHistory(user);
        if (history == null) {
            return "";
        }
        return history.getReason();
    }
}
