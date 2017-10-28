package com.mycompany.dvdlibrary.service;

import java.util.List;
import java.util.Map;

import com.mycompany.dvdlibrary.dao.DvdLibraryDao;
import com.mycompany.dvdlibrary.dao.SearchTerm;
import com.mycompany.dvdlibrary.model.Dvd;

public class DvdLibraryServiceImpl implements DvdLibraryService {

	DvdLibraryDao dvdLibraryDao;

	public DvdLibraryDao getDvdLibraryDao() {
		return dvdLibraryDao;
	}

	public void setDvdLibraryDao(DvdLibraryDao dvdLibraryDao) {
		this.dvdLibraryDao = dvdLibraryDao;
	}

	@Override
	public void addDvd(Dvd dvd) {
		dvdLibraryDao.addDvd(dvd);

	}

	@Override
	public void deleteDvd(int dvdId) {
		dvdLibraryDao.deleteDvd(dvdId);
	}

	@Override
	public void updateDvd(Dvd dvd) {
		dvdLibraryDao.updateDvd(dvd);
	}

	@Override
	public Dvd getDVDById(int id) {
		Dvd dvd = dvdLibraryDao.getDvdById(id);

		return dvd;
	}

	@Override
	public List<Dvd> getAllDVDs() {
		return dvdLibraryDao.getAllDvds();
	}

	@Override
	public List<Dvd> searchDVDs(Map<SearchTerm, String> criteria) {

		return dvdLibraryDao.searchDvds(criteria);
	}

	@Override
	public Dvd getDVDByTitle(String title) {
		return dvdLibraryDao.getDVDByTitle(title);
	}

}
