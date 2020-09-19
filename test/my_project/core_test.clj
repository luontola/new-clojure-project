(ns my-project.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [my-project.core :as core]))

(def all-suits
  [:heart :spade :club :diamond])
(def all-ranks
  [:ace :king :queen :jack 10 9 8 7 6 5 4 3 2])
(def all-cards
  (for [suit all-suits
        rank all-ranks]
    {:suit suit
     :rank rank}))

(deftest cards-test
  (is (= {:suit :heart
          :rank :ace}
         (first all-cards)))
  (is (= 52 (count all-cards))))

(comment
  ; https://clojuredocs.org/clojure.core/sort-by
  ; https://clojuredocs.org/clojure.core/map
  (map :rank [{:suit :spade, :rank 7}
              {:suit :spade, :rank :queen}
              {:suit :heart, :rank 4}])
  (sort-by count ["aaa" "bb" "c"])
  #(.indexOf all-ranks %))

(defn get-highest-rank [ranks]
  (->> ranks
       (sort-by #(.indexOf all-ranks %))
       (first)))

(defn evaluate-hand [hand]
  (let [rank (->> (map :rank hand)
                  (get-highest-rank))]
    {:category :high-card
     :rank rank}))

(deftest get-highest-rank-test
  (is (= :king (get-highest-rank [7 :queen 4 3 :king]))))


(deftest hands-test
  (testing "high card"
    (is (= {:category :high-card, :rank :king}
           (evaluate-hand [{:suit :spade, :rank 7}
                           {:suit :spade, :rank :queen}
                           {:suit :heart, :rank 4}
                           {:suit :heart, :rank 3}
                           {:suit :diamond, :rank :king}])))))
