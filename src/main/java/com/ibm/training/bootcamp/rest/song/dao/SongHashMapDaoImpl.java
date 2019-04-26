package com.ibm.training.bootcamp.rest.song.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.ibm.training.bootcamp.rest.song.domain.Song;

public class SongHashMapDaoImpl implements SongDao {

	static private SongHashMapDaoImpl INSTANCE;
	static private final Map<Long, Song> SONG_STORE;
	static private long id = 0;

	static {
		SONG_STORE = new HashMap<>();
		Song song1 = new Song(id++, "The Show", "Lenka", "Label Whatever 01", "Jan. 7, 2190", "Cool");
		Song song2 = new Song(id++, "Take A Bow", "Lenka", "Label Whatever 01", "Jan. 7, 2190", "Cool");
		Song song3 = new Song(id++, "High School Never Ends", "Lenka", "Label Whatever 01", "Jan. 7, 2190", "Cool");
		SONG_STORE.put(song1.getId(), song1);
		SONG_STORE.put(song2.getId(), song2);
		SONG_STORE.put(song3.getId(), song3);
	}

	private SongHashMapDaoImpl() {
		
	}
	
	static public SongHashMapDaoImpl getInstance( ) {
		
		SongHashMapDaoImpl instance;
		if (INSTANCE != null) {
			instance = INSTANCE;
		} else {
			instance = new SongHashMapDaoImpl();
			INSTANCE = instance;
		}
		
		return instance;
	}
	
	@Override
	public List<Song> findAll() {
		return new ArrayList<Song>(SONG_STORE.values());
	}

	@Override
	public Song find(Long id) {
		return SONG_STORE.get(id);
	}

	@Override
	public List<Song> findByArtistOrGenre(String artist, String genre) {
		List<Song> songs = SONG_STORE.values().stream()
				.filter(user -> StringUtils.isBlank(artist) || user.getArtist().equalsIgnoreCase(artist))
				.filter(user -> StringUtils.isBlank(genre) || user.getGenre().equalsIgnoreCase(genre))
				.collect(Collectors.toList());
	
		return songs;
	}

	@Override
	public void add(Song song) {
		if (song != null && song.getId() == null) {
			song.setId(id++);
			SONG_STORE.put(song.getId(), song);
		}
	}

	@Override
	public void update(Song song) {
		if (song != null && song.getId() != null) {
			SONG_STORE.put(song.getId(), song);
		}
	}

	@Override
	public void delete(Long id) {
		if (id != null) {
			SONG_STORE.remove(id);
		}
	}

}
