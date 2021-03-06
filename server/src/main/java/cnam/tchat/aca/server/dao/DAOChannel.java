/**
 * 
 */
package cnam.tchat.aca.server.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import cnam.tchat.aca.server.io.Channel;

/**
 * @authors Arnold / Adrien / Cihat
 *
 */
public class DAOChannel implements DAO<Channel> {

	private static final Logger LOG = Logger.getLogger(DAOChannel.class.getName());
	private static final String URL = "jdbc:mysql://localhost:3306/chatirc?useSSL=false";
	private static final String LOGIN = "root";
	private static final String PASSWORD = "";
	
	public DAOChannel() throws DAOException{
		//useless
	}

	public Channel find(Object id) throws DAOException {
		if(!(id instanceof Integer))
			throw new DAOException("ID not take in charge.");
		
		final String sql = "SELECT * FROM `channel` WHERE `channel_id` = ? ;";
		
		Connection c = null;
		PreparedStatement st = null;
		ResultSet r = null;
		Channel ch = new Channel();
		
		try{
			c = DriverManager.getConnection(URL, LOGIN, PASSWORD);
			st = c.prepareStatement(sql);
			st.setInt(1, (int) id);
			r = st.executeQuery();
			
			if(r.next()){
				ch.setChannelId(r.getInt("channel_id"));
				ch.setChannelName(r.getString("channel_name"));
				
				return ch;
				
			}else{
				throw new DAOException("Error no channel for this id.");
			}
				
		} catch (SQLException e){
			throw new DAOException("Error in SQL engines during log information loading.", e);
		}finally{
			try{
				if(r != null)
					r.close();
				if(st != null)
					st.close();
				if(c != null)
					c.close();
			}catch (SQLException e){
				LOG.error("Error during closing open connections", e);
				
			}
		}
	}

	public void create(Channel obj) throws DAOException {
		final String sql = "INSERT INTO `channel` (`channel_id`, `channel_name`)"
				+ " VALUES ( ? , ? ) ;";
		
		Connection c = null;
		PreparedStatement st = null;
		int r = 0;
		
		
		try{
			c = DriverManager.getConnection(URL, LOGIN, PASSWORD);
			st = c.prepareStatement(sql);
			st.setInt(1, obj.getChannelId());
			st.setString(2, obj.getChannelName());
			r = st.executeUpdate();
				
		} catch (SQLException e){
			throw new DAOException("Error in SQL engines during channel creation.", e);
		}finally{
			try{
				if(st != null)
					st.close();
				if(c != null)
					c.close();
			}catch (SQLException e){
				LOG.error("Error during closing open connections");
				
			}
		}
	}

	public void update(Channel obj) throws DAOException {
		// TODO Auto-generated method stub
				final String sql = "UPDATE `channel` SET `channel_name` = ? "
						+ "WHERE `channel_id` = ? ;";
				
				Connection c = null;
				PreparedStatement st = null;
				int r = 0;
				
				
				try{
					// init connection
					c = DriverManager.getConnection(URL, LOGIN, PASSWORD);
					st = c.prepareStatement(sql);
					st.setString(1, obj.getChannelName());
					st.setInt(2, obj.getChannelId());
					r = st.executeUpdate();
					
					// check modification		
					if(r < 1){
						throw new DAOException("No channel was updated.");
					}
				} catch (SQLException e){
					throw new DAOException("Error in SQL engines during channel updating.", e);
				}finally{
					try{
						if(st != null)
							st.close();
						if(c != null)
							c.close();
					}catch (SQLException e){
						LOG.error("Error during closing open connections", e);
						
					}
				}
	}

	public void delete(Channel obj) throws DAOException {
		final String sql = "DELETE FROM `channel` WHERE `channel_id` = ? ;";
		
		Connection c = null;
		PreparedStatement st = null;
		int r = 0;
		
		try{
			c = DriverManager.getConnection(URL, LOGIN, PASSWORD);
			st = c.prepareStatement(sql);
			st.setInt(1, obj.getChannelId());
			r = st.executeUpdate();
			
			if(r < 1){
				throw new DAOException("No channel was deleted.");
			}
				
		} catch (SQLException e){
			throw new DAOException("Error in SQL engines during channel deleting.", e);
		}finally{
			try{
				if(st != null)
					st.close();
				if(c != null)
					c.close();
			}catch (SQLException e){
				LOG.error("Error during closing open connections", e);
				
			}
		}
		
	}

}
