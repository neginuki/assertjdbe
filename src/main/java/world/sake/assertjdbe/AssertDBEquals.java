package world.sake.assertjdbe;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

/**
 * @author neginuki
 */
public class AssertDBEquals {

	private final List<DataSourceEntry> dataSources;

	public AssertDBEquals(DataSource... dataSources) {
		this.dataSources = Arrays.stream(dataSources).map(dataSource -> {
			return new DataSourceEntry(getDataSourceName(dataSource), dataSource);
		}).collect(Collectors.toList());
	}

	protected String getDataSourceName(DataSource dataSource) {
		try {
			return dataSource.getConnection().getCatalog();
		} catch (SQLException e) {
			throw new IllegalStateException(
					"データソースの名前解決が出来ませんでした。接続先の情報を確認してください。または getDataSourceName をオーバーライドしてカスタマイズしてください。　dataSource: "
							+ dataSource,
					e);
		}
	}

	private class DataSourceEntry {
		final String name;
		final DataSource dataSource;

		public DataSourceEntry(String name, DataSource dataSource) {
			this.name = name;
			this.dataSource = dataSource;
		}
	}
}
