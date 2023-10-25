package `2020`

import aoc.Day

case class PasswordPolicy(min: Int, max: Int, char: Char, pass: String)

class Day2(input: Iterator[String]) extends Day[List[PasswordPolicy]](input) {
  override def parseInput(input: Iterator[String]): List[PasswordPolicy] = {
    val data = for line <- input yield {
      val Array(minmax, char, pass) = line.split(" ")
      val Array(min, max) = minmax.split("-")
      PasswordPolicy(min.toInt, max.toInt, char.head, pass)
    }

    data.toList
  }

  override def part1(): Any = {
    val valid = for {
      PasswordPolicy(min, max, char, pass) <- data
      if min <= pass.count(_ == char)
        && max >= pass.count(_ == char)
    } yield pass

    valid.length
  }

  override def part2(): Any = {
    val valid = for {
      PasswordPolicy(min, max, char, pass) <- data
      if pass(min - 1) == char
        && pass(max - 1) != char
        || pass(min - 1) != char
        && pass(max - 1) == char
    } yield pass

    valid.length
  }

}
