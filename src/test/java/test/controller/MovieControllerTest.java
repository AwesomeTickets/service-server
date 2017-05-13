package test.controller;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import test.RestControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class MovieControllerTest extends RestControllerTest {

    @Test
    public void testGetMovieDetailsByID() throws Exception {
        mockMvc.perform(get("/resource/movie/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(11)))
            .andExpect(jsonPath("$.movieId").value(1))
            .andExpect(jsonPath("$.title").isString())
            .andExpect(jsonPath("$.pubDate").isString())
            .andExpect(jsonPath("$.length").isNumber())
            .andExpect(jsonPath("$.rating").isNumber())
            .andExpect(jsonPath("$.country").isString())
            .andExpect(jsonPath("$.movieStatus").isString())
            .andExpect(jsonPath("$.movieType").isString())
            .andExpect(jsonPath("$.movieStyle").isArray())
            .andExpect(jsonPath("$.posterSmall").isString())
            .andExpect(jsonPath("$.posterLarge").isString());
    }

    @Test
    public void testGetOnShowMovieIDs() throws Exception {
        mockMvc.perform(get("/resource/movie/on"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.count").isNumber())
            .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void testGetComingSoonMovieIDs() throws Exception {
        mockMvc.perform(get("/resource/movie/soon"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.count").isNumber())
            .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void testGetPopularMovies() throws Exception {
        mockMvc.perform(get("/resource/movie/popular?count=1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.count").value(1))
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data[0].movieId").isNumber())
            .andExpect(jsonPath("$.data[0].posterLarge").isString());
    }
}
