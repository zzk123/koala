package org.openkoala.application.impl;

import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.openkoala.auth.application.ResourceTypeApplication;
import org.openkoala.auth.application.vo.ResourceTypeVO;
import org.openkoala.exception.extend.ApplicationException;
import org.openkoala.koala.util.KoalaBaseSpringTestCase;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.dayatang.querychannel.support.Page;

/**
 * ResourceTypeApplicationImpl测试
 * @author zyb <a href="mailto:zhuyuanbiao2013@gmail.com">zhuyuanbiao2013@gmail.com</a>
 * @since Aug 14, 2013 2:39:04 PM
 */
@TransactionConfiguration(transactionManager = "transactionManager_security", defaultRollback = true)
public class ResourceTypeApplicationImplTest extends KoalaBaseSpringTestCase {
	
	@Inject
	private ResourceTypeApplication resourceTypeApplication;
	
	private ResourceTypeVO resourceTypeVO;
	
	@Before
	public void setUp() {
		resourceTypeVO = new ResourceTypeVO();
		resourceTypeVO.setName("test");
		resourceTypeVO.setText("test");
		resourceTypeApplication.save(resourceTypeVO);
		assertNotNull(resourceTypeVO.getId());
	}

	@Test(expected = ApplicationException.class)
	public void testIsExist() {
		resourceTypeApplication.isExist(resourceTypeVO);
	}

	@Test
	public void testDeleteResourceTypeVO() {
		resourceTypeApplication.delete(resourceTypeVO);
	}

	@Test
	public void testUpdate() {
		resourceTypeVO.setName("update");
		resourceTypeApplication.update(resourceTypeVO);
	}

	@Test
	public void testPageQuery() {
		Page<ResourceTypeVO> page = resourceTypeApplication.pageQuery(1, 10);
		assertNotNull(page);
		assertTrue(page.getPageSize() == 10);
		assertTrue(page.getResult().size() > 0);
	}

	@Test
	public void testFindResourceType() {
		List<ResourceTypeVO> results = resourceTypeApplication.findResourceType();
		assertNotNull(results);
		assertTrue(results.size() > 0);
	}

	@Test
	public void testFindMenuType() {
		List<ResourceTypeVO> results = resourceTypeApplication.findMenuType();
		assertNotNull(results);
		assertTrue(results.size() > 0);
	}

}