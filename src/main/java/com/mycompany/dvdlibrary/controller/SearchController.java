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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.dvdlibrary.dao.SearchTerm;
import com.mycompany.dvdlibrary.model.Dvd;
import com.mycompany.dvdlibrary.service.DvdLibraryService;

@Controller
public class SearchController {

	@Autowired
	DvdLibraryService dvdLibraryService;

	@RequestMapping(value = "api/dvd/search", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Dvd> update(@RequestBody Map<String, Object> model) {
		HashMap<String, String> errors = new HashMap<String, String>();

		String keyword = (String) model.get("keyword");
		String category = (String) model.get("category");
		SearchTerm searchTerm = SearchTerm.valueOf(category);

		Map<SearchTerm, String> ss = new HashMap<SearchTerm, String>();
		ss.put(searchTerm, keyword);

		List<Dvd> dvds = dvdLibraryService.searchDVDs(ss);

		return dvds;
	}
}
