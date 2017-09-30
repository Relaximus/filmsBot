package org.telegram.bot;

import org.springframework.stereotype.Service;
import org.telegram.bot.domain.Movie;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    public List<Movie> findMoviesByTitle(String title) {
        // TODO change STUB
        List<Movie> list = new ArrayList<>();
        list.add(new Movie("Terminator",1984,"https://images-na.ssl-images-amazon.com/images/M/MV5BODE1MDczNTUxOV5BMl5BanBnXkFtZTcwMTA0NDQyNA@@._V1_SY1000_CR0,0,666,1000_AL_.jpg"));
        return list;
    }
}
