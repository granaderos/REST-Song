package com.ibm.training.bootcamp.rest.song.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hsqldb.jdbc.JDBCDataSource;

import com.ibm.training.bootcamp.rest.song.domain.Song;

public class SongJdbcDaoImpl implements SongDao {

	private static SongJdbcDaoImpl INSTANCE;

	private JDBCDataSource dataSource;

	static public SongJdbcDaoImpl getInstance() {

		SongJdbcDaoImpl instance;
		if (INSTANCE != null) {
			instance = INSTANCE;
		} else {
			instance = new SongJdbcDaoImpl();
			INSTANCE = instance;
		}

		return instance;
	}

	private SongJdbcDaoImpl() {
		init();
	}

	private void init() {
		dataSource = new JDBCDataSource();
		dataSource.setDatabase("jdbc:hsqldb:mem:SONG");
		dataSource.setUser("username");
		dataSource.setPassword("password");

		createSongTable();
		insertInitSongs();
	}
	
	// create song table
	private void createSongTable() {
		String createSql = "CREATE TABLE SONGS " + "(id INTEGER IDENTITY PRIMARY KEY, " + " title VARCHAR(255), "
				+ " artist VARCHAR(255), label VARCHAR(255), date VARCHAR(255), genre VARCHAR(255))";

		try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {

			stmt.executeUpdate(createSql);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void insertInitSongs() {
		// Song(String title, String artist, String label, String date, String genre)
		add(new Song(null, "The Show", "Lenka", "label01", "Jan. 30, 1990", "Whatever"));
		add(new Song(null, "Take A Bow", "Rhiana", "label01", "Jan. 30, 1990", "Whatever"));
		add(new Song(null, "High School Never Ends", "Bowling for Soup", "label01", "Jan. 30, 1990", "Whatever"));
	}

	@Override
	public List<Song> findAll() {

		return findByArtistOrGenre(null, null);
	}

	@Override
	public Song find(Long id) {

		Song song = null;

		if (id != null) {
			String sql = "SELECT * FROM SONGS where id = ?";
			try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

				ps.setInt(1, id.intValue());
				ResultSet results = ps.executeQuery();

				if (results.next()) {
					song = new Song(Long.valueOf(results.getInt("id")), results.getString("title"),
							results.getString("artist"), results.getString("label"), results.getString("date"), results.getString("genre"));
				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		return song;
	}

	@Override
	public List<Song> findByArtistOrGenre(String artist, String genre) {
		List<Song> songs = new ArrayList<>();

		String sql = "SELECT * FROM SONGS WHERE artist LIKE ? AND genre LIKE ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, createSearchValue(artist));
			ps.setString(2, createSearchValue(genre));
			
			ResultSet results = ps.executeQuery();

			while (results.next()) {
				Song song = new Song(Long.valueOf(results.getInt("id")), results.getString("title"),
						results.getString("artist"), results.getString("label"), results.getString("date"), results.getString("genre"));
				songs.add(song);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return songs;
	}

	private String createSearchValue(String string) {
		
		String value;
		
		if (StringUtils.isBlank(string)) {
			value = "%";
		} else {
			value = string;
		}
		
		return value;
	}
	
	@Override
	public void add(Song song) {
		
		String insertSql = "INSERT INTO SONGS VALUES (null, ?, ?, ?, ?, ?)";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(insertSql)) {

			ps.setString(1, song.getTitle());
			ps.setString(2, song.getArtist());
			ps.setString(3, song.getLabel());
			ps.setString(4, song.getDate());
			ps.setString(5, song.getGenre());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Song	song) {
		String updateSql = "UPDATE songs SET title = ?, artist = ?, label = ?, date = ?, genre = ? WHERE id = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setString(1, song.getTitle());
			ps.setString(2, song.getArtist());
			ps.setString(3, song.getLabel());
			ps.setString(4, song.getDate());
			ps.setString(5, song.getGenre());
			ps.setLong(3, song.getId());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Long id) {
		String updateSql = "DELETE FROM users WHERE id = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setLong(1, id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
