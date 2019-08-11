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
					"could not resolve the connect identifier specified. DataSource: " + dataSource, e);
		}
	}

	public void assertEquals() {

	}

	protected ExpectedWorkbook createExpectedWorkbook() {
		return new ExpectedWorkbook();
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("dataSources");
		dataSources.forEach(ds -> {
			buf.append("\n" + ds);
		});

		return buf.toString();
	}

	private class DataSourceEntry {
		final String name;
		final DataSource dataSource;

		public DataSourceEntry(String name, DataSource dataSource) {
			this.name = name;
			this.dataSource = dataSource;
		}

		@Override
		public String toString() {
			return "name: " + name + ", dataSource: " + dataSource;
		}
	}
}
