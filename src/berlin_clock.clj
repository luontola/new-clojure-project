(ns berlin-clock
  (:import (java.time LocalTime)))

(def lamp-off \O)
(def lamps-off (repeat lamp-off))

(defn light-lamps [all-lamps num-lights]
  (let [num-lamps (count all-lamps)
        lamps-on (take num-lights all-lamps)]
    (->> (concat lamps-on lamps-off)
         (take num-lamps)
         (apply str))))

(def all-1-minute-lamps "YYYY")
(defn time->1-minute-lamps [^LocalTime time]
  (light-lamps all-1-minute-lamps (rem (.getMinute time)
                                       5)))

(def all-5-minute-lamps "YYRYYRYYRYY")
(defn time->5-minute-lamps [^LocalTime time]
  (light-lamps all-5-minute-lamps (quot (.getMinute time)
                                        5)))

(def all-1-hour-lamps "RRRR")
(defn time->1-hour-lamps [^LocalTime time]
  (light-lamps all-1-hour-lamps (rem (.getHour time)
                                     5)))

(def all-5-hour-lamps "RRRR")
(defn time->5-hour-lamps [^LocalTime time]
  (light-lamps all-5-hour-lamps (quot (.getHour time)
                                      5)))

(defn- invert-seconds [n]
  (case n
    0 1
    1 0))

(def all-1-second-lamps "Y")
(defn time->1-second-lamps [^LocalTime time]
  (light-lamps all-1-second-lamps (invert-seconds (rem (.getSecond time)
                                                       2))))

(defn time->berlin-clock [^LocalTime time]
  (str (time->1-second-lamps time)
       (time->5-hour-lamps time)
       (time->1-hour-lamps time)
       (time->5-minute-lamps time)
       (time->1-minute-lamps time)))

(defn- lit? [lamp]
  (not= lamp-off lamp))

(defn- num-lights [lamps]
  (count (filter lit? lamps)))

(defn berlin-clock->time [lamps]
  (let [[lamps-1-second lamps] (split-at (count all-1-second-lamps) lamps)
        [lamps-5-hour lamps] (split-at (count all-5-hour-lamps) lamps)
        [lamps-1-hour lamps] (split-at (count all-1-hour-lamps) lamps)
        [lamps-5-minute lamps] (split-at (count all-5-minute-lamps) lamps)
        [lamps-1-minute _lamps] (split-at (count all-1-minute-lamps) lamps)
        hours (+ (* 5 (num-lights lamps-5-hour))
                 (num-lights lamps-1-hour))
        minutes (+ (* 5 (num-lights lamps-5-minute))
                   (num-lights lamps-1-minute))
        seconds (invert-seconds (num-lights lamps-1-second))]
    (LocalTime/of hours minutes seconds)))
