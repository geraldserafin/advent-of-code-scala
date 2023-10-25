//> using lib "com.monovore::decline::2.4.1"
//> using lib "org.typelevel::cats-core::2.10.0"

package aoc

import com.monovore.decline._
import cats.syntax.all.*
import java.nio.file.Path
import java.time.{LocalDate}
import scala.io.Source._
import java.io.FileNotFoundException

def getInputFileLines(path: Path): Either[String, Iterator[String]] = {
  try {
    Right(fromFile(path.toUri()).getLines())
  } catch {
    case e: FileNotFoundException => Left("File not found")
    case _                        => Left("Unknown exception")
  }
}

def getYear(
    number: Int
): Either[String, Int => Iterator[String] => Either[String, Day[_]]] =
  number match {
    case 2020 => Right(`2020`.matchDay)
    case _    => Left(s"No solutions for year ")
  }

def main(): Opts[Unit] = {
  val path = Opts.argument[Path]("input-file-path");

  val year = Opts
    .option[Int]("year", short = "y", metavar = "day", help = "Year")
    .withDefault(LocalDate.now().getYear())

  val day = Opts
    .option[Int]("day", short = "d", metavar = "day", help = "Day to solve")
    .withDefault(LocalDate.now().getDayOfMonth())

  val part = Opts
    .option[Int](
      "part",
      short = "p",
      metavar = "part",
      help = "0 - both, 1 - part1, 2 - part2"
    )
    .withDefault(0)

  (path, year, day, part).mapN { (pathArg, yearArg, dayArg, partArg) =>
    val day = for {
      getDay <- getYear(yearArg)
      input <- getInputFileLines(pathArg)
      day <- getDay(dayArg)(input)
    } yield day

    (day, partArg) match {
      case (Right(day), 0) => {
        println(day.part1())
        println(day.part2())
      }
      case (Right(day), 1) => println(day.part1())
      case (Right(day), 2) => println(day.part2())
      case (Right(_), _)   => println("Pick a correct part to solve")
      case (Left(e), _)    => println(e)
    }
  }
}

object Main
    extends CommandApp(
      name = "scala-cli run . --",
      header =
        "Given a path for input file solves advent of code problem for specified day in a specified year",
      main = main()
    )
