package database.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class MySQL {
	public Connection c;
	public Statement s;
	public ResultSet rs;
	public PreparedStatement ps;
	
	public MySQL(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/abm_pharma","root","241989");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void close() {
		try {
			if(s!=null && !s.isClosed()) {
				s.close();
			}
			
			if(rs!=null && !rs.isClosed()) {
				rs.close();
			}

			if(ps!=null && !ps.isClosed()) {
				ps.close();
			}
			
			if(c!=null && !c.isClosed()) {
				c.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void execute() {
		try {
			onExecute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		close();
	}
	public abstract void onExecute() throws Exception;
	
	/**
	 * count(*) from table where condition
	 * 
	 * @param table - table name.
	 * @param condition - conditional query, requires the term "where".
	 */
	public static int count(String table, String condition) {
		final String query = "select count(*) from " + table + " " + condition + " ;";
		final int length[] = {-1};
		
		new MySQL() {
			@Override
			public void onExecute() throws Exception {
				s = c.createStatement();
				rs = s.executeQuery(query);
				
				while(rs.next()) {
					length[0] = rs.getInt(1);
				}
			}
		}.execute();
		
		return length[0];
	}
	/**
	 * select columns[0], columns[1], columns[2]... columns[n] from table where condition;
	 * 
	 * @param columns - name of selected columns or "*" for select all.
	 * @param table - table name.
	 * @param condition - conditional query, requires the term "where".
	 */
	public static Object[][] select(String[] columns, String table, String condition){
		final int
		result_count = count(table, condition),
		column_count = columns.length;

		final Object result[][] = new Object[result_count][column_count];
		
		new MySQL() {
			private int i,ii;
			private final String query;
			{
				String query = "select ";
				for(i=0; i<column_count-1; i++) {
					query += columns[i] + ", ";
				}
				query += columns[i] + " from " + table + " " + condition + " ;";
				this.query = query;
			}
			@Override
			public void onExecute() throws Exception {				
				s = c.createStatement();
				rs = s.executeQuery(query);
				
				i=0;
				while(rs.next()) {
					for(ii=0; ii<column_count; ii++) {
						result[i][ii] = rs.getObject(ii + 1);
					}
					i++;
				}
			}
		}.execute();
		
		return result;
	}
	/** 
	 * gets the next unique ID of the table.
	 * 
	 * @param table - table name
	 */
	public static int nextUID(String table) {
		final int id[] = {-1};
		
		new MySQL() {
			@Override
			public void onExecute() throws Exception {
				s = c.createStatement();
				rs = s.executeQuery("select next_id from uid where table_name='" + table + "' ;");
				while(rs.next()) {
					id[0] = rs.getInt(1);
				}
				ps = c.prepareStatement("insert into uid(next_id) values(?) where table_name='" + table + "' ;");
				ps.setInt(1, id[0] + 1);
				ps.execute();
			}
		};
		
		return id[0];
	}
	/** 
	 * insert into table (columns[0], columns[1], columns[2]... columns[n]) values(values[0], values[1], values[2]... values[n]);
	 * 
	 * @param table - table name.
	 * @param columns - selected columns.
	 * @param values - respected column values.
	 */
	public static void insert(String table, String columns[], Object values[]) {		
		new MySQL() {
			@Override
			public void onExecute() throws Exception {
				String query = "insert into " + table + " ";
				int i;
				
				if(columns.length > 0) {
					query += "(";
					for(i=0; i<columns.length-1; i++) {
						query += columns[i] + ", ";
					}
					query += columns[i] + ") ";
				}
				
				query += " values(";
				
				for(i=0; i<values.length-1; i++) {
					query += "?, ";
				}
				query += "?);";
				
				ps = c.prepareStatement(query);
				for(i=0; i<values.length; i++) {
					ps.setObject(i+1, values[i]);
				}
				ps.execute();
			}
		};
	}
}
