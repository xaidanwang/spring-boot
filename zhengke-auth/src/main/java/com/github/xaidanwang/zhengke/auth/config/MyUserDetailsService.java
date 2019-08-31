package com.github.xaidanwang.zhengke.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author wang yi fei
 * @date 2019/8/31 19:16
 */
@Component
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String password = passwordEncoder.encode("123");
		User user = new User(username, password,true,true,true,true,
				AuthorityUtils.commaSeparatedStringToAuthorityList("/**"));
		return user;
	}
}
