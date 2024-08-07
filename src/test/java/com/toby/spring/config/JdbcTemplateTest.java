package com.toby.spring.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@JdbcTest
public class JdbcTemplateTest {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	void insertAndQuery() {
		jdbcTemplate.update("insert into hello values(?, ?)", "test1", 1);
		jdbcTemplate.update("insert into hello values(?, ?)", "test2", 1);

		Long count = jdbcTemplate.queryForObject("select count(*) from hello", Long.class);
		Assertions.assertThat(count).isEqualTo(2);
	}

}
