package com.example.batchprocessing;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;


// usable for same case
//@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
//        DirtiesContextTestExecutionListener.class})
//// This is to avoid clashing of several JobRepository instances using the same data source for several test classes
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
//@SpringBootTest(classes = BatchProcessingApplication.class)



@ActiveProfiles("test")
@SpringBatchTest
@SpringBootTest                            // This is required to be able to used spring boot features such as profiles
public class PersonDaoTest {
	
	@Autowired
	PersonDao p;
	
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Test
    void ssss() throws Exception {
	
    	Person pp = new Person("aaa", "bbb");
    	List<Person> lp = List.of(pp);
    	
    	p.insert(lp);
    	
    	var m = jdbcTemplate.queryForMap("select * from people where first_name='aaa'");
    	
    	System.out.println(m.size());
    	
    	
    	
    	var m1 = jdbcTemplate.queryForList("select * from people");
    	
    	
    	System.out.println(m1.size());
    	
    	
    	Stream<Person>  sp= p.selectPerson();
    	
    	//System.out.println(sp.toList().size());
    	
    	sp.forEach(p-> {
    		System.out.println(p.firstName() +", "+ p.lastName());
    	});
    	
    	
    	
    }

}

