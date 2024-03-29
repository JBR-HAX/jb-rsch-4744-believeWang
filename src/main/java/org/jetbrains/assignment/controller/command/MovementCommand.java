package org.jetbrains.assignment.controller.command;

import org.jetbrains.assignment.controller.enums.Direction;

public record MovementCommand(Direction direction, int steps) {}
