package com.toby.spring.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class HelloRepositoryJdbc implements HelloRepository {

	private final JdbcTemplate jdbcTemplate;

	public HelloRepositoryJdbc(final JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Hello findHello(final String name) {
		try {
			return jdbcTemplate.queryForObject("select * from hello where name = '" + name + "'",
				(rs, rowNum) -> new Hello(
					rs.getString("name"),
					rs.getInt("count")
				));
		} catch (final EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void increaseCount(final String name) {
		final Hello hello = findHello(name);
		if (hello == null) {
			jdbcTemplate.update("insert into hello values(?, ?)", name, 1);
		} else {
			jdbcTemplate.update("update hello set count = ? where name = ?", hello.getCount() + 1, name);
		}
	}

}
