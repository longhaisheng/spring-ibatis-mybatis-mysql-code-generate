package com.lhs.test;

import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.weibo.dao.UserDAO;
import com.weibo.domain.UserDO;
import com.weibo.exception.DAOException;

public class TestDAO {

	public static void main(String[] args) throws DAOException {
		String projectPath = System.getProperty("user.dir");
		ApplicationContext ctx = new FileSystemXmlApplicationContext(projectPath
				+ "\\bin\\com\\lhs\\test\\ibatis-application.xml");
		// ApplicationContext ctx = new
		// FileSystemXmlApplicationContext(projectPath+"\\bin\\com\\lhs\\test\\mybatis-application.xml");
		System.out.println(ctx.getBean("userDAO"));
		UserDAO userDAO = (UserDAO) ctx.getBean("userDAO");

		/******* insert test ********/
		UserDO user = new UserDO();
		user.setUserName("lhws");
		user.setPassword("pwd");
		user.setGmtCreate(new Date());
		user.setGmtModified(new Date());
		long insert_id = userDAO.insert(user);
		System.out.println("insert_id:" + insert_id);

		/******* selectDynamic test ********/
		UserDO user2 = new UserDO();
		user2.setUserName("lhws");
		List<UserDO> list = userDAO.selectDynamic(user2);
		System.out.println("selectDynamic:" + list.size());

		/******* selectById test ********/
		Long userId = 2L;
		UserDO user3 = userDAO.selectById(userId);
		if (user3 != null) {
			System.out.println("selectById:" + user3.getPassword());
		}

		/******* update test ********/
		user3.setUserName("中国人");
		int update_count = userDAO.update(user3);
		System.out.println("update all:" + update_count);

		/******* updateDynamic test ********/
		UserDO user4 = new UserDO();
		user4.setId(4L);
		user4.setUserName("username4");
		user4.setGmtModified(new Date());
		System.out.println("updateDynamic:" + userDAO.updateDynamic(user4));

		/******* selectCountDynamic test ********/
		UserDO lhUserDO = new UserDO();
		lhUserDO.setUserName("lhws");
		long count = userDAO.selectCountDynamic(lhUserDO);
		System.out.println("selectCountDynamic:" + count);

		/******* selectDynamicPageQuery test ********/
		UserDO user10 = new UserDO();
		user10.setStartPage(1);
		user10.setUserName("lhws");
		List<UserDO> list2 = userDAO.selectDynamicPageQuery(user10);
		System.out.println("selectDynamicPageQuery:" + list2.size());

		/******* deleteById test ********/
		System.out.println("deleteById:" + userDAO.deleteById(insert_id));

		System.out.println(System.getProperty("path.separator"));
	}

}
