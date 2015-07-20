package com.cai310.lottery.dao.lottery;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProcedureDao {

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public String generateId(final String key) {
		return (String) jdbcTemplate.execute(new CallableStatementCreator() {
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement cs = con.prepareCall("{ ? = call GenerateId (?) }");
				cs.registerOutParameter(1, java.sql.Types.VARCHAR);
				cs.setString(2, key);
				return cs;
			}
		}, new CallableStatementCallback() {
			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				cs.execute();
				String rs = cs.getString(1);
				if (StringUtils.isBlank(rs))
					throw new SQLException("存储过程返回值为空");
				return rs;
			}
		});
	}
}
