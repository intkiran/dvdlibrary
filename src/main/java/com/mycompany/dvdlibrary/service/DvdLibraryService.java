package com.mycompany.dvdlibrary.service;

import java.util.List;
import java.util.Map;

import com.mycompany.dvdlibrary.dao.SearchTerm;
import com.mycompany.dvdlibrary.model.Dvd;

public interface DvdLibraryService {
	public void addDvd(Dvd dvd);

	public void deleteDvd(int dvdId);

	public void updateDvd(Dvd dvd);

	public Dvd getDVDById(int id);
	public Dvd getDVDByTitle(String title);

	public List<Dvd> getAllDVDs();

	public List<Dvd> searchDVDs(Map<SearchTerm, String> criteria);

}
