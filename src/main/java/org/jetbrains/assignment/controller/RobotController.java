package org.jetbrains.assignment.controller;

import org.jetbrains.assignment.controller.command.MovementCommand;
import org.jetbrains.assignment.controller.res.Position;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/locations")
public class RobotController {

  @PostMapping
  public ResponseEntity<List<Position>> receiveMovements(
      @RequestBody List<MovementCommand> commands) {
    List<Position> result = new ArrayList<>();
    result.add(new Position(0, 0));
    int x = 0;
    int y = 0;
    for (MovementCommand command : commands) {
      int steps = command.steps();
      switch (command.direction()) {
        case EAST -> x = x + steps;
        case NORTH -> y = y + steps;
        case WEST -> x = x - steps;
        case SOUTH -> y = y - steps;
      }
      result.add(new Position(x, y));
    }

    return ResponseEntity.ok(result);
  }
}
