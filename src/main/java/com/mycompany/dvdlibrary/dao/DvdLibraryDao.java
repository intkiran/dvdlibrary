/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dvdlibrary.dao;

import java.util.List;
import java.util.Map;

import com.mycompany.dvdlibrary.model.Dvd;

/**
 *
 * @author albertshiral
 */
public interface DvdLibraryDao {

	public void addDvd(Dvd dvd);

	public void deleteDvd(int dvdId);

	public void updateDvd(Dvd dvd);

	public Dvd getDvdById(int id);
	public Dvd getDVDByTitle(String title) ;

	public List<Dvd> getAllDvds();

	public List<Dvd> searchDvds(Map<SearchTerm, String> criteria);

}
