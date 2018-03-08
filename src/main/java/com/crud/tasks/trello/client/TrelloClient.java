package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {
    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Value("${trello.api.endpoint.path.boards}")
    private String trelloBoardsPath;

    @Value("${trello.api.endpoint.path.cards}")
    private String trelloCardsPath;

    @Autowired
    private RestTemplate restTemplate;

    public Optional<List<TrelloBoardDto>> getTrelloBoards() {
        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(buildTrelloBoardsURI(), TrelloBoardDto[].class);
        return boardsResponse == null ? Optional.empty() : Optional.of(Arrays.asList(boardsResponse));
    }

    private URI buildTrelloBoardsURI() {
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint)
                   .path(trelloBoardsPath)
                   .queryParam("key", trelloAppKey)
                   .queryParam("token", trelloToken)
                   .queryParam("fields", "id,name")
                   .queryParam("lists", "all")
                   .build().encode().toUri();
    }

    public CreatedTrelloCardDto createNewCard(TrelloCardDto trelloCardDto) {
        return restTemplate.postForObject(buildTrelloCardsURI(trelloCardDto), null, CreatedTrelloCardDto.class);
    }

    private URI buildTrelloCardsURI(TrelloCardDto trelloCardDto) {
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint)
                   .path(trelloCardsPath)
                   .queryParam("key", trelloAppKey)
                   .queryParam("token", trelloToken)
                   .queryParam("name", trelloCardDto.getName())
                   .queryParam("desc", trelloCardDto.getDescription())
                   .queryParam("pos", trelloCardDto.getPos())
                   .queryParam("idList", trelloCardDto.getListId())
                   .build().encode().toUri();
    }
}
