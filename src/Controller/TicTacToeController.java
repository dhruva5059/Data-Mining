package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.TicTacToe;

public class TicTacToeController {
	static final String databaseName = "data_mining";
	static final String datasetTableName = "tic_tac_toe";
	static final String labelPositive = "positive";
	static final String labelNegative = "negative";
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

	public void getResult(TicTacToe ticTacToe) {
		float probPositive = 1.0f, probNegative = 1.0f;
		String query = "";
		Statement s = null;
		ResultSet rs;
		int totalRows = -1;
		int totalPosiRows = -1;
		int totalNegRows = -1;
		int temp = -1;
		try {
			s = conn.createStatement();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Get total number of rows in the data set
		try {
			query = "SELECT COUNT(*) FROM " + datasetTableName;
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				totalRows = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Calculate probability of positive and negative class
		try {
			query = "SELECT COUNT(*) FROM " + datasetTableName + " WHERE Label = '" + labelPositive + "'";
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				totalPosiRows = rs.getInt(1);
				totalNegRows = totalRows - totalPosiRows;
				probPositive = probPositive * ((float) totalPosiRows / (float) totalRows);
				probNegative = probNegative * ((float) totalNegRows / (float) totalRows);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Calculate probability of TL = ticTacToe.getTL() over positive and negative class
		try {
			query = "SELECT COUNT(*) FROM " + datasetTableName + " WHERE TL = '" + ticTacToe.getTL() + "' and Label = '" + labelPositive + "'";
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				temp = rs.getInt(1);
				probPositive = probPositive * ((float) temp / (float) totalPosiRows);
			}
			query = "SELECT COUNT(*) FROM " + datasetTableName + " WHERE TL = '" + ticTacToe.getTL() + "' and Label = '" + labelNegative + "'";
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				temp = rs.getInt(1);
				probNegative = probNegative * ((float) temp / (float) totalNegRows);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Calculate probability of TM = ticTacToe.getTM() over positive and negative class
		try {
			query = "SELECT COUNT(*) FROM " + datasetTableName + " WHERE TM = '" + ticTacToe.getTM() + "' and Label = '" + labelPositive + "'";
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				temp = rs.getInt(1);
				probPositive = probPositive * ((float) temp / (float) totalPosiRows);
			}
			query = "SELECT COUNT(*) FROM " + datasetTableName + " WHERE TM = '" + ticTacToe.getTM() + "' and Label = '" + labelNegative + "'";
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				temp = rs.getInt(1);
				probNegative = probNegative * ((float) temp / (float) totalNegRows);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Calculate probability of TR = ticTacToe.getTR() over positive and negative class
		try {
			query = "SELECT COUNT(*) FROM " + datasetTableName + " WHERE TR = '" + ticTacToe.getTR() + "' and Label = '" + labelPositive + "'";
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				temp = rs.getInt(1);
				probPositive = probPositive * ((float) temp / (float) totalPosiRows);
			}
			query = "SELECT COUNT(*) FROM " + datasetTableName + " WHERE TR = '" + ticTacToe.getTR() + "' and Label = '" + labelNegative + "'";
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				temp = rs.getInt(1);
				probNegative = probNegative * ((float) temp / (float) totalNegRows);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Calculate probability of ML = ticTacToe.getML() over positive and negative class
		try {
			query = "SELECT COUNT(*) FROM " + datasetTableName + " WHERE ML = '" + ticTacToe.getML() + "' and Label = '" + labelPositive + "'";
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				temp = rs.getInt(1);
				probPositive = probPositive * ((float) temp / (float) totalPosiRows);
			}
			query = "SELECT COUNT(*) FROM " + datasetTableName + " WHERE ML = '" + ticTacToe.getML() + "' and Label = '" + labelNegative + "'";
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				temp = rs.getInt(1);
				probNegative = probNegative * ((float) temp / (float) totalNegRows);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Calculate probability of MM = ticTacToe.getMM() over positive and negative class
		try {
			query = "SELECT COUNT(*) FROM " + datasetTableName + " WHERE MM = '" + ticTacToe.getMM() + "' and Label = '" + labelPositive + "'";
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				temp = rs.getInt(1);
				probPositive = probPositive * ((float) temp / (float) totalPosiRows);
			}
			query = "SELECT COUNT(*) FROM " + datasetTableName + " WHERE MM = '" + ticTacToe.getMM() + "' and Label = '" + labelNegative + "'";
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				temp = rs.getInt(1);
				probNegative = probNegative * ((float) temp / (float) totalNegRows);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Calculate probability of MR = ticTacToe.getMR() over positive and negative class
		try {
			query = "SELECT COUNT(*) FROM " + datasetTableName + " WHERE MR = '" + ticTacToe.getMR() + "' and Label = '" + labelPositive + "'";
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				temp = rs.getInt(1);
				probPositive = probPositive * ((float) temp / (float) totalPosiRows);
			}
			query = "SELECT COUNT(*) FROM " + datasetTableName + " WHERE MR = '" + ticTacToe.getMR() + "' and Label = '" + labelNegative + "'";
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				temp = rs.getInt(1);
				probNegative = probNegative * ((float) temp / (float) totalNegRows);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Calculate probability of BL = ticTacToe.getBL() over positive and negative class
		try {
			query = "SELECT COUNT(*) FROM " + datasetTableName + " WHERE BL = '" + ticTacToe.getBL() + "' and Label = '" + labelPositive + "'";
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				temp = rs.getInt(1);
				probPositive = probPositive * ((float) temp / (float) totalPosiRows);
			}
			query = "SELECT COUNT(*) FROM " + datasetTableName + " WHERE BL = '" + ticTacToe.getBL() + "' and Label = '" + labelNegative + "'";
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				temp = rs.getInt(1);
				probNegative = probNegative * ((float) temp / (float) totalNegRows);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Calculate probability of BM = ticTacToe.getBM() over positive and negative class
		try {
			query = "SELECT COUNT(*) FROM " + datasetTableName + " WHERE BM = '" + ticTacToe.getBM() + "' and Label = '" + labelPositive + "'";
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				temp = rs.getInt(1);
				probPositive = probPositive * ((float) temp / (float) totalPosiRows);
			}
			query = "SELECT COUNT(*) FROM " + datasetTableName + " WHERE BM = '" + ticTacToe.getBM() + "' and Label = '" + labelNegative + "'";
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				temp = rs.getInt(1);
				probNegative = probNegative * ((float) temp / (float) totalNegRows);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Calculate probability of BR = ticTacToe.getBR() over positive and negative class
		try {
			query = "SELECT COUNT(*) FROM " + datasetTableName + " WHERE BR = '" + ticTacToe.getBR() + "' and Label = '" + labelPositive + "'";
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				temp = rs.getInt(1);
				probPositive = probPositive * ((float) temp / (float) totalPosiRows);
			}
			query = "SELECT COUNT(*) FROM " + datasetTableName + " WHERE BR = '" + ticTacToe.getBR() + "' and Label = '" + labelNegative + "'";
			s.execute(query);
			rs = s.getResultSet();
			if (rs != null && rs.next()) {
				temp = rs.getInt(1);
				probNegative = probNegative * ((float) temp / (float) totalNegRows);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (probPositive > probNegative)
			ticTacToe.setLabel(labelPositive);
		else
			ticTacToe.setLabel(labelNegative);
		System.out.println("\nP(positive|X) : " + probPositive + "\nP(negative|X) : " + probNegative + "\n");
		return;
	}

}
