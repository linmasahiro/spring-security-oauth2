package main.java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import main.java.db.UserDetailsDAO;
import main.java.db.UserDetailsDTO;

@Service("MyUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDetailsDAO userDetailsDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDetailsDTO userInfo = userDetailsDAO.select(username);
		if (userInfo != null) {
			return new User(username, "123456", AuthorityUtils.createAuthorityList("ROLE_CLIENT"));

		} else {
			throw new UsernameNotFoundException("not found : " + username);

		}
	}
}