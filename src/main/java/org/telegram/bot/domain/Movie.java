package org.telegram.bot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Movie {
    String title;
    int year;
    // url
    String poster;
}
