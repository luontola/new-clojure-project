(ns berlin-clock-test
  (:require [berlin-clock :as bc]
            [clojure.test :refer [deftest is]])
  (:import (java.time LocalTime)))

(deftest time->1-minutes-test
  (doseq [[lights time] [["OOOO" (LocalTime/of 0 0 0)]
                         ["YYYY" (LocalTime/of 23 59 59)]
                         ["YOOO" (LocalTime/of 12 31 0)]
                         ["YYOO" (LocalTime/of 12 32 0)]
                         ["YYYO" (LocalTime/of 12 33 0)]
                         ["YYYY" (LocalTime/of 12 34 0)]
                         ["OOOO" (LocalTime/of 12 35 0)]]]
    (is (= lights (bc/time->1-minutes time))
        (str time))))

(deftest time->5-minutes-test
  (doseq [[lights time] [["OOOOOOOOOOO" (LocalTime/of 0 0 0)]
                         ["YYRYYRYYRYY" (LocalTime/of 23 59 59)]
                         ["OOOOOOOOOOO" (LocalTime/of 12 4 0)]
                         ["YYRYOOOOOOO" (LocalTime/of 12 23 0)]
                         ["YYRYYRYOOOO" (LocalTime/of 12 35 0)]]]
    (is (= lights (bc/time->5-minutes time))
        (str time))))

(deftest time->1-hours-test
  (doseq [[lights time] [["OOOO" (LocalTime/of 0 0 0)]
                         ["RRRO" (LocalTime/of 23 59 59)]
                         ["RROO" (LocalTime/of 2 4 0)]
                         ["RRRO" (LocalTime/of 8 23 0)]
                         ["RRRR" (LocalTime/of 14 35 0)]]]
    (is (= lights (bc/time->1-hours time))
        (str time))))
