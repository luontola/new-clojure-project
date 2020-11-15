(ns my-project.core-test
  (:require [clojure.test :refer [deftest is testing]]))

(def rules
  (->> [[:rock "crushes" :scissors]
        [:rock "crushes" :lizard]
        [:paper "covers" :rock]
        [:paper "disproves" :spock]
        [:scissors "cuts" :paper]
        [:scissors "decapitates" :lizard]
        [:lizard "eats" :paper]
        [:lizard "poisons" :spock]
        [:spock "vaporizes" :rock]
        [:spock "smashes" :scissors]]
       (map (fn [[winner _ loser]]
              {[winner loser] first
               [loser winner] second
               [winner winner] (constantly :draw)}))
       (apply merge)))

(defn winner
  ([human-hand computer-hand]
   (winner :human human-hand
           :computer computer-hand))
  ([player1 hand1
    player2 hand2]
   (let [players [player1 player2]
         hands [hand1 hand2]
         select-winner (or (rules hands)
                           (throw (IllegalArgumentException.
                                   (str "Illegal hands: " hands))))]
     (select-winner players))))


(deftest rock-paper-scissors-test
  (testing "human plays rock"
    (is (= :draw (winner :rock :rock)))
    (is (= :computer (winner :rock :paper)))
    (is (= :human (winner :rock :scissors))))

  (testing "human plays paper"
    (is (= :human (winner :paper :rock)))
    (is (= :draw (winner :paper :paper)))
    (is (= :computer (winner :paper :scissors))))

  (testing "human plays scissors"
    (is (= :computer (winner :scissors :rock)))
    (is (= :human (winner :scissors :paper)))
    (is (= :draw (winner :scissors :scissors))))

  (testing "invalid input"
    (is (thrown-with-msg?
         IllegalArgumentException #"\QIllegal hands: [:foo :bar]\E"
         (winner :foo :bar)))))

(deftest lizard-spock-test
  (testing "human plays lizard"
    (is (= :computer (winner :lizard :rock)))
    (is (= :human (winner :lizard :paper)))
    (is (= :computer (winner :lizard :scissors)))
    (is (= :draw (winner :lizard :lizard)))
    (is (= :human (winner :lizard :spock))))

  (testing "human plays spock"
    (is (= :human (winner :spock :rock)))
    (is (= :computer (winner :spock :paper)))
    (is (= :human (winner :spock :scissors)))
    (is (= :computer (winner :spock :lizard)))
    (is (= :draw (winner :spock :spock)))))
