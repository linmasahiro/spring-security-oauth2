package main.java.db;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;

/**
 * PostgreSQL用のDatabaseIdを取得するためのクラス。
 *
 *
 *
 */
public class PostgreSQLDatabaseIdProvider implements DatabaseIdProvider {

	/**
	 * デフォルトコンストラクタ。
	 */
	public PostgreSQLDatabaseIdProvider() {
	}

	/**
	 * プロパティ値は使用しないため、何も行わない。
	 *
	 * @param p
	 *            プロパティ
	 */
	public void setProperties(Properties p) {
		// do nothing
	}

	/**
	 * 常にPostgreSQL用のDatabaseId(PostgreSQL)を取得する。
	 *
	 * @param dataSource
	 *            {@link DataSource}
	 * @return PostgreSQL用のDatabaseId(PostgreSQL)
	 */
	public String getDatabaseId(DataSource dataSource) {
		return "PostgreSQL";
	}

}
