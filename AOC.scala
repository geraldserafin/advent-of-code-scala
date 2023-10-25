package aoc

abstract class Day[X](var input: Iterator[String]) {
  val data = parseInput(input)
  def parseInput(input: Iterator[String]): X

  def part1(): Any
  def part2(): Any
}
