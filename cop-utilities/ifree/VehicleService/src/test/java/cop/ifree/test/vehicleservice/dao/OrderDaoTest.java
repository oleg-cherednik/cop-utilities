package cop.ifree.test.vehicleservice.dao;

import cop.ifree.test.vehicleservice.data.Order;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.UUID;

/**
 * @author Oleg Cherednik
 * @since 26.05.2013
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ContextConfiguration(locations = {"/web/WEB-INF/applicationContext.xml", "/web/WEB-INF/database-config.xml"})
//@TransactionConfiguration(defaultRollback = true)
//@ContextConfiguration(locations = "classpath:context.xml")
//@Transactional

public class OrderDaoTest {
	@Resource(name = "orderDAO")
	private OrderDAO daoOrder;

	//	@Autowired
	//	private PlatformTransactionManager transactionManager;

	@After
	public void after() {
		//		dataDao.checkpoint();
	}

	@Test
	public void simpleTest() throws SQLException, NamingException {
		String text = UUID.randomUUID().toString();

		Order order = daoOrder.createOrder(1, 1);

		int a = 0;
		//		dataDao.save(new Data(text));
		//		Collection<Data> result = dataDao.find(text);
		//		Assert.assertEquals(1, result.size());
		//		Assert.assertEquals(text, result.iterator().next().getText());
	}

	//	@Test
	//	public void comlexTest() {
	//		TransactionStatus transaction = transactionManager
	//				.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
	//		String text = UUID.randomUUID().toString();
	//		dataDao.save(new Data(text));
	//		transactionManager.commit(transaction);
	//		transaction = transactionManager
	//				.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
	//		Collection<Data> result = dataDao.find(text);
	//		Assert.assertEquals(1, result.size());
	//		Assert.assertEquals(text, result.iterator().next().getText());
	//		transactionManager.rollback(transaction);
}