package com.ibm.training.bootcamp.rest.song.domain;

public class Song {
	
	private Long id;
	private String title;
	private String artist;
	private String label;
	private String date;
	private String genre;
	
	public Song() {}
	
	public Song(Long id, String title, String artist, String label, String date, String genre) {
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.label = label;
		this.date = date;
		this.genre = genre;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	

}
