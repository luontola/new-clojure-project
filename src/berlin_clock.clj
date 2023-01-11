(ns berlin-clock
  (:import (java.time LocalTime)))

(defn time->1-minutes [^LocalTime time]
  (case (mod (.getMinute time) 5)
    0 "OOOO"
    1 "YOOO"
    2 "YYOO"
    3 "YYYO"
    4 "YYYY"))
