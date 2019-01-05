package com.citylife.function.auth.service;

import com.citylife.common.model.ResponseVO;
import com.citylife.common.model.ResultEntity;
import com.citylife.common.model.builder.RequestVOBuilder;
import com.citylife.function.api.user.client.entity.BaseUser;

public class PhoneUserDetailService extends BaseUserDetailService {

	@Override
	protected BaseUser getUser(String phone) {
		ResultEntity<ResponseVO<BaseUser>> result = baseUserClient.getUserByPhone(RequestVOBuilder.build(phone), "v1",
				jwtHelper.createAdminToken());

		return result.getBody().getData();
	}

}
