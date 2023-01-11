(ns berlin-clock
  (:import (java.time LocalTime)))

(def lamps-off (repeat \O))

(defn light-lamps [lamps num-lights]
  (let [lamps-count (count lamps)
        lamps-on (take num-lights lamps)]
    (->> (concat lamps-on lamps-off)
         (take lamps-count)
         (apply str))))

(def lamps-1-minutes "YYYY")
(defn time->1-minutes [^LocalTime time]
  (light-lamps lamps-1-minutes (mod (.getMinute time)
                                    5)))

(def lamps-5-minutes "YYRYYRYYRYY")
(defn time->5-minutes [^LocalTime time]
  (light-lamps lamps-5-minutes (int (/ (.getMinute time)
                                       5))))

(def lamps-1-hours "RRRR")
(defn time->1-hours [^LocalTime time]
  (light-lamps lamps-1-hours (mod (.getHour time)
                                  5)))

(def lamps-5-hours "RRRR")
(defn time->5-hours [^LocalTime time]
  (light-lamps lamps-5-hours (int (/ (.getHour time)
                                     5))))

(defn- invert-seconds [n]
  (case n
    0 1
    1 0))

(def lamps-seconds "Y")
(defn time->seconds [^LocalTime time]
  (light-lamps lamps-seconds (invert-seconds (mod (.getSecond time)
                                                  2))))

(defn time->berlin-clock [^LocalTime time]
  (str (time->seconds time)
       (time->5-hours time)
       (time->1-hours time)
       (time->5-minutes time)
       (time->1-minutes time)))

(defn- lit? [lamp]
  (not= \O lamp))

(defn- count-lights [lamps]
  (count (filter lit? lamps)))

(defn berlin-clock->time [lamps]
  (let [[lamps-seconds lamps] (split-at (count lamps-seconds) lamps)
        [lamps-5-hours lamps] (split-at (count lamps-5-hours) lamps)
        [lamps-1-hours lamps] (split-at (count lamps-1-hours) lamps)
        [lamps-5-minutes lamps] (split-at (count lamps-5-minutes) lamps)
        [lamps-1-minutes _lamps] (split-at (count lamps-1-minutes) lamps)
        hours (+ (* 5 (count-lights lamps-5-hours))
                 (count-lights lamps-1-hours))
        minutes (+ (* 5 (count-lights lamps-5-minutes))
                   (count-lights lamps-1-minutes))
        seconds (invert-seconds (count-lights lamps-seconds))]
    (LocalTime/of hours minutes seconds)))
