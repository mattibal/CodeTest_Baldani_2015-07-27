package com.mattibal.tennissim

import scala.util.Random

/**
 * A controller of a simulated tennis game.
 *
 * At each point, it decides the player that wins the point by extracting a boolean from a Random generator,
 * with probability equally distributed between the two players.
 * It's useful to provide a custom Random generator with a fixed predefined seed for testing, in order to obtain
 * a deterministic game and remove randomness.
 * If a Random is not provided, the singleton Random from Scala libraries is used. In this way, the game will be
 * truly random.
 *
 * @param randGen the Random generator to use. If not passed, the singleton Random from Scala libraries will be used.
 */
class Game(randGen: Random = Random) {
  
  var score = Score.initialScore

  /**
   * Play the game updating and showing in terminal the score after each point has been played
   */
  def playGameAndShowSteps() = {
    showStep()
    while(!score.gameOver){
      playPoint()
      showStep()
    }
  }

  /**
   * Play a single point, updating the score
   */
  def playPoint() = {
    score = randGen.nextBoolean() match {
      case true => score.incrementPlayerAScore
      case false => score.incrementPlayerBScore
    }
  }
  
  private def showStep() = {
    println(score.textRepresentation)
  }

}

