package com.citylife.function.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.citylife.common.component.JWTHelper;
import com.citylife.common.model.IHeaderUser;
import com.citylife.common.model.ResponseVO;
import com.citylife.common.model.ResultEntity;
import com.citylife.common.model.UserValueObject;
import com.citylife.common.model.builder.RequestVOBuilder;
import com.citylife.function.api.user.client.IBaseRoleClient;
import com.citylife.function.api.user.client.IBaseUserClient;
import com.citylife.function.api.user.client.entity.BaseRole;
import com.citylife.function.api.user.client.entity.BaseUser;
import com.citylife.function.auth.userdetails.AppUserDetail;

public abstract class BaseUserDetailService implements UserDetailsService {

	@Autowired
	protected IBaseUserClient baseUserClient;

	@Autowired
	protected JWTHelper jwtHelper;

	@Autowired
	private IBaseRoleClient baseRoleClient;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		BaseUser baseUser = getUser(username);

		ResultEntity<ResponseVO<List<BaseRole>>> result = baseRoleClient
				.getRoleByUserId(RequestVOBuilder.build(baseUser.getId()));

//		if (result.hasError() || CollectionUtils.isEmpty(result.getBody().getData())) {
//			
//		}
//		
//		 List<BaseRole> roles;

		List<GrantedAuthority> authorities = null;

		User user = new User(baseUser.getUserName(), baseUser.getPassword(), isActive(baseUser.getActive()), true, true,
				true, authorities);

		// TODO Auto-generated method stub
		IHeaderUser headerUser =  UserValueObject.empty();
		headerUser.setUserId(baseUser.getId());
		return new AppUserDetail(headerUser, user);
	}

	private boolean isActive(int active) {
		return active == 1 ? true : false;
	}

	protected abstract BaseUser getUser(String var1);

}
