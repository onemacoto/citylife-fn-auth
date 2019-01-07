package com.citylife.function.auth.service;

import com.citylife.common.model.ResponseVO;
import com.citylife.common.model.ResultEntity;
import com.citylife.common.model.builder.RequestVOBuilder;
import com.citylife.function.api.user.client.entity.BaseUser;
import com.citylife.function.auth.integration.AuthRequestVO;
import com.citylife.function.auth.integration.IntegrationAuthenticationContext;

public class PhoneUserDetailService extends BaseUserDetailService {

  @Override
  protected BaseUser getUser(String username) {
    AuthRequestVO authParam = IntegrationAuthenticationContext.get().getAuthParameters();
    ResultEntity<ResponseVO<BaseUser>> result = baseUserClient.getUserByPhone(RequestVOBuilder.build(authParam.getData().getPhoneNumber(), authParam), "v1", jwtHelper.createAdminToken());
    return result.getBody().getData();
  }
}
