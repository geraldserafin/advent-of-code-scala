package `2020`

import aoc.Day

class Day1(input: Iterator[String]) extends Day[List[Int]](input) {
  override def parseInput(input: Iterator[String]): List[Int] = {
    input.map(_.toInt).toList
  }

  override def part1(): Any =
    (for
      x <- data
      y <- data
      if x + y == 2020
    yield x * y).head

  override def part2(): Any =
    (for
      x <- data
      y <- data
      z <- data
      if x + y + z == 2020
    yield x * y * z).head
}
