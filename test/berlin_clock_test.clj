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

(deftest time->5-hours-test
  (doseq [[lights time] [["OOOO" (LocalTime/of 0 0 0)]
                         ["RRRR" (LocalTime/of 23 59 59)]
                         ["OOOO" (LocalTime/of 2 4 0)]
                         ["ROOO" (LocalTime/of 8 23 0)]
                         ["RRRO" (LocalTime/of 16 35 0)]]]
    (is (= lights (bc/time->5-hours time))
        (str time))))

(deftest time->seconds-test
  (doseq [[lights time] [["Y" (LocalTime/of 0 0 0)]
                         ["O" (LocalTime/of 23 59 59)]]]
    (is (= lights (bc/time->seconds time))
        (str time))))

(deftest time->berlin-clock-test
  (doseq [[lights time] [["YOOOOOOOOOOOOOOOOOOOOOOO" (LocalTime/of 0 0 0)]
                         ["ORRRRRRROYYRYYRYYRYYYYYY" (LocalTime/of 23 59 59)]
                         ["YRRROROOOYYRYYRYYRYOOOOO" (LocalTime/of 16 50 6)]
                         ["ORROOROOOYYRYYRYOOOOYYOO" (LocalTime/of 11 37 01)]]]
    (is (= lights (bc/time->berlin-clock time))
        (str time))))
