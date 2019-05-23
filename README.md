# ChatfuelWebHook
Scala Webhook for Chatfuel chatbots: it includes the domain definitions and possible actions

## How to use it

Build library 
```
sbt publishLocal
```

Code snippet (or see unit testing adn the example)
```scala
val userInput = getUserInput

post {
 entity(as[String]) { payload =>

 implicit val body = payload
 val userInput = getUserInput
          
 val messages = replyWithTextMessages(Array[String]("hello " + firstname, "ciao Mr " + lastname))
          
 }
}
```
