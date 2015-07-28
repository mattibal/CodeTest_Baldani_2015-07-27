package com.mattibal.tennissim

/**
 * This is the "main" of an App that simulates a Tennis match, showing in the terminal the evolution of the score
 * after each point.
 */
object SimulatorApp extends App {

  println("Starting random tennis game, these are the scores after each point:")

  val game = new Game()
  game.playGameAndShowSteps()

  println("Game finished")

}
