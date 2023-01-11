(ns berlin-clock-test
  (:require [berlin-clock :as bc]
            [clojure.test :refer [deftest is]])
  (:import (java.time LocalTime)))

(deftest time->1-minutes-test
  (is (= "OOOO" (bc/time->1-minutes (LocalTime/of 0 0 0))))
  (is (= "YYYY" (bc/time->1-minutes (LocalTime/of 23 59 59))))
  (is (= "YOOO" (bc/time->1-minutes (LocalTime/of 12 31 0))))
  (is (= "YYOO" (bc/time->1-minutes (LocalTime/of 12 32 0))))
  (is (= "YYYO" (bc/time->1-minutes (LocalTime/of 12 33 0))))
  (is (= "YYYY" (bc/time->1-minutes (LocalTime/of 12 34 0))))
  (is (= "OOOO" (bc/time->1-minutes (LocalTime/of 12 35 0)))))
