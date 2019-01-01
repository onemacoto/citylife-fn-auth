package com.citylife.function.auth.provider;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.citylife.function.auth.token.PhoneAuthenticationToken;

public class PhoneAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	   private UserDetailsService userDetailsService;

	    @Override
	    protected void additionalAuthenticationChecks(UserDetails userDetails, Authentication authentication) throws AuthenticationException {

	        if(authentication.getCredentials() == null) {
	            this.logger.debug("Authentication failed: no credentials provided");
	            throw new BadCredentialsException(this.messages.getMessage("PhoneAuthenticationProvider.badCredentials", "Bad credentials"));
	        } else {
	            String presentedPassword = authentication.getCredentials().toString();

	            // TODO 验证码验证，调用公共服务查询 key 为authentication.getPrincipal()的value， 并判断其与验证码是否匹配
	            if(!"1000".equals(presentedPassword)){
	                this.logger.debug("Authentication failed: verifyCode does not match stored value");
	                throw new BadCredentialsException(this.messages.getMessage("PhoneAuthenticationProvider.badCredentials", "Bad verifyCode"));
	            }
	        }
	    }

	    @Override
	    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
	        PhoneAuthenticationToken result = new PhoneAuthenticationToken(principal, authentication.getCredentials(), authoritiesMapper.mapAuthorities(user.getAuthorities()));
	        result.setDetails(authentication.getDetails());
	        return result;
	    }

	    @Override
	    protected UserDetails retrieveUser(String phone, Authentication authentication) throws AuthenticationException {
			try {
				UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(phone);
				if (loadedUser == null) {
					throw new InternalAuthenticationServiceException(
							"UserDetailsService returned null, which is an interface contract violation");
				}
				return loadedUser;
			}
			catch (UsernameNotFoundException ex) {
				throw ex;
			}
			catch (InternalAuthenticationServiceException ex) {
				throw ex;
			}
			catch (Exception ex) {
				throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
			}
	    }

	    @Override
	    public boolean supports(Class<?> authentication) {
	        return PhoneAuthenticationToken.class.isAssignableFrom(authentication);
	    }


	    public UserDetailsService getUserDetailsService() {
	        return userDetailsService;
	    }

	    public void setUserDetailsService(UserDetailsService userDetailsService) {
	        this.userDetailsService = userDetailsService;
	    }
	
}
