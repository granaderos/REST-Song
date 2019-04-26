package com.ibm.training.bootcamp.rest.song.dao;

import java.util.List;

import com.ibm.training.bootcamp.rest.song.domain.Song;

public interface SongDao {
	
	public List<Song> findAll();
	
	public Song find(Long id);
	
	public List<Song> findByArtistOrGenre(String artist, String genre);
	
	public void add(Song song);
	
	public void update(Song song);
	
	public void delete(Long id);

}
