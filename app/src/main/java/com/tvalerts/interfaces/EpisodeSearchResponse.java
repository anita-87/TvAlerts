package com.tvalerts.interfaces;

import com.tvalerts.domain.Episode;

import java.util.List;
import java.util.Map;

/**
 * Created by anita on 18/02/16.
 */
public interface EpisodeSearchResponse {
    void onFinishedEpisodeSearch(Map<String, List<Episode>> episodes);
}
