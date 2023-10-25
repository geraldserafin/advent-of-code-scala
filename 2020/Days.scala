package `2020`

import aoc.*

def matchDay(day: Int)(input: Iterator[String]): Either[String, Day[_]] =
  day match {
    case 1             => Right(new `2020`.Day1(input))
    case 2             => Right(new `2020`.Day2(input))
    case _ if day > 25 => Left("Advent is 25 days long you idiot :D.")
    case _             => Left(s"No solution for day $day in year 2020.")
  }
