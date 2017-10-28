/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dvdlibrary.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.dvdlibrary.model.Dvd;
import com.mycompany.dvdlibrary.service.DvdLibraryService;

/**
 *
 * @author albertshiral
 */
@Controller
@RequestMapping("/api/")
public class DVDController {

	@Autowired
	DvdLibraryService dvdLibraryService;

	@RequestMapping(value = "/dvds", method = RequestMethod.GET)
	public String displayDVDsPage() {
		return "contacts";
	}

	@RequestMapping(value = "/dvd/readall", method = RequestMethod.GET, produces = "application/json", headers = {
			"Content-type=application/json" })
	public @ResponseBody List<Dvd> allDvds() {

		return dvdLibraryService.getAllDVDs();
	}

	@RequestMapping(value = "/dvd/update", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Dvd update(@RequestBody Map<String, Object> model) {
		HashMap<String, String> errors = new HashMap<String, String>();

		String title = (String) model.get("title");
		Dvd dvd = dvdLibraryService.getDVDByTitle(title.toLowerCase());

		dvd.setTitle(((String) model.get("title")));
		dvd.setDirector((String) model.get("director"));
		dvd.setRating((String) model.get("rating"));
		String syear = (String) model.get("year");			
		String notes = (String) model.get("notes");

		
/*		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, Integer.parseInt(syear));*/
		dvd.setReleaseDate(syear);
		dvd.setNotes(notes);

		dvdLibraryService.updateDvd(dvd);

		return dvd;
	}

	@RequestMapping(value = "/dvd/create", method = RequestMethod.POST)
	public @ResponseBody Object create(@RequestBody Map<String, Object> model) {
		// Role role = new Role();
		HashMap<String, String> errors = new HashMap<String, String>();

		String title = (String) model.get("title");
		Dvd dvd = dvdLibraryService.getDVDByTitle(title.toLowerCase());
		if (dvd != null) {

			errors.put("errors", title + "  Dvd already exist!. Please Choose different title");
			return errors;

		} else {
			dvd = new Dvd();

			dvd.setTitle(((String) model.get("title")));
			dvd.setDirector((String) model.get("director"));
			dvd.setRating((String) model.get("rating"));
			String syear = (String) model.get("year");
			String notes = (String) model.get("notes");

/*			Calendar calendar = Calendar.getInstance();
			calendar.clear();
			calendar.set(Calendar.YEAR, Integer.parseInt(syear));*/
			dvd.setReleaseDate(syear);
			dvd.setNotes(notes);

			dvdLibraryService.addDvd(dvd);

		}

		return dvd;
	}

	@RequestMapping(value = "/dvd/delete", method = RequestMethod.POST)
	public @ResponseBody Dvd destroy(@RequestBody Map<String, Object> model) {
		// Role role = new Role();

		Integer dvdId = (Integer) model.get("dvdId");
		// role.setId(new Long(rid));
		Dvd dvd = dvdLibraryService.getDVDById(dvdId);

		dvdLibraryService.deleteDvd(dvdId);

		return dvd;

	}

	@RequestMapping(value = "/dvd/{dvdId}", method = RequestMethod.GET)
	@ResponseBody
	public Dvd getFunctionsByRoleId(@PathVariable Integer dvdId) {
		Dvd dvd = dvdLibraryService.getDVDById(dvdId);
		return dvd;
	}
}
