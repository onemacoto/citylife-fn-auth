package com.citylife.function.auth.service;

import com.citylife.common.model.ResponseVO;
import com.citylife.common.model.ResultEntity;
import com.citylife.common.model.builder.RequestVOBuilder;
import com.citylife.function.api.user.client.entity.BaseUser;

public class UsernameUserDetailService extends BaseUserDetailService {

	@Override
	protected BaseUser getUser(String username) {
    ResultEntity<ResponseVO<BaseUser>> result = baseUserClient.getUserByUserName(RequestVOBuilder.build(username), "v1",
        jwtHelper.createAdminToken());
    return result.getBody().getData();
	}

}
