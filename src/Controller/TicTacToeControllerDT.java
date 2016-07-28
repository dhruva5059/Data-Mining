package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import Model.TicTacToe;

public class TicTacToeControllerDT {
	static final String databaseName = "data_mining";
	static final String datasetTableName = "tic_tac_toe";
	static final String tempTableName = "tempTable";
	static final String labelPositive = "positive";
	static final String labelNegative = "negative";
	static final String rulesTableName = "rules";
	static final Integer totalRows = 958;
	static Connection conn;
	
	public Boolean setupDBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/" + databaseName + "?" + "user=dhruva&password=dhruva&useSSL=false");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//Returns a list of Columns in given table
	public ArrayList<String> getDescOfTable(String tableName) {
		Statement s = null;
		String query = "DESC " + tableName;
		ResultSet rs;
		ArrayList<String> columnNames = new ArrayList<String>();
		// describe table
		try {
			s = conn.createStatement();
			s.execute(query);
			rs = s.getResultSet();
			while (rs != null && rs.next()) {
				columnNames.add(rs.getString(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return columnNames;
	}
	
	// Checks if table belongs to single class or not	Return False if not else returns the label
	public String ifBelongsToSingleClass(String tableName) {
		Statement s = null;
		String query = "SELECT DISTINCT COUNT(*),Label FROM " + tableName + " GROUP BY Label";
		ResultSet rs;
		String str = "False";
		int count = 0;
		try {
			s = conn.createStatement();
			s.execute(query);
			rs = s.getResultSet();
			while (rs != null && rs.next()) {
				count++;
				if (count > 1) {
					str = "False";
					break;
				}
				str = rs.getString(2);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public double I(double p, double n){
		return -((p / ((p + n) * Math.log(2))) * Math.log(p / (p + n)))-((n / ((p + n) * Math.log(2))) * Math.log(n / (p + n)));
	}

	public int createTempTable(String str, ArrayList<String> columnNames){
		Statement s = null;
		String query = "DROP TABLE " + tempTableName;
		ResultSet rs;
		try {
			s = conn.createStatement();
			s.execute(query);
		} catch (SQLException e) {
		}
		StringTokenizer st = new StringTokenizer(str, ", ");
		ArrayList<String> conditions = new ArrayList<String>();
	    while (st.hasMoreTokens()) {
	    	 String column = st.nextToken();
	    	 conditions.add(column + " = '" + st.nextToken() +"'");
	    	 columnNames.remove(column);
	    }
	    query = "CREATE TABLE " + tempTableName + " AS SELECT ";
	    for(int i=0; i<columnNames.size()-1; i++){
	    	query += columnNames.get(i) + ",";
	    }
	    query += columnNames.get(columnNames.size()-1);
	    query += " FROM " + datasetTableName + " WHERE ";
	    for(int i=0; i<conditions.size()-1; i++){
	    	query += conditions.get(i) + " AND ";
	    }
	    query += " " + conditions.get(conditions.size()-1);
	    try {
			s.execute(query);
			if(s.getUpdateCount()<1){
				return 0;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	    
	    return 1;
	}
	
	public void generateDecisionTree(){
		
		//drop and create again rules table
		setupRulesTable();
		
		ArrayList<String> queue = new ArrayList<String>(); 
		ArrayList<String> rules = new ArrayList<String>();
		ArrayList<String> columnNames = new ArrayList<String>();
		ArrayList<String> columnValues = new ArrayList<String>();
		columnValues.add("x");
		columnValues.add("o");
		columnValues.add("b");
		
		queue.add("-1");
		for(int i=0; i<queue.size(); i++){
			columnNames = getDescOfTable(datasetTableName);
			if((queue.get(i).equals("-1"))){
				if(createTempTable("1,1",columnNames) == 0){
					continue;
				}
			}
			else {
				if(createTempTable(queue.get(i), columnNames) == 0){
					continue;
				}
			}
			//Temporary table created successfully
			String ifSingleClass = ifBelongsToSingleClass(tempTableName);
			if(!ifSingleClass.equals("False")){
				rules.add(queue.get(i)+",Label,"+ifSingleClass);
				insertRule(queue.get(i), ifSingleClass);
				continue;
			}
			else {
				ArrayList<Integer> pandn = getPandN(tempTableName,"1","1");
				double valueI = I(pandn.get(0),pandn.get(1));
				ArrayList<Double> gain = new ArrayList<Double>();
				columnNames = getDescOfTable(tempTableName);
				for(int j=0; j<columnNames.size()-1; j++){
					double E = 0;
					for(int k=0; k<columnValues.size(); k++) {
						ArrayList<Integer> subpandn = getPandN(tempTableName, columnNames.get(j), columnValues.get(k));
						E += (subpandn.get(0) + subpandn.get(1)) * I(subpandn.get(0),subpandn.get(1)) / totalRows;
					}
					gain.add(valueI - E);
				}
				
				//Not usual
				if(gain.size()<1){
					System.out.println("Line: 174 Abnormal Case Occurred");
					continue;
				}
				//Find maximum gain
				double max = gain.get(0);
				int maxIndex = 0;
				for(int j=1; j<gain.size(); j++){
					if(gain.get(j) > max){
						max = gain.get(j); 
						maxIndex = j;
					}
				}
				String queueStr = "";
				if(queue.get(i).equals("-1")){
					queueStr = columnNames.get(maxIndex);
				}
				else {
					queueStr = queue.get(i) + "," + columnNames.get(maxIndex);
				}
				for(int j=0;j<columnValues.size();j++){
					
					
					queue.add(queueStr+","+columnValues.get(j));
				}
			}
		}
		return;
	}
	
	private void setupRulesTable() {
			Statement s = null;
			String query = "DROP TABLE "+rulesTableName;
			try {
				s = conn.createStatement();
				s.execute(query);
				query = "CREATE TABLE "+rulesTableName+" (id int auto_increment primary key, path varchar(200), Label varchar(20))";
				s.execute(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
	}

	void insertRule(String string, String ifSingleClass) {
		Statement s = null;
		String query;
		ResultSet rs;
		try {
			query = "INSERT INTO " + rulesTableName + " (path,Label) VALUES ('"+ string + "','" + ifSingleClass +"')";
			s = conn.createStatement();
			s.execute(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;
	}

	//Returns list of P and N in given table 
	public ArrayList<Integer> getPandN(String tableName, String name, String value) {
		Statement s = null;
		String query;
		ResultSet rs;
		ArrayList<Integer> pn = new ArrayList<Integer>();
		try {
			query = "SELECT COUNT(*) FROM " + tableName + " WHERE Label = '" + labelPositive + "' AND "+ name +" = '"+ value +"'";
			s = conn.createStatement();
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				pn.add(rs.getInt(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			query = "SELECT COUNT(*) FROM " + tableName + " WHERE Label = '" + labelNegative + "' AND "+ name +" = '"+ value +"'";
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				pn.add(rs.getInt(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pn;
	}
	
	//Get result based on decision tree
	public void getResult(TicTacToe ticTacToe) {
		
		int count = 1;
		String pathString = "";
		String dbPathstring = getFirstRow(pathString);
		while(dbPathstring!=null){
			if(pathString.equals(dbPathstring) && getFirstRowLabel(dbPathstring)!=null) {
				ticTacToe.setLabel(getFirstRowLabel(dbPathstring));
				return;
			}
			else {
				int tempCount = 0;
				String field = "";
				StringTokenizer st = new StringTokenizer(dbPathstring,",");
			     while (st.hasMoreTokens()) {
			    	 tempCount++;
			    	 if(tempCount==count){
			    		 field = st.nextToken();
			    		 count += 2;
			    		 break;
			    	 }
			    	 else
			    		 st.nextToken();
			     }
			     pathString = pathString+getString(field, ticTacToe);
			     if(pathString.substring(0,1).equals(",")){
			    	 pathString = pathString.substring(1, pathString.length());
			     }
			     dbPathstring = getFirstRow(pathString);
			}
		}
		return;
	}

	public String getFirstRow(String condition) {

		Statement s = null;
		String query;
		ResultSet rs;
		if(condition.equals("")){
			condition = "%";
		}
		try {
			query = "SELECT path FROM "+ rulesTableName + " WHERE path like '"+ condition +"%'";
			s = conn.createStatement();
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				String st = rs.getString(1);
				return(rs.getString(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public String getFirstRowLabel(String condition) {

		Statement s = null;
		String query;
		ResultSet rs;
	
		try {
			query = "SELECT id,Label FROM "+ rulesTableName + " WHERE path = '"+ condition +"'";
			s = conn.createStatement();
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				return(rs.getString(2)+"(id : "+ rs.getString(1) +")");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getString(String field, TicTacToe ticTacToe) {
		
		if(field.equals("TL")){
			return ",TL," + ticTacToe.getTL();
		}
		if(field.equals("TM")){
			return ",TM," + ticTacToe.getTM();
		}
		if(field.equals("TR")){
			return ",TR," + ticTacToe.getTR();
		}
		if(field.equals("ML")){
			return ",ML," + ticTacToe.getML();
		}
		if(field.equals("MM")){
			return ",MM," + ticTacToe.getMM();
		}
		if(field.equals("MR")){
			return ",MR," + ticTacToe.getMR();
		}
		if(field.equals("BL")){
			return ",BL," + ticTacToe.getBL();
		}
		if(field.equals("BM")){
			return ",BM," + ticTacToe.getBM();
		}
		if(field.equals("BR")){
			return ",BR," + ticTacToe.getBR();
		}
		return "";
	}
}
