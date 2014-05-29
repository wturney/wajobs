package com.wtl.wawork.core.persistence;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EntityManager;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.wtl.wawork.config.JpaTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext()
@ContextConfiguration(classes = JpaTestConfig.class)
public abstract class RepositoryTest {

    private static final Logger LOG = LoggerFactory.getLogger(RepositoryTest.class);

    @Autowired
    private IDatabaseConnection connection;

    @Autowired
    private EntityManager entityManager;

    @Rule
    public TestName currentTest = new TestName();

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Before
    public void setup() throws Exception {
        Method m = getClass().getMethod(currentTest.getMethodName());
        DataSet anno = m.getAnnotation(DataSet.class);

        FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
        flatXmlDataSetBuilder.setColumnSensing(true);

        InputStream in;
        List<IDataSet> dataSets = Lists.newArrayList();
        for (String resource : anno.names()) {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
            dataSets.add(flatXmlDataSetBuilder.build(in));
        }
        IDataSet merged = new CompositeDataSet(dataSets.toArray(new IDataSet[] {}));

        DatabaseOperation.CLEAN_INSERT.execute(connection, merged);
    }

}
