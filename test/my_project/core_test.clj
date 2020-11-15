(ns my-project.core-test
  (:require [clojure.test :refer [deftest is testing]]))

(def rules
  (->> [[:scissors "cuts" :paper]
        [:paper "covers" :rock]
        [:rock "crushes" :lizard]
        [:lizard "poisons" :spock]
        [:spock "zaps" :wizard]
        [:wizard "stuns" :batman]
        [:batman "scares" :spider-man]
        [:spider-man "disarms" :glock]
        [:glock "beats" :rock]
        [:rock "interrupts" :wizard]
        [:wizard "burns" :paper]
        [:paper "disproves" :spock]
        [:spock "befuddles" :spider-man]
        [:spider-man "defeats" :lizard]
        [:lizard "confuses" :batman "(because he looks like Killer Croc)"]
        [:batman "dismantles" :scissors]
        [:scissors "cut" :wizard]
        [:wizard "transforms" :lizard]
        [:lizard "eats" :paper]
        [:paper "jams" :glock]
        [:glock "kills" :batman "'s mom"]
        [:batman "explodes" :rock]
        [:rock "crushes" :scissors]
        [:scissors "decapitates" :lizard]
        [:lizard "is too small for" :glock]
        [:glock "shoots" :spock]
        [:spock "vaporizes" :rock]
        [:rock "knocks out" :spider-man]
        [:spider-man "rips" :paper]
        [:paper "delays" :batman]
        [:batman "hangs" :spock]
        [:spock "smashes" :scissors]
        [:scissors "cut" :spider-man]
        [:spider-man "annoys" :wizard]
        [:wizard "melts" :glock]
        [:glock "dents" :scissors]]
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

(deftest spider-man-batman-wizard-glock-test
  (testing "human plays spider-man"
    (is (= :computer (winner :spider-man :rock)))
    (is (= :human (winner :spider-man :paper)))
    (is (= :computer (winner :spider-man :scissors)))
    (is (= :human (winner :spider-man :lizard)))
    (is (= :computer (winner :spider-man :spock)))
    (is (= :draw (winner :spider-man :spider-man)))
    (is (= :computer (winner :spider-man :batman)))
    (is (= :human (winner :spider-man :wizard)))
    (is (= :human (winner :spider-man :glock))))

  (testing "human plays batman"
    (is (= :human (winner :batman :rock)))
    (is (= :computer (winner :batman :paper)))
    (is (= :human (winner :batman :scissors)))
    (is (= :computer (winner :batman :lizard)))
    (is (= :human (winner :batman :spock)))
    (is (= :human (winner :batman :spider-man)))
    (is (= :draw (winner :batman :batman)))
    (is (= :computer (winner :batman :wizard)))
    (is (= :computer (winner :batman :glock))))

  (testing "human plays wizard"
    (is (= :computer (winner :wizard :rock)))
    (is (= :human (winner :wizard :paper)))
    (is (= :computer (winner :wizard :scissors)))
    (is (= :human (winner :wizard :lizard)))
    (is (= :computer (winner :wizard :spock)))
    (is (= :computer (winner :wizard :spider-man)))
    (is (= :human (winner :wizard :batman)))
    (is (= :draw (winner :wizard :wizard)))
    (is (= :human (winner :wizard :glock))))

  (testing "human plays glock"
    (is (= :human (winner :glock :rock)))
    (is (= :computer (winner :glock :paper)))
    (is (= :human (winner :glock :scissors)))
    (is (= :computer (winner :glock :lizard)))
    (is (= :human (winner :glock :spock)))
    (is (= :computer (winner :glock :spider-man)))
    (is (= :human (winner :glock :batman)))
    (is (= :computer (winner :glock :wizard)))
    (is (= :draw (winner :glock :glock)))))
