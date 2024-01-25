package com.dev.springreditclone.service;

import com.dev.springreditclone.dto.SubredditDto;
import com.dev.springreditclone.exception.SpringReditException;
import com.dev.springreditclone.mapper.SubredditMapper;
import com.dev.springreditclone.model.Subreddit;
import com.dev.springreditclone.repository.SubredditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubredditServiceImpl implements SubredditService {
    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Override
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    @Override
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(toList());
    }

    @Override
    public SubredditDto getSubreddit(Long id) {
        try {
            Subreddit subreddit = subredditRepository.findById(id)
                    .orElseThrow(() -> new SpringReditException("No subreddit found with this id " + id));
            return subredditMapper.mapSubredditToDto(subreddit);
        } catch (Exception ex) {
            log.error("Error getting subreddit with id {}", id, ex);
            throw new SpringReditException("Error getting subreddit with id " + id);
        }
    }
}