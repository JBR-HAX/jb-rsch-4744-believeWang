package org.jetbrains.assignment.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.jetbrains.assignment.controller.dto.Movement;
import org.jetbrains.assignment.controller.enums.Direction;
import org.jetbrains.assignment.controller.dto.Position;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class RobotControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private RobotController robotController;

  @Test
  public void testReceiveMovements() throws Exception {
    Movement testCommand1 = new Movement(Direction.EAST, 1);
    Movement testCommand2 = new Movement(Direction.NORTH, 3);
    // Add more commands as needed
    List<Movement> commands = Arrays.asList(testCommand1, testCommand2);

    Position pos1 = new Position(0, 0);
    Position pos2 = new Position(1, 0);
    // Add more positions as needed
    List<Position> positions = Arrays.asList(pos1, pos2);

    when(robotController.receiveMovements(any())).thenReturn(ResponseEntity.ok(positions));

    mockMvc
        .perform(
            post("/locations")
                .content(
                    "[{\"direction\":\"EAST\",\"steps\":1},{\"direction\":\"NORTH\",\"steps\":3}]")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].x").value(pos1.x()))
        .andExpect(jsonPath("$[0].y").value(pos1.y()))
        .andExpect(jsonPath("$[1].x").value(pos2.x()))
        .andExpect(jsonPath("$[1].y").value(pos2.y()));
  }
}
