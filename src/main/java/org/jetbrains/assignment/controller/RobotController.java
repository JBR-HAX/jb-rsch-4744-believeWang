package org.jetbrains.assignment.controller;

import org.jetbrains.assignment.controller.dto.Movement;
import org.jetbrains.assignment.controller.dto.Position;
import org.jetbrains.assignment.controller.enums.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class RobotController {

  @PostMapping("/locations")
  public ResponseEntity<List<Position>> receiveMovements(@RequestBody List<Movement> commands) {
    List<Position> result = new ArrayList<>();
    result.add(new Position(0, 0));
    int x = 0;
    int y = 0;
    for (Movement command : commands) {
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

  @PostMapping("/moves")
  public ResponseEntity<List<Movement>> performMoves(@RequestBody List<Position> positions) {

    List<Movement> result = new ArrayList<>(positions.size());
    for (int i = 1; i < positions.size(); i++) {
      Position previousPosition = positions.get(i - 1);
      Position currentPosition = positions.get(i);
      Movement movement;
      int previousX = previousPosition.x();
      int currentX = currentPosition.x();
      int previousY = previousPosition.y();
      int currentY = currentPosition.y();
      if (previousX != currentX) {
        int xChange = currentX - previousX;
        if (xChange > 0) {
          movement = new Movement(Direction.EAST, xChange);
        } else {
          movement = new Movement(Direction.WEST, -xChange);
        }
        result.add(movement);
      }
      if (previousY != currentY) {

        int yChange = currentY - previousY;
        if (yChange > 0) {
          movement = new Movement(Direction.NORTH, yChange);
        } else {
          movement = new Movement(Direction.SOUTH, -yChange);
        }
        result.add(movement);
      }
    }

    return ResponseEntity.ok(result);
  }
}
