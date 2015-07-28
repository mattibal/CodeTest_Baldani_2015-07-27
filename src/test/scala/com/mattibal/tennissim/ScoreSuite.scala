package com.mattibal.tennissim

import org.scalatest.FunSuite


class ScoreSuite extends FunSuite {


  test("PlayerScore should be incremented when a new incremented Score is created"){
    val score1 = Score.initialScore
    val score2 = score1.incrementPlayerAScore
    assert(score2.playerA.number == 1)
    assert(score2.playerB.number == 0)
    val score3 = score2.incrementPlayerBScore
    assert(score3.playerA.number == 1)
    assert(score3.playerB.number == 1)
  }


  test("If Score is 4-0, playerA should win, playerB should lose, and the game should not go on"){
    var score = Score.initialScore
    for(i <- 1 to 4){
      score = score.incrementPlayerAScore
    }
    assert(score.playerA.isPlayerWinner)
    assert(!score.playerB.isPlayerWinner)
    // The game is over, I can't go on in incrementing scores
    intercept[IllegalStateException] {
      score = score.incrementPlayerAScore
    }
    intercept[IllegalStateException] {
      score = score.incrementPlayerBScore
    }
  }


  test("Initial Score text representation should be 'love - love'"){
    val score = Score.initialScore
    assert(score.textRepresentation == "love - love")
  }


  test("Score should be Deuce when 3-3 and 4-4, but not 2-2"){
    var score = Score.initialScore

    score = incrementBothPlayers(score, 2)
    // we are now 2 - 2
    assert(!score.playerA.isScoreDeuce)

    score = incrementBothPlayers(score, 1)
    // we are now 3 - 3, Deuce!
    assert(score.playerA.isScoreDeuce)
    assert(score.playerB.isScoreDeuce)

    score = incrementBothPlayers(score, 1)
    // we are now 4 - 4, still Deuce
    assert(score.playerA.isScoreDeuce)
  }


  test("A player of the two should be in Advantage if the score is 4-3, and not if it's 4-4"){
    var score = Score.initialScore

    score = incrementBothPlayers(score, 3)
    score = score.incrementPlayerAScore
    // we are now 4-3
    assert(score.playerA.isPlayerInAdvantage)
    assert(!score.playerB.isPlayerInAdvantage)

    score = score.incrementPlayerBScore
    assert(!score.playerA.isPlayerInAdvantage)
    assert(!score.playerB.isPlayerInAdvantage)
  }


  private def incrementBothPlayers(score: Score, incrementTimes: Int) : Score = {
    var newScore = score
    for(i <- 1 to incrementTimes){
      newScore = newScore.incrementPlayerAScore
      newScore = newScore.incrementPlayerBScore
    }
    newScore
  }

}
