package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/trello")
public class TrelloController {
    @Autowired
    private TrelloClient trelloClient;

    @RequestMapping(method = RequestMethod.GET, value = "boards")
    public void getTrelloBoards() {
        Optional<List<TrelloBoardDto>> boards = trelloClient.getTrelloBoards();
        if (!boards.isPresent()) {
            // todo: apply exception
            System.err.println("service error");
            return;
        }

        boards.get().stream()
            .filter(this::validIdAndName)
            .forEach(b -> {
                System.out.println(b.getId() + " " + b.getName());
                System.out.println("This board contains lists: ");
                b.getLists().forEach(
                    list -> System.out.println(list.getName() + " - " + list.getId() + " - " + list.isClosed()));
            });
    }

    private boolean validIdAndName(TrelloBoardDto board) {
        String id = board.getId();
        String name = board.getName();
        return !(id == null || id.isEmpty() || name == null || name.isEmpty());
    }

    @RequestMapping(method = RequestMethod.POST, value = "cards")
    public CreatedTrelloCardDto createTrelloCard(@RequestBody TrelloCardDto card) {
        return trelloClient.createNewCard(card);
    }
}