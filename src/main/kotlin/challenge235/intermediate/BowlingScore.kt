package challenge235.intermediate

import java.util.*

/**
 * Description:
 * The game of bowling is pretty simple: you have ten pins arranged in a triangle,
 * and you roll a ball down a slick alley towards them and try and knock as many down as possible.
 * In most frames (see below about the tenth frame) you get two attempts per "frame" before the remaining pins are cleared.
 * The bowler is allowed 10 frames in which to knock down pins,
 * with frames one (1) through nine (9) being composed of up to two rolls.
 * The tenth frame may be composed of up to three rolls:
 * the bonus roll(s) following a strike or spare in the tenth (sometimes referred to as the eleventh and twelfth frames)
 * are fill ball(s) used only to calculate the score of the mark rolled in the tenth.
 * Bowing scoring is a bit tricky (which is why this is an intermediate challenge).
 * In addition to a gutter ball (which is 0 pins), you have strikes and spares as well as 1 to 9 pins being knocked down.
 * Strikes and spares affect the next balls in different ways.
 * When all ten pins are knocked down with the first ball of a frame (called a strike and typically rendered as an "X" on a scoresheet),
 * a player is awarded ten points, plus a bonus of whatever is scored with the next two balls.
 * In this way, the points scored for the two balls after the strike are counted twice.
 * A "spare" is awarded when no pins are left standing after the second ball of a frame;
 * i.e., a player uses both balls of a frame to clear all ten pins.
 * A player achieving a spare is awarded ten points, plus a bonus of whatever is scored with the next ball
 * (only the first ball is counted). It is typically rendered as a slash on scoresheets in place of the second pin count for a frame.
 * Your challenge today is to determine a bowling score from a score sheet.
 *
 *
 * Input Description:
 * You'll be given a bowling sheet for a single person on a line, with the ten frames separated by a space. All scores are single characters: - for zero (aka a gutter ball), 1-9 for 1-9 pins knocked down, / for a spare, and X for a strike. If you see any two digit numbers (e.g. "63") that's actually the first and second ball scores (a six then a three). Example:
 *
 * X X X X X X X X X XXX
 *
 *
 * Output Description:
 * Your program should calculate the score for the sheet and report it. From our example:
 * 300 (Aka a perfect game)
 *
 *
 * Challenge Input:
 * X -/ X 5- 8/ 9- X 81 1- 4/X
 * 62 71  X 9- 8/  X  X 35 72 5/8
 *
 *
 * Challenge Output:
 * 137
 * 140
 */
fun main(args: Array<String>) {

  val strikeIndexes = ArrayList<Int>()
  val spareIndexes = ArrayList<Int>()

  val frameRolls = args.mapIndexed { index, frame ->
    frame.map { roll ->
      when (roll) {
        '-' -> 0
        '/' -> {
          spareIndexes += index
          -1
        }
        'X' -> {
          strikeIndexes += index
          10
        }
        else -> roll.toInt() - '0'.toInt()
      }
    }
  }

  val rolls = frameRolls.flatten().withFixedSpares()
  val framePoints = frameRolls.map { it.withFixedSpares().sum() }

  val strikeBonus = strikeIndexes.map {

  }

  println("Strikes:")
  strikeIndexes.forEach { println(it) }

  println("Spares:")
  spareIndexes.forEach { println(it) }

  println("Rolls:")
  rolls.forEach { println(it) }

  println("Frame points:")
  framePoints.forEach { println(it) }
}

fun List<Int>.withFixedSpares(): List<Int> = this.mapIndexed { index, value ->
  when (value) {
    -1 -> 10 - this[index - 1]
    else -> value
  }
}