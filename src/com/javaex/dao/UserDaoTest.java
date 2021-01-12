package com.javaex.dao;

import com.javaex.vo.UserVo;

public class UserDaoTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserDao userDao = new UserDao();
		UserVo userVo = userDao.getUser("aaa", "1234");
		
		System.out.println(userVo);
	}

}
