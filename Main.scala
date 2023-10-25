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

def getYear(year: Int): Either[String, Int => Iterator[String] => Either[String, Day[_]]] = {
  year match {
    case 2020 => Right(`2020`.matchDay)
    case _    => Left(s"No solutions for year $year")
  }
}

def main(): Opts[Unit] = {
  val path = Opts.argument[Path]("input-file-path");

  val year = Opts
    .option[Int]("year", short = "y", metavar = "day", help = "Year")
    .withDefault(LocalDate.now().getYear())

  val day = Opts
    .option[Int]("day", short = "d", metavar = "day", help = "Day to solve")
    .withDefault(LocalDate.now().getDayOfMonth())

  (path, year, day).mapN { (pathArg, yearArg, dayArg) =>
    val day = for {
      getDay <- getYear(yearArg)
      input  <- getInputFileLines(pathArg)
      day    <- getDay(dayArg)(input)
    } yield day

    day match {
      case Left(err)  => println(err)
      case Right(day) => {
        println(day.part1())
        println(day.part2())
      }
    }
  }
}

object Main extends CommandApp(
  name   = "scala-cli run . --",
  header = "Given a path for input file solves advent of code problem for specified day in a specified year",
  main   = main()
)
