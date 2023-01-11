(ns berlin-clock-test
  (:require [berlin-clock :as bc]
            [clojure.test :refer [deftest is testing]])
  (:import (java.time LocalTime)))

(deftest time->1-minutes-test
  (is (= "OOOO" (bc/time->1-minutes (LocalTime/of 0 0 0))))
  (is (= "YYYY" (bc/time->1-minutes (LocalTime/of 23 59 59))))
  (is (= "YOOO" (bc/time->1-minutes (LocalTime/of 12 31 0))))
  (is (= "YYOO" (bc/time->1-minutes (LocalTime/of 12 32 0))))
  (is (= "YYYO" (bc/time->1-minutes (LocalTime/of 12 33 0))))
  (is (= "YYYY" (bc/time->1-minutes (LocalTime/of 12 34 0))))
  (is (= "OOOO" (bc/time->1-minutes (LocalTime/of 12 35 0)))))

(deftest time->5-minutes-test
  (is (= "OOOOOOOOOOO" (bc/time->5-minutes (LocalTime/of 0 0 0))))
  (is (= "YYRYYRYYRYY" (bc/time->5-minutes (LocalTime/of 23 59 59))))
  (is (= "OOOOOOOOOOO" (bc/time->5-minutes (LocalTime/of 12 4 0))))
  (is (= "YYRYOOOOOOO" (bc/time->5-minutes (LocalTime/of 12 23 0))))
  (is (= "YYRYYRYOOOO" (bc/time->5-minutes (LocalTime/of 12 35 0)))))

(deftest time->1-hours-test
  (doseq [[lights time] [["OOOO" (LocalTime/of 0 0 0)]
                         ["RRRO" (LocalTime/of 23 59 59)]
                         ["RROO" (LocalTime/of 2 4 0)]
                         ["RRRO" (LocalTime/of 8 23 0)]
                         ["RRRR" (LocalTime/of 14 35 0)]]]
    (testing (str time " -> " lights)
      (is (= lights (bc/time->1-hours time))))))
