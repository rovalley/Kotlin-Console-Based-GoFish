/*
NAME
Main

DESCRIPTION
This module simulate a game of go fish with a AI player.

Created on February 21, 2024

@author: ryan_ovalley
*/

import kotlin.random.Random

// function to create a deck
fun createDeck(): MutableList<String> {
    // create card suits
    var suits = listOf("H", "D", 'S', "C")
    // create card ranks
    var ranks = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
    // create deck list
    var deck = mutableListOf<String>()
    // loop through all cards and add to the deck
    for (suit in suits) {
        for (rank in ranks) {
            var card = rank.plus(suit)
            deck.add(card)
        }
    }
    // return the deck
    return deck
}

// function to display hand
fun displayHand(hand: MutableList<String>, name: String) {
    print(name)
    print(": ")

    for (card in hand) {
        print(card)
        print(" ")
    }
    println()

}

// function to check if the game is over
fun checkGameOver(
    deck: MutableList<String>, playerHand: MutableList<String>,
    aiHand: MutableList<String>
): Boolean {
    // if the deck is empty return true
    if (deck.isEmpty()) {
        return true
    }
    // if the player hand is empty return true
    if (playerHand.isEmpty()) {
        return true
    }
    // if the ai hand is empty return true
    if (aiHand.isEmpty()) {
        return true
    }
    // return false
    return false

}

// function to check if you have a rank
fun hasRank(hand: MutableList<String>, rank: String): Boolean {
    // loop through hand
    for (card in hand) {
        // if card is the rank
        if (card.startsWith(rank)) {
            // return true
            return true
        }
    }
    // return false
    return false
}

// function to give cards away
fun giveCards(hand: MutableList<String>, rank: String): MutableList<String> {
    // create received cards list
    var receivedCards = mutableListOf<String>()
    // loop through hand
    for (card in hand) {
        // if card is the rank
        if (card.startsWith(rank)) {
            // add card to received cards
            receivedCards.add(card)
        }
    }
    // return received cards
    return receivedCards
}

// function to check if you have four of a kind
fun hasFour(hand: MutableList<String>, rank: String): Boolean {
    // create count variable and set it to 0
    var count = 0
    // loop through hand
    for (card in hand) {
        // if card is the rank
        if (card.startsWith(rank)) {
            // increment count by 1
            count += 1
        }

    }
    // return count equal 4
    return count == 4
}

// main function to run the game
fun main() {
    // create variables
    var playerScore = 0
    var aiScore = 0
    var ranks = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
    var suits = listOf("H", "D", 'S', "C")
    var deck = createDeck()
    var playerHand = mutableListOf<String>()
    var aiHand = mutableListOf<String>()

    // add 7 random cards to player hand
    for (i in 1..7) {
        var randomIndex = Random.nextInt(deck.size)
        var card = deck[randomIndex]
        deck.remove(card)
        playerHand.add(card)
    }
    // add 7 random cards to ai hand
    for (i in 1..7) {
        var randomIndex = Random.nextInt(deck.size)
        var card = deck[randomIndex]
        deck.remove(card)
        aiHand.add(card)
    }
    // set randomize turn
    var turn = Random.nextInt(2)
    // loop through ranks
    for (rank in ranks) {
        // if player has four of a kind ranks
        if (hasFour(playerHand, rank)) {
            println("Player has four of a kind: ".plus(rank))
            // increment player score
            playerScore += 1
            // loop through suits
            for (suit in suits) {
                // make card
                var card = rank.plus(suit)
                // remove card from player hand
                playerHand.remove(card)
            }

        }
        // if ai has four of a kind ranks
        if (hasFour(aiHand, rank)) {
            println("Ai has four of a kind: ".plus(rank))
            // increment ai score
            aiScore += 1
            // loop through suits
            for (suit in suits) {
                // make card
                var card = rank.plus(suit)
                // remove card from ai hand
                aiHand.remove(card)
            }
        }
    }
    // while game is not over
    while (!checkGameOver(deck, playerHand, aiHand)) {
        println()
        // display hands
        displayHand(playerHand, "player")
        displayHand(aiHand, "ai")
        // print remaining cards
        println("Remaining cards: ".plus(deck.size))
        // print scores
        println("Player score: ".plus(playerScore))
        println("AI score: ".plus(aiScore))
        // if turn equals 0
        if (turn == 0) {
            // create a rank input variable
            var rankInput = ""
            // while rank input is a empty string
            while (rankInput == "") {
                print("Enter a rank: ")
                // read rank input
                rankInput = readln()
                // uppercase rank input
                rankInput = rankInput.uppercase()
                // if ranks does not contain the rank input
                if (!ranks.contains(rankInput)) {
                    println("Incorrect rank try again")
                    // set rank input to an empty string
                    rankInput = ""
                }
            }
            // if ai has rank
            if (hasRank(aiHand, rankInput)) {
                // set received cards to ai hand and rank input
                var receivedCards = giveCards(aiHand, rankInput)
                // loop through received cards
                for (card in receivedCards) {
                    // remove card from ai hand
                    aiHand.remove(card)
                    // add card to player hand
                    playerHand.add(card)
                }
            } else {
                println("Go fish!")
                // set random index to random value in deck size
                var randomIndex = Random.nextInt(deck.size)
                // set card to random index from deck
                var card = deck[randomIndex]
                // remove card from deck
                deck.remove(card)
                // add card to player hand
                playerHand.add(card)
            }
            // set turn to 1
            turn = 1
        } else {
            // set random index to random value in rank size
            var randomIndex = Random.nextInt(ranks.size)
            // set rank to random index in ranks
            var rank = ranks[randomIndex]
            println("ai asked for: ".plus(rank))
            // if player has rank
            if (hasRank(playerHand, rank)) {
                // set received cards to player hand and rank
                var receivedCards = giveCards(playerHand, rank)
                // loop through received cards
                for (card in receivedCards) {
                    // remove card from player hand
                    playerHand.remove(card)
                    // add card to ai hand
                    aiHand.add(card)
                }

            } else {
                println("Go fish!")
                // set random index to random deck size value
                var randomIndex = Random.nextInt(deck.size)
                // set card to random index in deck
                var card = deck[randomIndex]
                // remove card from deck
                deck.remove(card)
                // add card to ai hand
                aiHand.add(card)
            }
            // set turn to 0
            turn = 0
        }
        // loop through ranks
        for (rank in ranks) {
            // if player has four of a kind ranks
            if (hasFour(playerHand, rank)) {
                println("Player has four of a kind: ".plus(rank))
                // increment player score
                playerScore += 1
                // loop through suits
                for (suit in suits) {
                    // create card
                    var card = rank.plus(suit)
                    // remove card from player hand
                    playerHand.remove(card)
                }

            }
            // if ai has four of a kind ranks
            if (hasFour(aiHand, rank)) {
                println("Ai has four of a kind: ".plus(rank))
                // increment ai score
                aiScore += 1
                // loop through suits
                for (suit in suits) {
                    // create card
                    var card = rank.plus(suit)
                    // remove card from ai hand
                    aiHand.remove(card)
                }
            }
        }
    }
    // if player score is greater than ai score
    if (playerScore > aiScore) {
        // print player has won
        println("Player has won the game!")
    // if ai score is greater than player score
    } else if (aiScore > playerScore) {
        // print ai has won
        println("Ai has won the game!")
    } else {
        // print the game has tied
        println("The game has ended in a tie.")
    }

}