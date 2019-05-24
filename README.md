# ChatfuelWebHook
Scala Webhook for Chatfuel chatbots: it includes the domain definitions and possible actions

## How to use it

Build library 
```
sbt publishLocal
```

Code snippet (also see unit testing and 'sister' app ChatfuelWebHookExample)
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
