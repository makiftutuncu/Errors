name := "Errors"

version := "1.0"

scalaVersion := "2.11.7"

// Update shell prompt of this module to get rid of ugly ">"
shellPrompt := {
  state: State => "[" + scala.Console.CYAN + name.value.toLowerCase + scala.Console.RESET + "] $ "
}
