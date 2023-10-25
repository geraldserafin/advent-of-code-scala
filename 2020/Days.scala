package `2020`

import aoc.*

def matchDay(number: Int)(input: Iterator[String]): Either[String, Day[_]] =
  number match {
    case 1                => Right(new `2020`.Day1(input))
    case 2                => Right(new `2020`.Day2(input))
    case _ if number > 25 => Left("Advent is 25 days long you idiot :D.")
    case _                => Left(s"No solution for day $number in year 2020.")
  }
