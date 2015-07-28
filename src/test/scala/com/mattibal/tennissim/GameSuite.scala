package com.mattibal.tennissim

import org.scalatest.FunSuite

import scala.util.Random


class GameSuite extends FunSuite {

  test("The score of a Game using a Random with a fixed seed, should always evolve in the same way"){

    val seed = 43242 // this seed will determine how the game will evolve
    val game = new Game(new Random(seed))

    // Check if the score after each point is what we are expecting with the fixed seed we used.
    assert(game.score.textRepresentation == "love - love")
    game.playPoint()
    assert(game.score.textRepresentation == "love - fifteen")
    for(i <- 1 to 4) {
      game.playPoint()
    }
    assert(game.score.textRepresentation == "fifteen - Win!")
    // now the game is over, it should not be possible to play another point
    intercept[IllegalStateException] {
      game.playPoint()
    }
  }

}
