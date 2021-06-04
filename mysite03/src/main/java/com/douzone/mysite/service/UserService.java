package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.UserRepository;
import com.douzone.mysite.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public boolean join(UserVo vo) {
		boolean result = userRepository.insert(vo);
		return result;
	}
	
	public UserVo getUser(String email, String password) {
		UserVo result = userRepository.findByEmailAndPassword(email, password);
		return result;
	}

	public UserVo getUser(Long no) {
		UserVo result = userRepository.findByNo(no);
		return result;
		
	}

	public boolean updateUser(UserVo userVo) {
		int result = userRepository.update(userVo);
		return result==1;
	}

}
