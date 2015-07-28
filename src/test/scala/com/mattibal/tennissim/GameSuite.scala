package com.mattibal.tennissim

import java.io.ByteArrayOutputStream

import org.scalatest.FunSuite

import scala.util.Random


class GameSuite extends FunSuite {

  test("A Game using a Random with a fixed seed, should always proceed in the same way"){

    val seed = 43242
    val game = new Game(new Random(seed))

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
