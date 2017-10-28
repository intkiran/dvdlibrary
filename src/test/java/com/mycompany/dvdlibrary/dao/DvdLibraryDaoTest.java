/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dvdlibrary.dao;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.dvdlibrary.model.Dvd;

/**
 *
 * @author albertshiral
 */
//transactional lets 

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
@Transactional
public class DvdLibraryDaoTest {
    @Autowired
    private DvdLibraryDao dao; 
    
    //injects JdbcTemplate within this test class 
    @Autowired
    private JdbcTemplate template;
    
    public DvdLibraryDaoTest() {
    }
    
 
      @Before
    public void setUp() {
        
        //implementation of the query
        //template is the database connection
      template.execute("insert into dvd(title, rating) values ('it', 'PG')");
      template.execute("insert into dvd(title, rating) values ('it', 'R')");
      template.execute("insert into dvd(title, rating) values ('ET', 'PG')");

    }
    
    @After
    public void tearDown() {
        
    }

    /**
     * Test of addDVD method, of class DVDLibraryDAO.
     */
    @Test
    public void testAddDvd() throws Exception {
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Date date = sdf.parse("1980.01.01");*/
        Dvd dvd = new Dvd(0, "M", "Steven Spielberg", "ET", "1980");        
        dao.addDvd(dvd);
        assertTrue("DVD Id was not generated", dvd.getDvdId() != 0);   
    }

    /**
     * Test of deleteDVD method, of class DVDLibraryDAO.
     */
    @Test
    public void testDeleteDvd() {
    }

    /**
     * Test of updateDVD method, of class DVDLibraryDAO.
     */
    @Test
    public void testUpdateDvd() {
    }

    /**
     * Test of getDVDById method, of class DVDLibraryDAO.
     */
    @Test
    public void testGetDvdById() {
    }
    
    @Test
    public void testSearchDvdsByTitle() {
        Map<SearchTerm, String> criteria = new HashMap<SearchTerm, String>();
        criteria.put(SearchTerm.TITLE, "it");
        List<Dvd> results = dao.searchDvds(criteria);
        assertTrue(!results.isEmpty());
        assertEquals("Incorrect number of results found", 2, results.size());
        assertEquals(results.get(0).getTitle(), "it");
    }
    
      
    @Test
    public void testSearchDVDsByRating() {
        Map<SearchTerm, String> criteria = new HashMap<SearchTerm, String>();
        criteria.put(SearchTerm.RATING, "PG");
        List<Dvd> results = dao.searchDvds(criteria);
        assertTrue("No results found", !results.isEmpty());
        assertEquals("Incorrect number of results found", 2, results.size());
        assertTrue(results.stream().filter(dvd -> dvd.getTitle().equals("it")).findFirst().isPresent());
        assertTrue(results.stream().filter(dvd -> dvd.getTitle().equals("ET")).findFirst().isPresent());
    }
    
    @Test
    public void testSearchDVDsByRatingAndTitle() {
        Map<SearchTerm, String> criteria = new HashMap<SearchTerm, String>();
        criteria.put(SearchTerm.RATING, "PG");
        criteria.put(SearchTerm.TITLE, "it");
        List<Dvd> results = dao.searchDvds(criteria);
        assertTrue("No results found", !results.isEmpty());
        assertEquals("Incorrect number of results found", 1, results.size());
        assertEquals(results.get(0).getRating(), "PG");
        assertEquals(results.get(0).getTitle(), "it");
        
        
    }
    
   

    /**
     * Test of getAllDVDs method, of class DVDLibraryDAO.
     */
    @Test
    public void testGetAllDvds() {
    }

}
