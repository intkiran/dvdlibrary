/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dvdlibrary.model;

import java.util.Objects;

/**
 *
 * @author albertshiral
 */
public class Dvd {
	private int dvdId;
	private String rating;
	private String director;
	private String title;
	private String releaseDate;
	private String notes;

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Dvd() {
	}

	public Dvd(int dvdId, java.lang.String rating, String director, String title, String releaseDate) {
		this.dvdId = dvdId;
		this.rating = rating;
		this.director = director;
		this.title = title;
		this.releaseDate = releaseDate;
	}

	public int getDvdId() {
		return dvdId;
	}

	public void setDvdId(int dvdId) {
		this.dvdId = dvdId;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 67 * hash + this.dvdId;
		hash = 67 * hash + Objects.hashCode(this.rating);
		hash = 67 * hash + Objects.hashCode(this.director);
		hash = 67 * hash + Objects.hashCode(this.title);
		hash = 67 * hash + Objects.hashCode(this.releaseDate);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Dvd other = (Dvd) obj;
		if (this.dvdId != other.dvdId) {
			return false;
		}
		if (this.rating != other.rating) {
			return false;
		}
		if (!Objects.equals(this.director, other.director)) {
			return false;
		}
		if (!Objects.equals(this.title, other.title)) {
			return false;
		}
		if (!Objects.equals(this.releaseDate, other.releaseDate)) {
			return false;
		}
		return true;
	}

}
