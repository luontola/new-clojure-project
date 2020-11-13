(ns my-project.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [my-project.core :as core]))

(def rules
  (->> [[:paper :rock]
        [:scissors :paper]
        [:rock :scissors]]
       (map (fn [[winner loser]]
              {[winner loser] first
               [loser winner] second
               [winner winner] (constantly :draw)}))
       (apply merge)))

(defn winner [human-hand computer-hand]
  (let [players [:human :computer]
        hands [human-hand computer-hand]
        select-winner (rules hands)]
    (select-winner players)))


(deftest rock-paper-scissors-test
  (is (= :computer (winner :rock :paper)))
  (is (= :human (winner :paper :rock)))

  (is (= :computer (winner :paper :scissors)))
  (is (= :human (winner :scissors :paper)))

  (is (= :computer (winner :scissors :rock)))
  (is (= :human (winner :rock :scissors)))

  (is (= :draw (winner :rock :rock)))
  (is (= :draw (winner :paper :paper)))
  (is (= :draw (winner :scissors :scissors))))
