/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dvdlibrary.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.dvdlibrary.model.Dvd;

/**
 * @author albertshiral
 */
@Repository
public class DvdDaoJdbcTemplateImpl implements DvdLibraryDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_DVD = "insert into dvds (title, releasedate, director, "
            + "rating,notes) values (?, ?, ?, ?,?)";

    private static final String SQL_DELETE_DVD = "delete from dvds where dvd_id = ?";

    private static final String SQL_UPDATE_DVD = "update dvds set title = ?, releasedate = ?, "
            + "director = ?, rating = ?, notes= ? where dvd_id =  ?";

    private static final String SQL_SELECT_DVD = "select * from dvds where dvd_id = ?";
    private static final String SQL_SELECT_DVD_TITLE = "select * from dvds where title = ?";
    // Check this one again
    private static final String SQL_SELECT_DVD_BY_DVD_ID = "select dvd.dvd_id,dvd.title, dvd.releasedate, "
            + "dvd.director, dvd.rating from dvds dvd " + "where dvd_id = ?";

    private static final String SQL_SELECT_ALL_DVDS = "select * from dvds";

    private static final String SELECT_DVD = "select dvd.dvd_id,dvd.title, dvd.releasedate, "
            + "dvd.director, dvd.rating,dvd.notes from dvds dvd where ";

    //private static final String FILTER_BY_TITLE = "dvd.title like '%?%'";
    //private static final String FILTER_BY_RATING = "dvd.rating like '%?%'";

    private static final String FILTER_BY_TITLE = " LOWER(dvd.title) like ? ";
    private static final String FILTER_BY_RATING = " LOWER(dvd.rating)  like ? ";
    private static final String FILTER_BY_DIRECTOR = " LOWER(dvd.director) like ? ";
    private static final String FILTER_BY_YEAR = " dvd.releasedate like ? ";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addDvd(Dvd dvd) {
        jdbcTemplate.update(SQL_INSERT_DVD, dvd.getTitle(), dvd.getReleaseDate(), dvd.getDirector(), dvd.getRating(), dvd.getNotes());

        int dvdId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        dvd.setDvdId(dvdId);

    }

    @Override
    public void deleteDvd(int dvdId) {
        jdbcTemplate.update(SQL_DELETE_DVD, dvdId);
    }

    @Override
    public void updateDvd(Dvd dvd) {
        jdbcTemplate.update(SQL_UPDATE_DVD, dvd.getTitle(), dvd.getReleaseDate(), dvd.getDirector(), dvd.getRating(), dvd.getNotes(), dvd.getDvdId());

    }

    @Override
    public Dvd getDvdById(int id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_DVD, new DVDMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    @Override
    public List<Dvd> getAllDvds() {
        return jdbcTemplate.query(SQL_SELECT_ALL_DVDS, new DVDMapper());
    }

    @Override
    public List<Dvd> searchDvds(Map<SearchTerm, String> criteria) {
        // throw new UnsupportedOperationException("Not supported yet."); //To
        // change body of generated methods, choose Tools | Templates.
        StringBuilder query = new StringBuilder(SELECT_DVD);
        boolean first = true;
        List<String> params = new ArrayList<String>();
        for (SearchTerm term : criteria.keySet()) {
            String text = criteria.get(term);
            text = "%" + text.toLowerCase().trim() + "%"; //Notes: no quote

            if (!first) {
                query.append("and ");
            }
            switch (term) {
                case RATING:
                    query.append(FILTER_BY_RATING);
                    break;
                case TITLE:
                    query.append(FILTER_BY_TITLE);
                    break;
                case DIRECTOR:
                    query.append(FILTER_BY_DIRECTOR);
                    break;
                case YEAR:
                    query.append(FILTER_BY_YEAR);
                    break;
            }
            params.add(text);
            first = false;
        }
        System.out.println("query.toString() 1" + query.toString() + params);

        return jdbcTemplate.query(query.toString(), params.toArray(), new DVDMapper());

    }

    private static final class DVDMapper implements RowMapper<Dvd> {

        @Override
        public Dvd mapRow(ResultSet rs, int i) throws SQLException {
            Dvd dvd = new Dvd();
            dvd.setTitle(rs.getString("title"));
            dvd.setReleaseDate(rs.getString("releasedate"));

            // dvd.setReleaseDate((rs.getDate("releasedate") != null) ?
            // rs.getDate("releasedate").toLocalDate() : null);
            dvd.setDirector(rs.getString("director"));
            dvd.setRating(rs.getString("rating"));
            dvd.setNotes(rs.getString("notes"));

            dvd.setDvdId(rs.getInt("dvd_id"));
            return dvd;
        }
    }

    @Override
    public Dvd getDVDByTitle(String title) {

        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_DVD_TITLE, new DVDMapper(), title);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

}
