package com.example.batchprocessing;


import java.util.List;
import java.util.stream.Stream;

import org.seasar.doma.*;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.BatchResult;
import org.seasar.doma.jdbc.Result;
import org.seasar.doma.jdbc.SelectOptions;
import org.seasar.doma.message.Message;
import org.springframework.transaction.annotation.Transactional;


@Dao
@ConfigAutowireable
@Transactional
public interface PersonDao {

    @BatchInsert
    BatchResult<Person> insert(List<Person> bonuses);
    
    
    @Select
    @Suppress(messages = { Message.DOMA4274 })
    Stream<Person> selectPerson();
	
}
