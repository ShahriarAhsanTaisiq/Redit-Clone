package com.dev.springreditclone.service;

import com.dev.springreditclone.dto.SubredditDto;


import java.util.List;

public interface SubredditService {
    SubredditDto save(SubredditDto subredditDto);

    List<SubredditDto> getAll();
    SubredditDto getSubreddit(Long id);
}
