package org.jetbrains.assignment.controller.dto;

import org.jetbrains.assignment.controller.enums.Direction;

public record Movement(Direction direction, int steps) {}
