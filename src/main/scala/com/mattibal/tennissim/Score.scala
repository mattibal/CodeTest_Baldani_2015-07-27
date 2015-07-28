package com.mattibal.tennissim

/**
 * An immutable representation of a score of a tennis game.
 */
case class Score(playerA: PlayerScore, playerB: PlayerScore) {

  /**
   * Create a new Score instance with the score of playerA incremented
   */
  def incrementPlayerAScore : Score = {
    if(gameOver) throw new IllegalStateException("Cannot play in finished game")
    lazy val newPlayerA: PlayerScore = PlayerScore(playerA.number + 1)(newPlayerB)
    lazy val newPlayerB: PlayerScore = PlayerScore(playerB.number)(newPlayerA)
    Score(newPlayerA, newPlayerB)
  }

  /**
   * Create a new Score instance with the score of playerB incremented
   */
  def incrementPlayerBScore : Score = {
    if(gameOver) throw new IllegalStateException("Cannot play in finished game")
    lazy val newPlayerB: PlayerScore = PlayerScore(playerB.number + 1)(newPlayerA)
    lazy val newPlayerA: PlayerScore = PlayerScore(playerA.number)(newPlayerB)
    Score(newPlayerA, newPlayerB)
  }

  /**
   * @return a nice text representation of the game score
   */
  def textRepresentation : String = playerA.textRepresentation + " - " + playerB.textRepresentation

  def gameOver : Boolean = playerA.isPlayerWinner || playerB.isPlayerWinner
}

object Score {

  /**
   * Creates a Score instance representing a game that has just started, so where the points of both players are zero.
   */
  def initialScore : Score = {
    lazy val playerA: PlayerScore = PlayerScore(0)(playerB)
    lazy val playerB: PlayerScore = PlayerScore(0)(playerA)
    Score(playerA, playerB)
  }

}


/**
 * An immutable representation of the score of a player in a tennis game
 *
 * @param number the player score represented as the number of points it won
 * @param otherPlayer the PlayerScore of the other player that this is playing with. Note that this is a circular reference.
 */
case class PlayerScore(number: Int)(otherPlayer: => PlayerScore) {

  require(number >= 0, "the player score can't be negative")

  /**
   * Return a string of the term used in tennis to indicate a certain score, if any, otherwise the score number
   */
  def tennisJargonNumber : String = number match {
    case 0 => "love"
    case 1 => "fifteen"
    case 2 => "thirty"
    case 3 => "forty"
    case n: Int => n + ""
  }

  /**
   * Return a string representation of the score of this player, in relation with the one of the other player
   */
  def textRepresentation : String = {
    if(isPlayerWinner) return "Win!"
    if(isPlayerInAdvantage) return "advantage"
    if(isScoreDeuce) return "deuce"
    tennisJargonNumber
  }


  def isPlayerWinner : Boolean =
    number >=4 && number - otherPlayer.number >= 2

  def isPlayerInAdvantage : Boolean =
    threePointsEachScored && number == otherPlayer.number + 1

  def isScoreDeuce : Boolean =
    threePointsEachScored && number == otherPlayer.number


  private def threePointsEachScored : Boolean =
    number >= 3 && otherPlayer.number >= 3

}
